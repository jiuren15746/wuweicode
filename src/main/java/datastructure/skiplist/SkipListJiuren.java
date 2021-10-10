package datastructure.skiplist;


import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * 参考 https://www.cnblogs.com/wangbiaoneu/archive/2013/04/27/skiplist.html
 */
public class SkipListJiuren {

    private int count;

    private DataNode head;
    private IndexNode indexHead;

    SkipListJiuren() {
        DataNode head = new DataNode(Integer.MIN_VALUE);
        DataNode tail = new DataNode(Integer.MAX_VALUE);
        head.setNext(tail);
        tail.setPrevious(head);
        this.head = head;

        IndexNode indexHead = new IndexNode(Integer.MIN_VALUE);
        IndexNode indexTail = new IndexNode(Integer.MAX_VALUE);
        indexHead.setNext(indexTail);
        indexTail.setPrevious(indexHead);
        indexHead.setDown(head);
        indexTail.setDown(tail);
        this.indexHead = indexHead;
    }


    public boolean insert(int data) {
        // 找到索引节点
        IndexNode indexNode = findPositionInIndex(data);

        // 索引节点向下走, 找到数据位置, 并插入数据节点
        DataNode dataNode = findPosAfterDataNode(data, (DataNode) indexNode.getDown());
        DataNode newDataNode = insertAfter(dataNode, data);

        // 判断是否插入索引
        if (isAddToIndex()) {
            insertAfter(indexNode, data, newDataNode);
        }

        return true;
    }

    private IndexNode insertAfter(IndexNode node, final int data, DataNode dataNode) {
        IndexNode newNode = new IndexNode(data);
        newNode.setPrevious(node);
        newNode.setNext(node.getNext());
        node.setNext(newNode);

        newNode.setDown(dataNode);
        return newNode;
    }

    private DataNode insertAfter(DataNode node, final int data) {
        // 断言：data >= node节点数据 && data < node.next节点数据
        assertTrue(data >= node.getData());
        if (node.getNext() != null) {
            assertTrue(data < node.getNext().getData());
        }

        // 插入数据节点
        DataNode newNode = new DataNode(data);
        newNode.setNext(node.getNext());
        newNode.setPrevious(node);
        node.setNext(newNode);
        count++;
        return newNode;
    }

    public DataNode insertAfterHead(int data) {
        return insertAfter(head, data);
    }

    @Override
    public String toString() {

        List<Integer> datas = new ArrayList<>();
        for (DataNode i = head.getNext(); i.getNext() != null; i = i.getNext()) {
            datas.add(i.getData());
        }

        List<Integer> indeies = new ArrayList<>();
        for (IndexNode i = indexHead.getNext(); i.getNext() != null; i = i.getNext()) {
            indeies.add(i.getData());
        }

        return "data=" + datas + "\nindex=" + indeies;
    }

    static boolean isAddToIndex() {
        long now = System.nanoTime();
        boolean result = (now % 2 == 0);
        System.out.println(result);
        return result;
    }

    private IndexNode findPositionInIndex(int data) {
        for (IndexNode pos = indexHead; ; pos = pos.getNext()) {
            if (data >= pos.getData() && data < pos.getNext().getData()) {
                return pos;
            }
        }
    }

    private DataNode findPosAfterDataNode(int data, DataNode dataNode) {
        for (DataNode pos = dataNode; ; pos = pos.getNext()) {
            if (data >= pos.getData() && data < pos.getNext().getData()) {
                return pos;
            }
        }
    }
}
