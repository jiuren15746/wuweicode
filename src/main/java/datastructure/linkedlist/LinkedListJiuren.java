package datastructure.linkedlist;

public class LinkedListJiuren {
    ListNode head;
    ListNode tail;
    // =============

    /**
     * 在头部插入新节点
     */
    public void push(int value) {
        ListNode node = new ListNode(value);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = head;
        }
    }

//    // 在指定节点后插入
//    public void insertAfter(ListNode node, int value) {
//        ListNode temp = new ListNode(value);
//        temp.next = node.next;
//        node.next = temp;
//    }

    // 在尾部插入节点
    public void append(int value) {
        if (head == null) {
            tail = head = new ListNode(value);
        } else {
            tail.next = new ListNode(value);
        }
    }

    public int getLength() {
        int length = 0;
        for (ListNode node = head; node != null; node = node.next) {
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
