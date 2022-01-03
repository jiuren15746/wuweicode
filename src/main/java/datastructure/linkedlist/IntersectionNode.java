package datastructure.linkedlist;

import lombok.Data;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。
 * 如果两个链表不存在相交节点，返回 null 。
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 *
 * 该解法需要将两个链表反转。
 * 时间复杂度：O((m+n)*2)
 * 空间复杂度：O(m+n)
 */
public class IntersectionNode {
    static class ListNode {
        final int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }

    static public ListNode createList(int[] array) {
        ListNode head = null;
        ListNode tail = null;

        for (int i = 0; i < array.length; ++i) {
            ListNode temp = new ListNode(array[i]);
            if (head == null) {
                head = tail = temp;
            } else {
                tail.next = temp;
                tail = temp;
            }
        }
        return head;
    }

    static public ListNode getIntersection(ListNode a, ListNode b) {
        ListNode reverseA = reverseList(a);
        ListNode reverseB = reverseList(b);
        ListNode aPrevious = null;

        while (reverseA != null && reverseB != null
                && reverseA.val == reverseB.val) {
            aPrevious = reverseA;
            reverseA = reverseA.next;
            reverseB = reverseB.next;
        }
        return aPrevious;
    }

    static private ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        for (ListNode i = head; i != null; i = i.next) {
            ListNode tempCopy = new ListNode(i.val);
            tempCopy.next = newHead;
            newHead = tempCopy;
        }
        return newHead;
    }


    @Test
    public void test() {
        ListNode headA = createList(new int[]{4, 1, 8, 4, 5});
        ListNode headB = createList(new int[]{5, 6, 1, 8, 4, 5});

        ListNode node = getIntersection(headA, headB);
        Assert.assertEquals(node.val, 1);
    }

    @Test
    public void test2() {
        ListNode headA = createList(new int[]{1, 9, 1, 2, 4});
        ListNode headB = createList(new int[]{3, 2, 4});

        ListNode node = getIntersection(headA, headB);
        Assert.assertEquals(node.val, 2);
    }

    @Test
    public void test3() {
        ListNode headA = createList(new int[]{2,6,4});
        ListNode headB = createList(new int[]{1,5});

        ListNode node = getIntersection(headA, headB);
        Assert.assertEquals(node, null);
    }
}
