package datastructure.linkedlist.algo;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    static public ListNode arrayToList(int[] array) {
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

    static public int[] listToArray(ListNode head) {
        int length = 0;
        for (ListNode temp = head; temp != null; temp = temp.next) {
            length++;
        }

        ListNode temp = head;
        int[] array = new int[length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = temp.val;
            temp = temp.next;
        }
        return array;
    }
}
