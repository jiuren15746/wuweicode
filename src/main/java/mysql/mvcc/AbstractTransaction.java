package mysql.mvcc;

import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务父类。
 */
public abstract class AbstractTransaction {

    static public final AtomicInteger systemVersion = new AtomicInteger(0);

    // 事务状态
    private TransactionStatus status;
    // 事务版本
    private final int version;
    // 事务处理的记录id
    private Map<String, MVCCTable> processingRecords = new HashMap<>();

    // 执行事务的单线程
    private ExecutorService executor = Executors.newFixedThreadPool(1);


    // 私有构造函数，只能通过静态方法begin()来创建事务
    protected AbstractTransaction() {
        status = TransactionStatus.STARTED;
        version = systemVersion.incrementAndGet();
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
     * 查询数据。
     */
    public Object select(MVCCTable table, String id) {
        return execute(new Callable<Object>() {
            @Override
            public Object call() {
                return table.select(id, AbstractTransaction.this);
            }
        });
    }

    /**
     * 插入数据，以当前事务版本号作为数据的创建版本。
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

    protected void execute(Runnable task) {
        // 断言事务状态
        Assert.assertEquals(status, TransactionStatus.STARTED);
        try {
            executor.submit(task).get();
        } catch (Exception ire) {
            throw new RuntimeException(ire);
        }
    }
    protected <T> T execute(Callable<T> task) {
        // 断言事务状态
        Assert.assertEquals(status, TransactionStatus.STARTED);
        try {
            return executor.submit(task).get();
        } catch (Exception ire) {
            throw new RuntimeException(ire);
        }
    }

    public int getVersion() {
        return version;
    }

    /**
     * MVCC隐式条件，数据是否可以被该事务看见。
     * @param item
     * @return
     */
    abstract public boolean isDataVisible(MVCCTable.VersionData item);
}
