package mysql.mvcc.readcommitted;

import mysql.mvcc.MVCCTable;
import mysql.mvcc.readcommited.ReadCommittedTransaction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static mysql.mvcc.readcommited.ReadCommittedTransaction.begin;

public class ReadCommittedTransactionTest {

    private MVCCTable table = new MVCCTable();

    @BeforeMethod
    public void startup() {
        table = new MVCCTable();
    }

    @Test
    public void test_单个事务() {
        ReadCommittedTransaction t = begin();

        // 插入后，可以查询到
        t.insert(table, "ttt", "ttt");
        Assert.assertEquals(t.select(table, "ttt"), "ttt");

        // 更新后，可以查询到最新值
        String newValue = "ttt1";
        t.update(table, "ttt", newValue);
        Assert.assertEquals(t.select(table, "ttt"), newValue);

        // 删除后，查询不到
        t.delete(table, "ttt");
        Assert.assertEquals(t.select(table, "ttt"), null);
    }

    // ReadCommitted测试。
    @Test
    public void testReadCommited_事务a插入数据并提交_事务b后创建可以查询到() {
        ReadCommittedTransaction ta = begin();
        ta.insert(table, "aaa", "aaa");
        ta.commit();

        ReadCommittedTransaction tb = begin();
        Assert.assertEquals(tb.select(table, "aaa"), "aaa");
    }

    // ReadCommitted测试。
    @Test
    public void testReadCommited_事务a插入数据不提交_事务b后创建查询不到() {
        ReadCommittedTransaction ta = begin();
        ta.insert(table, "aaa", "aaa");

        ReadCommittedTransaction tb = begin();
        Assert.assertEquals(tb.select(table, "aaa"), null);
    }

    // 事务a先创建。事务b插入记录并提交，事务a可以看到。
    @Test
    public void test_查询和插入并发_事务a查询_事务b插入_事务a看到记录() {
        ReadCommittedTransaction ta = begin();
        ReadCommittedTransaction tb = begin();

        // 事务b后创建，插入记录后提交
        String id = UUID.randomUUID().toString();
        tb.insert(table, id, "bbb");
        tb.commit();

        // 事务a看到记录
        Assert.assertEquals(ta.select(table, id), "bbb");
    }

    // 事务a先创建。事务a先插入记录并提交。事务b可以看到。
    @Test
    public void test_查询和插入并发_事务a插入记录并提交_事务b查询_可以查询到记录() {
        ReadCommittedTransaction ta = begin();
        ReadCommittedTransaction tb = begin();

        // 事务a先插入记录并提交。事务b可以看到。
        ta.insert(table, "aaa", "aaa");
        ta.commit();

        Assert.assertEquals(tb.select(table, "aaa"), "aaa");
    }

    // 事务a先创建。事务b更新记录。事务a看到更新值。
    @Test
    public void test_查询和更新并发_事务a先创建_事务b更新_a看到更新值() {
        ReadCommittedTransaction t = begin();
        String id = UUID.randomUUID().toString();
        t.insert(table, id, "ttt");
        t.commit();

        // 事务A先创建
        ReadCommittedTransaction ta = begin();
        ReadCommittedTransaction tb = begin();

        // 事务B更新记录
        tb.update(table, id, "bbb");
        tb.commit();

        // 事务A能看到更新值
        Assert.assertEquals(ta.select(table, id), "bbb");
    }

    // 事务a先创建。事务b更新记录但不提交。事务a只能看到更新前的值。
    @Test
    public void test_查询和更新并发_事务a先创建_事务b更新但不提交_a只能看到更新前的版本() {
        ReadCommittedTransaction t = begin();
        String id = UUID.randomUUID().toString();
        t.insert(table, id, "ttt");
        t.commit();

        // 事务A先创建
        ReadCommittedTransaction ta = begin();
        ReadCommittedTransaction tb = begin();

        // 事务B更新记录
        tb.update(table, id, "bbb");

        // 事务A只能看到旧版本
        Assert.assertEquals(ta.select(table, id), "ttt");
    }

    // 事务a先创建。事务b删除记录。事务a看不到记录。
    @Test
    public void test_查询和删除并发_事务a先创建_事务b删除记录_a看不到记录() {
        ReadCommittedTransaction t = begin();
        String id = UUID.randomUUID().toString();
        t.insert(table, id, "ttt");
        t.commit();

        // 事务A先于事务B
        ReadCommittedTransaction ta = begin();
        ReadCommittedTransaction tb = begin();

        Assert.assertEquals(ta.select(table, id), "ttt");

        // 事务B删除记录
        tb.delete(table, id);
        tb.commit();

        // 事务A看不到数据
        Assert.assertEquals(ta.select(table, id), null);
    }

    // 事务a先创建，事务b后创建。
    // 事务b修改数据。事务a只能看到修改前数据。
    // 然后事务a对数据加锁，再次select，可以看到最新的数据。
    @Test
    public void test_事务a先创建_事务b先修改记录_事务a后修改记录() {
        final String id = "ttt";
        ReadCommittedTransaction t = begin();
        t.insert(table, id, "ttt");
        t.commit();

        ReadCommittedTransaction ta = begin();
        ReadCommittedTransaction tb = begin();

        // 事务b修改记录，事务a能看到新值
        tb.update(table, id, "bbb");
        tb.commit();
        Assert.assertEquals(ta.select(table, id), "bbb");

        // 事务a后修改记录。修改后可以看到最新值
        ta.update(table, id, "aaa");
        Assert.assertEquals(ta.select(table, id), "aaa");
    }
}
