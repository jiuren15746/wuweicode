package mysql.mvcc;

/**
 * Read Committed 隔离级别下的事务
 */
public class ReadCommittedTransaction extends AbstractTransaction {

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

    /**
     * MVCC隐式条件: 只要是已提交的，或者是自己锁定的，都可以看见
     */
    public boolean isDataVisible(MVCCTable.VersionData item) {
        return (item.getLockedBy() == null || item.getLockedBy() == getVersion());
    }

}
