package mysql.mvcc;

/**
 * 事务状态枚举
 */
public enum TransactionStatus {
    INIT,
    STARTED,
    COMMIT,
    ROLLBACK
}
