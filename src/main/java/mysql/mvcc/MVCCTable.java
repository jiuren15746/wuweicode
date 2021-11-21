package mysql.mvcc;

import lombok.Data;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * MVCC机制的表。！！！最核心。
 */
public class MVCCTable {
    // key = id
    private Map<String, VersionData> dataMap = new HashMap<>();

    @Data
    static public class VersionData {
        private String id;
        private Object data;
        // 删除标记
        private final boolean isDelete;

        // (MVCC) InnoDB给每条记录增加的版本号
        private final Integer createVersion;  // 创建该版本的事务的版本号
        private Integer deleteVersion;  // 删除该版本的事务的版本号
        // 上一个版本的数据
        private final VersionData previousVersion;

        // 锁住该记录的事务版本. null表示没有被锁定。
        private Integer lockedBy;

        public VersionData(String id, Object data,
                           boolean isDelete,
                           Integer version,
                           VersionData previous) {
            this.id = id;
            this.data = data;
            this.isDelete = isDelete;
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
        // Insert record and lock it
        VersionData versionData = new VersionData(id, data, false, version, null);
        versionData.lockedBy = version;
        // Expose record
        dataMap.put(id, versionData);
    }

    /**
     * 在事务中删除记录。同时给记录加锁。
     * (MVCC) 给记录增加一个版本，标记为删除。
     *
     * @param id
     * @param version 创建版本
     */
    public boolean delete(String id, int version) throws InterruptedException {
        // Lock current version
        VersionData current = lock(id, version);
        if (null == current) {
            return false;
        }
        // Update deleteVersion
        current.setDeleteVersion(version);

        // Create new version, lock it and expose it.
        VersionData previous = current;
        VersionData newVersion = new VersionData(id, null, true, version, previous);
        newVersion.lockedBy = version;
        dataMap.put(id, newVersion);

        // Release lock for previous version
        previous.lockedBy = null;
        return true;
    }

    /**
     * 在事务中更新记录。(MVCC) 并不更新最新版本的VersionData，而是插入一个新版本的VersionData。
     *
     * @param id
     * @param value
     * @param version 发起更新操作的事务版本
     * @return
     */
    public boolean update(String id, Object value, Integer version)
            throws InterruptedException {
        // Lock current version
        VersionData current = lock(id, version);
        if (null == current) {
            return false;
        }
        // Update deleteVersion
        current.setDeleteVersion(version);

        // Insert new version, lock it and expose it
        VersionData previous = current;
        VersionData newVersion = new VersionData(id, value, false, version, previous);
        newVersion.lockedBy = version;
        dataMap.put(id, newVersion);

        // Release lock for previous version.
        previous.lockedBy = null;
        return true;
    }

    /**
     * 查询id对应的数据，且数据版本要符合versionCond指定的条件。
     */
    public Object select(String id, AbstractTransaction tx) {
        VersionData current = dataMap.get(id);
        if (null == current) {
            return null;
        }

        for (VersionData item = current; item != null; item = item.getPreviousVersion()) {
            if (// MVCC隐式条件
                    tx.isDataVisible(item)) {
                return item.isDelete() ? null : item.getData();
            }
        }
        return null;
    }

    /**
     * 对id记录的最新版本加锁。如果记录被其他事务锁定，当前线程会阻塞，直到其他事务释放锁。
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
