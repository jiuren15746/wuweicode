package mysql.mvcc;

/**
 * Repeatable Read 隔离级别下的事务
 */
public class RepeatableReadTransaction extends AbstractTransaction {

    // 私有构造函数，只能通过静态方法begin()来创建事务
    protected RepeatableReadTransaction() {
        super();
    }

    /**
     * 开始事务
     */
    static public RepeatableReadTransaction begin() {
        return new RepeatableReadTransaction();
    }

    /**
     * 数据被该事务看见的条件：1. 数据已提交，或被本事务锁定 2. MVCC_RR的隐含条件。
     * @param item
     * @return
     */
    public boolean isDataVisible(MVCCTable.VersionData item) {
        return (item.getLockedBy() == null || item.getLockedBy() == getVersion())
                //     * MVCC隐式条件：
                //     *   1. createVersion <= 事务版本
                //     *   2. deleteVersion为空 || deleteVersion > 事务版本
                && item.getCreateVersion() <= getVersion()
                && (item.getDeleteVersion() == null || item.getDeleteVersion() > getVersion());
    }
}
