package mysql.mvcc.repeatableread;

import mysql.mvcc.MVCCTable;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static mysql.mvcc.repeatableread.RepeatableReadTransaction.begin;

public class RepeatableReadTransactionTest {

    private MVCCTable table = new MVCCTable();

    @BeforeMethod
    public void startup() {
        table = new MVCCTable();
    }

    @Test
    public void test_事务a先创建_看不到事务b创建的记录() {

        RepeatableReadTransaction ta = begin();

        RepeatableReadTransaction tb = begin();
        tb.insert(table, "bbb", "bbb");
        tb.commit();

        Assert.assertEquals(ta.select(table, "bbb"), null);
    }

    @Test
    public void test_事务a后创建_可以看到事务b创建的记录() {

        RepeatableReadTransaction tb = begin();
        tb.insert(table, "bbb", "bbb");
        tb.commit();

        RepeatableReadTransaction ta = begin();
        Assert.assertEquals(ta.select(table, "bbb"), "bbb");
    }

    @Test
    public void test_事务a先创建_看不到事务b修改的记录() {

        RepeatableReadTransaction t = begin();
        t.insert(table, "ttt", "ttt");
        t.commit();

        // 事务A先于事务B
        RepeatableReadTransaction ta = begin();

        // 事务B更新记录
        RepeatableReadTransaction tb = begin();
        tb.update(table, "ttt", "bbb");
        tb.commit();

        // 事务A看到的数据，仍然等于ttt
        Assert.assertEquals(ta.select(table, "ttt"), "ttt");
    }
}
