package mysql.mvcc.readcommited;

import mysql.mvcc.MVCCTable;
import mysql.mvcc.repeatableread.RepeatableReadTransaction;

import java.util.concurrent.Callable;


/**
 * Read Committed 隔离级别下的事务
 */
public class ReadCommittedTransaction extends RepeatableReadTransaction {

    // 私有构造函数，只能通过静态方法begin()来创建事务
    private ReadCommittedTransaction() {
        super();
    }

    /**
     * 开始事务
     */
    static public ReadCommittedTransaction begin() {
        return new ReadCommittedTransaction();
    }

//    /**
//     * 提交事务，对事务处理的记录，释放锁。
//     */
//    public void commit() {
//        execute(new Runnable() {
//            @Override
//            public void run() {
//                Assert.assertEquals(status, TransactionStatus.STARTED);
//
//                for (String id : processingRecords.keySet()) {
//                    MVCCTable table = processingRecords.get(id);
//                    table.unlock(id, version);
//                }
//                status = TransactionStatus.COMMIT;
//            }
//        });
//    }

    /**
     * 查询数据。RC隔离级别下，可以看到已提交的最新版本。
     */
    public Object select(MVCCTable table, String id) {
        return execute(new Callable<Object>() {
            @Override
            public Object call() {
                return table.select(id, ReadCommittedTransaction.this);
            }
        });
    }

    // MVCC隐式条件：
    // 1. createVersion <= 事务版本
    // 2. deleteVersion为空 || deleteVersion > 事务版本
    public boolean isDataVisible(MVCCTable.VersionData item) {
        return (item.getLockedBy() == null || item.getLockedBy() == getVersion());
//                && item.getDeleteVersion() == null;
    }

//    /**
//     * 插入数据，以当前事务版本号作为数据的创建版本。
//     */
//    public void insert(MVCCTable table, String id, Object data) {
//        execute(new Runnable() {
//            @Override
//            public void run() {
//                table.insert(id, data, version);
//                processingRecords.put(id, table);
//            }
//        });
//    }

//    /**
//     * 删除数据，以当前事务版本号作为数据的删除版本。
//     *
//     * @param table
//     * @param id
//     */
//    public boolean delete(MVCCTable table, String id) {
//        return execute(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                boolean result = table.delete(id, version);
//                processingRecords.put(id, table);
//                return result;
//            }
//        });
//    }

//    /**
//     * 更新数据。新VersionData的版本等于事务版本。
//     *
//     * @param table
//     * @param id
//     * @param data
//     * @return
//     */
//    public boolean update(MVCCTable table, String id, Object data) {
//        return execute(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                boolean result = table.update(id, data, version);
//                processingRecords.put(id, table);
//                return result;
//            }
//        });
//    }


//    private void execute(Runnable task) {
//        // 断言事务状态
//        Assert.assertEquals(status, TransactionStatus.STARTED);
//        try {
//            executor.submit(task).get();
//        } catch (Exception ire) {
//            throw new RuntimeException(ire);
//        }
//    }
//
//    private <T> T execute(Callable<T> task) {
//        // 断言事务状态
//        Assert.assertEquals(status, TransactionStatus.STARTED);
//        try {
//            return executor.submit(task).get();
//        } catch (Exception ire) {
//            throw new RuntimeException(ire);
//        }
//    }

}
