package datastructure.linkedlist.algo;

import datastructure.linkedlist.algo.IntersectionNode.ListNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import static datastructure.linkedlist.algo.IntersectionNode.createList;

/**
 * 该解法脑洞有点大。
 * 假设a链表和b链表相交的部分为c，a链表左边的部分为a-c。b链表左边的部分为b-c。
 * 指针i从a链表开始，走完a链表后开始走b链表。走过的路径为: a+b-c+c
 * 指针j从b链表开始，走完b链表后开始走a链表。做过的路径为：b+a-c+c
 * 看到了么，指针i和j最终会经过一段相同的路径。他们的第一个交点即为答案。
 *
 * 时间复杂度：O(m+n)
 * 空间复杂度：O(1)，因为没有使用额外空间
 */
public class IntersectionNode2 {

    static public ListNode getIntersection(ListNode headA, ListNode headB) {
        ListNode i = headA;
        ListNode j = headB;
        boolean iChangeList = false;

        for (;;) {
            if (i == j) { // 找到交点. ！！！注意一定要使用地址比较
                return i;
            }

            i = i.next;
            j = j.next;
            if (i == null) {
                if (iChangeList) {
                    return null;
                } else {
                    i = headB;
                    iChangeList = true;
                }
            }
            if (j == null) {
                j = headA;
            }
        }
    }

    @Test
    public void test() {
        ListNode headA = createList(new int[]{4, 1});
        ListNode headB = createList(new int[]{5, 6, 1});
        ListNode c = createList(new int[]{8, 4, 5});
        append(headA, c);
        append(headB, c);

        ListNode node = getIntersection(headA, headB);
        Assert.assertEquals(node.val, 8);
    }

    @Test
    public void test2() {
        ListNode headA = createList(new int[]{1, 9, 1});
        ListNode headB = createList(new int[]{3});
        ListNode c = createList(new int[]{2, 4});
        append(headA, c);
        append(headB, c);

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

    static private void append(ListNode before, ListNode after) {
        ListNode temp = before;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = after;
    }
}
