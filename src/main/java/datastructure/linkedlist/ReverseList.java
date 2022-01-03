package datastructure.linkedlist;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 原地反转链表。
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * 难度：简单
 *
 * 思路：
 *  + 每次反转，把head右边第一个节点，放到左边链表头。直到head右边没有节点。
 *  + 考察对指针的理解。反转一个节点，要调整三个指针。
 *
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */
public class ReverseList {

    static public ListNode reverseList(ListNode head) {
        ListNode newHead = head;
        // 退出条件：head后面的节点都反转完了
        while (head != null && head.next != null) {
            ListNode reverseNode = head.next;
            head.next = reverseNode.next;
            reverseNode.next = newHead;
            newHead = reverseNode;
        }
        return newHead;
    }

    @Test
    public void test() {
        ListNode head = ListNode.arrayToList(new int[]{1, 2, 3, 4, 5});

        ListNode newHead = reverseList(head);
        Assert.assertEquals(ListNode.listToArray(newHead), new int[]{5, 4, 3, 2, 1});
    }
}
