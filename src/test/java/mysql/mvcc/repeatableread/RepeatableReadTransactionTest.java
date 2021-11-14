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
    public void testReadCommited_事务a插入数据并提交_事务b后创建可以查询到() {
        RepeatableReadTransaction ta = begin();
        ta.insert(table, "aaa", "aaa");
        ta.commit();

        RepeatableReadTransaction tb = begin();
        Assert.assertEquals(tb.select(table, "aaa"), "aaa");
    }

    @Test
    public void testReadCommited_事务a插入数据不提交_事务b后创建查询不到() {
        RepeatableReadTransaction ta = begin();
        ta.insert(table, "aaa", "aaa");

        RepeatableReadTransaction tb = begin();
        Assert.assertEquals(tb.select(table, "aaa"), null);
    }

    @Test
    public void test_事务中删除记录后查询不到() {
        RepeatableReadTransaction t = begin();
        t.insert(table, "ttt", "ttt");
        t.commit();

        // 在事务中删除记录，不提交。查询数据，应该查不到。
        RepeatableReadTransaction ta = begin();
        ta.delete(table, "ttt");
        Assert.assertEquals(ta.select(table, "ttt"), null);
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

    @Test
    public void test_事务a先创建_可以看到事务b删除的记录() {

        RepeatableReadTransaction t = begin();
        t.insert(table, "ttt", "ttt");
        t.commit();

        // 事务A先于事务B
        RepeatableReadTransaction ta = begin();

        // 事务B，删除记录
        RepeatableReadTransaction tb = begin();
        tb.delete(table, "ttt");
        tb.commit();

        // 事务A看到的数据，仍然等于ttt
        Assert.assertEquals(ta.select(table, "ttt"), "ttt");
    }
}
