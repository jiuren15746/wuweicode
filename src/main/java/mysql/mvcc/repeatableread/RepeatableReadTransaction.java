package mysql.mvcc.repeatableread;

import mysql.mvcc.MVCCTable;
import mysql.mvcc.MVCCTable.VersionData;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class RepeatableReadTransaction {

    static public final AtomicInteger systemVersion = new AtomicInteger(0);

    private final int version = systemVersion.incrementAndGet();

    private Predicate<VersionData> versionCond = new Predicate<VersionData>() {
        @Override
        public boolean test(VersionData versionData) {
            return versionData.getCreateVersion() <= version;
        }
    };

    /**
     * 插入数据，以当前事务版本号作为数据的创建版本。
     * @param table
     * @param id
     * @param data
     */
    public void insert(MVCCTable table, String id, Object data) {
        table.insert(id, data, version);
    }

    /**
     * 查询数据。数据的创建版本需要小于等于事务版本。
     * @param table
     * @param id
     * @return
     */
    public Object select(MVCCTable table, String id) {
        return table.select(id, versionCond);
    }

//    public boolean update(MVCCTable table, )

}
