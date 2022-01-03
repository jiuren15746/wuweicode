package datastructure.linkedlist;

import org.testng.collections.Lists;

import java.util.List;

public class LinkedListJiuren {
    ListNode head;
    ListNode tail;
    // =============

    // 从数组创建链表
    static public LinkedListJiuren createList(int[] array) {
        LinkedListJiuren list = new LinkedListJiuren();
        for (int val : array) {
            list.push(val);
        }
        return list;
    }

    /**
     * 在头部插入新节点
     */
    public void push(int value) {
        if (head == null) {
            head = tail = new ListNode(value);
        } else {
            ListNode node = new ListNode(value);
            node.next = head;
            head = node;
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
            head = tail = new ListNode(value);
        } else {
            tail = tail.next = new ListNode(value);
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

    static ListNode getPrevious(ListNode head, ListNode node) {
        ListNode i = head;
        while (i != null && i.next != node) {
            i = i.next;
        }
        return i;
    }

    static List<Integer> toList(ListNode head) {
        List<Integer> result = Lists.newArrayList();
        for (ListNode i = head; i != null; i = i.next) {
            result.add(i.data);
        }
        return result;
    }



    static public ListNode[] quickSort(ListNode head, ListNode tail) {
        int base = tail.data;
        ListNode baseNode = tail;
        ListNode pre = null;

        for (ListNode i = head; i != baseNode; ) {
            System.out.println(toList(head) + ", node=" + i.data);

            if (i.data < base) {
                pre = i;
                i = i.next;
                continue;
            }
            // 从链表移除
            if (pre != null) { // 从中间移除
                pre.next = i.next;
            } else { // 从表头移除
                head = i.next;
            }
            i.next = null;

            // 放到链尾
            tail = tail.next = i;

            // 继续下一个节点
            if (pre != null) {
                i = pre.next;
            } else {
                i = head;
            }
        }
        System.out.println(toList(head) + ", baseNode=" + base);

        ListNode basePre = getPrevious(head, baseNode);
        ListNode baseNext = baseNode.next;

        // 对左半边递归调用
        if (basePre != null && head != basePre) {
            basePre.next = null;
            ListNode[] leftResult = quickSort(head, basePre);
            head = leftResult[0];
            leftResult[1].next = baseNode;
        }

        // 对右半边递归调用
        if (baseNext != null && baseNext != tail) {
            ListNode[] rightResult = quickSort(baseNext, tail);
            baseNode.next = rightResult[0];
            tail = rightResult[1];
        }
        return new ListNode[]{head, tail};
    }



//    public void quickSort2(ListNode begin, ListNode end) {
//        //判断为空，或者只有一个节点
//        if (begin == null || end == null || begin == end) {
//            return;
//        }
//        //从第一个节点和第一个节点的下个节点
//        ListNode first = begin;
//        ListNode second = begin.next;
//        int nMidValue = begin.data;
//
//        //结束条件，second到最后了
//        while (second != end.next && second != null) {
//            if (second.data < nMidValue) {
//                first = first.next;
//                //判断一下，避免后面的数比第一个数小，不用换的局面
//                if (first != second) {
//                    int temp = first.data;
//                    first.data = second.data;
//                    second.data = temp;
//                }
//            }
//            second = second.next;
//        }
//        //判断，有些情况是不用换的，提升性能
//        if (begin != first) {
//            int temp = begin.data;
//            begin.data = first.data;
//            first.data = temp;
//        }
//        //前部分递归
//        quickSort2(begin, first);
//        //后部分递归
//        quickSort2(first.next, end);
//    }


    public void quickSort_swapValue(ListNode begin, ListNode end) {
        if (begin == null || end == null || begin == end) {
            return;
        }

        int base = begin.data;
        ListNode p = begin;
        for (ListNode i = begin; i != end.next; i = i.next) {
            if (i.data < base) {
                p = p.next;
                swap(p, i);
            }
        }
        swap(p, begin);

        ListNode pre = getPrevious(begin, p);
        quickSort_swapValue(begin, pre);
        quickSort_swapValue(p.next, end);
    }

    private void swap(ListNode a, ListNode b) {
        int temp = a.data;
        a.data = b.data;
        b.data = temp;
    }

}
