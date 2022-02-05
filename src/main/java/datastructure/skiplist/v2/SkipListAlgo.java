package datastructure.skiplist.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import datastructure.skiplist.v2.SkipList2.*;


public class SkipListAlgo {

    /**
     * 给跳表增加一层.
     * @return 新的topHead
     */
    static public HeadNode addLevel(SkipList2 skiplist) {
        HeadNode newHead = new HeadNode(skiplist.getLevel() + 1, null);
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
        DataNode previous = null;
        DataNode newNode = null;

        // 从底层向上层，在每一层插入节点
        for (int level = 0; level == 0 || isCreateIndex(); ++level) {
            if (skiplist.getLevel() >= level) {
                previous = path.get(skiplist.getLevel() - level);
            } else {
                previous = addLevel(skiplist);
            }
            // 插入新节点
            newNode = new DataNode(previous.getLevel(), target, previous.getNext(), newNode);
            previous.setNext(newNode);
        }
    }

    static private boolean isCreateIndex() {
        long time = System.nanoTime();
        return time % 2 == 1;
    }
}
