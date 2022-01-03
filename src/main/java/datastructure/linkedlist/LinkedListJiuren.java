package datastructure.linkedlist;

public class LinkedListJiuren {
    // 有一个固定头结点. 便于append
    ListNode head = new ListNode(-1);
    // =============

    /**
     * 在头部插入新节点
     */
    public void push(int value) {
        ListNode node = new ListNode(value);
        node.next = head.next;
        head.next = node;
    }

    // 在指定节点后插入
    public void insertAfter(ListNode node, int value) {
        ListNode temp = new ListNode(value);
        temp.next = node.next;
        node.next = temp;
    }

    // 在尾部插入节点
    public void append(int value) {
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = new ListNode(value);
    }

    public int getLength() {
        int length = 0;
        for (ListNode node = head.next; node != null; node = node.next) {
            length++;
        }
        return length;
    }

    // 递归解法，求长度
    public int getLength_recursive(ListNode node) {
        if (node == null) return 0;
        return 1 + getLength_recursive(node.next);
    }
}
