package datastructure.skiplist.v2;

import datastructure.skiplist.v2.Nodes.*;

import java.util.ArrayList;
import java.util.List;

public class Algo {
    /**
     * 在跳表中查找元素。OK
     * @param topHead 最上层的头结点
     * @param target
     * @return 从上到下的数据节点组成的数组。每层选取一个节点。
     */
    static public List<DataNode> find(HeadNode topHead, final int target) {
        // 查找起点从head开始
        DataNode ptr = topHead;
        List<DataNode> path = new ArrayList<>();

        while (ptr != null) {
            while ((ptr.getValue() == null || ptr.getValue() < target)
                    && ptr.getNext() != null && ptr.getNext().getValue() <= target) {
                ptr = ptr.getNext();
            }
            // 节点加入path
            path.add(ptr);
            // 在下一层继续搜索
            ptr = ptr.getDown();
        }
        return path;
    }


    /**
     * 向跳表中插入元素。
     * @param headNodes 从上向下的头结点数组。
     * @param target
     */
    static public void insert(HeadNode[] headNodes, int target) {

    }



    static public void main(String[] args) {
        DataNode l0_10 = new DataNode(0, 10, null);
        DataNode l0_9 = new DataNode(0, 9, l0_10);
        DataNode l0_8 = new DataNode(0, 8, l0_9);
        DataNode l0_7 = new DataNode(0, 7, l0_8);
        DataNode l0_6 = new DataNode(0, 6, l0_7);
        DataNode l0_4 = new DataNode(0, 4, l0_6);
        DataNode l0_3 = new DataNode(0, 3, l0_4);
        DataNode l0_1 = new DataNode(0, 1, l0_3);
        HeadNode l0_head = new HeadNode(0, l0_1);

        DataNode l1_9 = new DataNode(1, 9, null, l0_9);
        DataNode l1_6 = new DataNode(1, 6, l1_9, l0_6);
        DataNode l1_3 = new DataNode(1, 3, l1_6, l0_3);
        HeadNode l1_head = new HeadNode(1, l1_3, l0_head);

        DataNode l2_9 = new DataNode(2, 9, null, l1_9);
        DataNode l2_6 = new DataNode(2, 6, l2_9, l1_6);
        HeadNode l2_head = new HeadNode(2, l2_6, l1_head);



        List<DataNode> path = find(l2_head, 4);
        System.out.println("path for 4: " + path);

        path = find(l2_head, 9);
        System.out.println("path for 9: " + path);

        path = find(l2_head, 100);
        System.out.println("path for 100: " + path);

        path = find(l2_head, -100);
        System.out.println("path for -100: " + path);
    }
}
