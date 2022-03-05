package datastructure.skiplist.v2;

import datastructure.skiplist.v2.Nodes.DataNode;
import datastructure.skiplist.v2.Nodes.HeadNode;

import java.util.ArrayList;
import java.util.List;


public class SkipListAlgo {

    /**
     * 给跳表增加一层.
     * @return 新的topHead
     */
    static public HeadNode addLevel(SkipList2 skiplist) {
        HeadNode newHead = new HeadNode(skiplist.getLevel() + 1);
        newHead.setDown(skiplist.getTopHead());
        skiplist.setTopHead(newHead);
        System.out.println("add level, " + newHead);
        return newHead;
    }

    /**
     * 在跳表中查找元素。OK
     * 如果跳表中有该节点，返回该节点的查找路径。
     * 如果跳表中没有该节点，返回插入位置的前一个节点的查找路径。
     *
     * @param skiplist 跳表
     * @param target
     * @return 从上到下的数据节点组成的数组。每层选取一个节点。
     * 如果跳表为空，则返回头结点
     */
    static public List<DataNode> find(SkipList2 skiplist, final int target) {
        // 查找指针，从topHead开始
        DataNode ptr = skiplist.getTopHead();
        List<DataNode> path = new ArrayList<>();

        // 两层循环。内层循环在level内搜索。外层循环用于不同level。
        while (ptr != null) {
            while ((ptr.getValue() == null || ptr.getValue() < target)
                    && ptr.getNext() != null
                    && ptr.getNext().getValue() <= target) {
                ptr = ptr.getNext();
            }
            // 节点加入path。下探到下层。
            path.add(ptr);
            ptr = ptr.getDown();
        }
        return path;
    }

    /**
     * 向跳表中插入元素。
     * @param skiplist
     * @param target 要插入的数据
     */
    static public void insert(SkipList2 skiplist, int target) {
        // 查找插入的路径
        List<DataNode> path = find(skiplist, target);
        DataNode previous;
        DataNode newNode = null;

        // 从底层向上层，在每一层插入节点
        for (int level = 0; level == 0 || isCreateIndex(); ++level) {
            if (skiplist.getLevel() >= level) {
                previous = path.get(skiplist.getLevel() - level);
            } else {
                previous = addLevel(skiplist);
            }
            // 插入新节点
            DataNode temp = new DataNode(previous.getLevel(), target, previous.getNext(), previous);
            previous.setNext(temp);
            temp.setDown(newNode);
            newNode = temp;
        }
    }

    static private boolean isCreateIndex() {
        long time = System.nanoTime();
        return time % 2 == 1;
    }

    /**
     * 删除一个数值。
     *
     * @return 如果数值不在跳表中，返回false。否则删除后，返回true。
     */
    static public boolean delete(SkipList2 skiplist, int target) {
        // 查找路径
        List<DataNode> path = find(skiplist, target);
        // 没找到
        if (path.get(path.size() - 1).getValue().intValue() != target) {
            return false;
        }

        for (int level = 0; level <= skiplist.getLevel(); ++level) {
            DataNode deleteNode = path.get(skiplist.getLevel() - level);
            if (deleteNode.getValue() != null && deleteNode.getValue().intValue() == target) {
                deleteNode.getPre().setNext(deleteNode.getNext());
                if (deleteNode.getNext() != null) {
                    deleteNode.getNext().setPre(deleteNode.getPre());
                }
            }
        }
        return true;
    }
}
