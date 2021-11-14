package mysql.mvcc.repeatableread;

import mysql.mvcc.MVCCTable;
import mysql.mvcc.MVCCTable.VersionData;
import mysql.mvcc.TransactionStatus;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;


// TODO 内部使用一个单线程的线程池
public class RepeatableReadTransaction {

    static public final AtomicInteger systemVersion = new AtomicInteger(0);

    // 事务状态
    private TransactionStatus status;
    // 事务版本
    private final int version;
    // 事务处理的记录id
    private Map<String, MVCCTable> processingRecords = new HashMap<>();

    // MVCC+RR机制下，普通select数据时增加的两个隐含条件
    private Predicate<VersionData> versionCond = new Predicate<VersionData>() {
        @Override
        public boolean test(VersionData versionData) {
            return versionData.getCreateVersion() <= version
                    && (versionData.getDeleteVersion() == null
                    || versionData.getDeleteVersion() > version);
        }
    };

    // 执行事务的单线程
    private ExecutorService executor = Executors.newFixedThreadPool(1);


    // 私有构造函数，只能通过静态方法begin()来创建事务
    private RepeatableReadTransaction() {
        status = TransactionStatus.STARTED;
        version = systemVersion.incrementAndGet();
    }

    /**
     * 开始事务
     */
    static public RepeatableReadTransaction begin() {
        return new RepeatableReadTransaction();
    }

    /**
     * 提交事务，对事务处理的记录，释放锁。
     */
    public void commit() {
        execute(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(status, TransactionStatus.STARTED);

                for (String id : processingRecords.keySet()) {
                    MVCCTable table = processingRecords.get(id);
                    table.unlock(id, version);
                }
                status = TransactionStatus.COMMIT;
            }
        });
    }

    /**
     * 查询数据。数据的创建版本需要小于等于事务版本。
     * !!! MVCC+RR机制下，普通select数据时增加的两个隐含条件:
     *   createVersion>=事务版本 && (deleteVersion为空 或 deleteVersion>事务版本)
     *
     * @param table
     * @param id
     * @return
     */
    public Object select(MVCCTable table, String id) {
        return execute(new Callable<Object>() {
            @Override
            public Object call() {
                return table.select(id, version);
            }
        });
    }

    /**
     * 插入数据，以当前事务版本号作为数据的创建版本。
     * @param table
     * @param id
     * @param data
     */
    public void insert(MVCCTable table, String id, Object data) {
        execute(new Runnable() {
            @Override
            public void run() {
                table.insert(id, data, version);
                processingRecords.put(id, table);
            }
        });
    }

    /**
     * 删除数据，以当前事务版本号作为数据的删除版本。
     * @param table
     * @param id
     */
    public boolean delete(MVCCTable table, String id) {
        return execute(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean result = table.delete(id, version);
                processingRecords.put(id, table);
                return result;
            }
        });
    }

    /**
     * 更新数据。新VersionData的版本等于事务版本。
     * @param table
     * @param id
     * @param data
     * @return
     */
    public boolean update(MVCCTable table, String id, Object data) {
        return execute(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean result = table.update(id, data, version);
                processingRecords.put(id, table);
                return result;
            }
        });
    }


    private void execute(Runnable task) {
        try {
            executor.submit(task).get();
        } catch (Exception ire) {
            throw new RuntimeException(ire);
        }
    }

    private <T> T execute(Callable<T> task) {
        try {
            return executor.submit(task).get();
        } catch (Exception ire) {
            throw new RuntimeException(ire);
        }
    }

}
