package mysql.mvcc.repeatableread;

import mysql.mvcc.MVCCTable;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RepeatableReadTransactionTest {

    @Test
    public void test_事务a看不到事务b创建的记录() {

        MVCCTable table = new MVCCTable();
        RepeatableReadTransaction ta = new RepeatableReadTransaction();
        RepeatableReadTransaction tb = new RepeatableReadTransaction();

        tb.insert(table, "bbb", "bbb");

        Assert.assertEquals(ta.select(table, "bbb"), null);

    }
}
