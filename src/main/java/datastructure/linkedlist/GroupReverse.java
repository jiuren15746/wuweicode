package datastructure.linkedlist;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * K个一组翻转链表。
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 * 难度：困难
 */
public class GroupReverse {

    static public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummyHead = new ListNode(0, head);
        ListNode current = head;
        ListNode pre = dummyHead;

        while (current != null) {
            // 如果剩余长度不足，不要翻转
            boolean isLengthEnough = true;
            ListNode temp = current;
            for (int i = 1; i <= k-1; ++i) {
                temp = temp.next;
                if (temp == null) {
                    isLengthEnough = false;
                    break;
                }
            }
            if (!isLengthEnough) {
                break;
            }
            // 原地翻转一组
            for (int i = 1; i <= k-1; ++i) {
                ListNode next = current.next;
                current.next = next.next;
                next.next = pre.next;
                pre.next = next;
            }
            // 准备翻转下一组
            pre = current;
            current = current.next;
        }
        return dummyHead.next;
    }


    @Test
    public void test() {
        ListNode head = ListNode.arrayToList(new int[]{1, 2, 3, 4, 5});
        final int k = 3;

        ListNode newHead = reverseKGroup(head, k);
        Assert.assertEquals(ListNode.listToArray(newHead), new int[]{3,2,1,4,5});
    }

}
