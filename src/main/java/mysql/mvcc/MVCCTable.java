package mysql.mvcc;

import lombok.Data;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * MVCC机制的表。
 */
public class MVCCTable {
    // key = id
    private Map<String, VersionData> dataMap = new HashMap<>();

    @Data
    static public class VersionData {
        private String id;
        private Object data;

        // 锁住该记录的事务版本. null表示没有被锁定。
        private Integer lockedBy;

        // (MVCC) InnoDB给每条记录增加的两个版本号
        private final Integer createVersion;
        private Integer deleteVersion;

        // 上一个版本的数据
        private final VersionData previousVersion;

        public VersionData(String id, Object data,
                           Integer version,
                           VersionData previous) {
            this.id = id;
            this.data = data;
            this.createVersion = version;
            this.previousVersion = previous;
        }
    }

    /**
     * 在事务中插入记录。同时给记录加锁。
     * @param id
     * @param data
     * @param version 创建版本
     */
    public void insert(String id, Object data, int version) {
        if (dataMap.containsKey(id)) {
            throw new RuntimeException();
        }

        VersionData versionData = new VersionData(id, data, version, null);
        versionData.lockedBy = version;
        dataMap.put(id, versionData);
    }

    /**
     * 在事务中删除记录。同时给记录加锁。
     * @param id
     * @param version 创建版本
     */
    public boolean delete(String id, int version) {
        try {
            // 加锁
            VersionData current = lock(id, version);
            if (null == current) {
                return false;
            }
            // (MVCC) 设置deleteVersion
            current.deleteVersion = version;
            return true;
        } catch (InterruptedException ire) {
            throw new RuntimeException(ire);
        }
    }

    /**
     * 在事务中更新记录。(MVCC) 并不更新最新版本的VersionData，而是插入一个新版本的VersionData。
     *
     * @param id
     * @param value
     * @param version 发起更新操作的事务版本
     * @return
     */
    public boolean update(String id, Object value, Integer version) {
        try {
            // Lock current version
            VersionData current = lock(id, version);
            if (null == current) {
                return false;
            }
            VersionData previous = current;

            // Insert new version and lock it
            VersionData newVersion = new VersionData(id, value, version, current);
            newVersion.lockedBy = version;

            // For previous version, record deleteVersion and release lock
            previous.setDeleteVersion(version);
            previous.lockedBy = null;

            // Expose new version
            dataMap.put(id, newVersion);
            return true;

        } catch (InterruptedException ire) {
            ire.printStackTrace();
            return false;
        }
    }

    /**
     * 查询id对应的数据，且数据版本要符合versionCond指定的条件。
     */
    public Object select(String id, Integer version) {
        VersionData current = dataMap.get(id);
        if (null == current) {
            return null;
        }

        // 只能查询已提交的记录，或本事务中的记录
        if (!(current.lockedBy == null || current.lockedBy == version)) {
            return null;
        }

        // MVCC隐式条件：
        // 1.记录createVersion <= 事务版本
        // 2.记录deleteVersion为空 || deleteVersion > 事务版本
        for (VersionData item = current; item != null; item = item.getPreviousVersion()) {
            if (item.getCreateVersion() <= version
                    && (item.getDeleteVersion() == null
                    || item.getDeleteVersion() > version)) {
                return item.getData();
            }
        }
        return null;
    }

    /**
     * 对id对应的记录加锁。如果记录被其他事务锁定，当前线程会阻塞，直到其他事务释放锁。
     * 如果事务已经获得该记录的锁，该方法立即返回。即该方法是可重入的。
     * @param id
     * @param version 加锁的事务版本
     */
    private VersionData lock(String id, Integer version) throws InterruptedException {
        VersionData current = dataMap.get(id);
        if (null == current) {
            return null;
        }

        synchronized (current) {
            while (!(current.lockedBy == null || current.lockedBy == version)) {
                current.wait();
            }
            current.lockedBy = version;
            return current;
        }
    }

    /**
     * 对id对应的记录释放锁。同时通知其他正在等待锁的事务。
     * @param id
     * @param version
     */
    public void unlock(String id, Integer version) {
        VersionData current = dataMap.get(id);
        // 断言：记录被当前事务锁定
        Assert.assertTrue(current.lockedBy == version);

        synchronized (current) {
            current.lockedBy = null;
            current.notifyAll();
        }
    }

}
