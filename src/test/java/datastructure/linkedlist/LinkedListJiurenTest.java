package datastructure.linkedlist;


import org.testng.Assert;
import org.testng.annotations.Test;

public class LinkedListJiurenTest {
    @Test
    public void test_push() {
        LinkedListJiuren list = new LinkedListJiuren();
        list.push(1);
        list.push(3);
        list.push(1);
        list.push(2);
        list.push(1);

        Assert.assertEquals(list.getLength(), 5);
        Assert.assertEquals(list.getLength_recursive(list.head.next), 5);
    }
}
