package datastructure.linkedlist;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static datastructure.linkedlist.LinkedListJiuren.*;

public class LinkedListJiurenTest {
    @Test
    public void test_push() {
        LinkedListJiuren list = new LinkedListJiuren();
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        list.push(5);

        Assert.assertNotNull(list.head);
        Assert.assertNotNull(list.tail);
        Assert.assertEquals(list.head.data, 5);
        Assert.assertEquals(list.tail.data, 1);

        Assert.assertEquals(list.getLength(), 5);
        Assert.assertEquals(list.getLength_recursive(list.head), 5);
    }

    @Test
    public void test_append() {
        LinkedListJiuren list = new LinkedListJiuren();
        list.append(1);
        list.append(2);
        list.append(3);
        list.append(4);
        list.append(5);

        Assert.assertNotNull(list.head);
        Assert.assertNotNull(list.tail);
        Assert.assertEquals(list.head.data, 1);
        Assert.assertEquals(list.tail.data, 5);

        Assert.assertEquals(list.getLength(), 5);
        Assert.assertEquals(list.getLength_recursive(list.head), 5);

        Assert.assertEquals(toList(list.head), Lists.newArrayList(1, 2, 3, 4, 5));
    }

    @Test
    public void test_getPrevious() {
        LinkedListJiuren list = new LinkedListJiuren();
        list.append(1);
        list.append(3);
        list.append(1);
        list.append(2);
        list.append(1);

        Assert.assertEquals(getPrevious(list.head, list.head.next), list.head);
        Assert.assertEquals(getPrevious(list.head, list.tail).data, 2);
        Assert.assertEquals(getPrevious(list.head, list.head), null);
    }


    @Test
    public void test_quicksort() {
        LinkedListJiuren list = new LinkedListJiuren();
        list.append(6);
        list.append(5);
        list.append(2);
        list.append(7);
        list.append(1);
        list.append(3);
        list.append(9);
        list.append(8);
        list.append(4);

        ListNode[] result = quickSort(list.head, list.tail);
        ListNode head = result[0];

        System.out.println(toList(head));
    }


    @Test
    public void test_quicksort_swapValue() {
        LinkedListJiuren list = LinkedListJiuren.createList(new int[]{5,4,8,7,3,6,5,2});

        list.quickSort_swapValue(list.head, list.tail);

        System.out.println(toList(list.head));
    }
}
