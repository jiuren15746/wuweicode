package datastructure.skiplist.v3;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 跳表接口。
 */
public interface SkipListInterface3 {

    /**
     * 返回跳表最顶层level（最底层level为零，向上递增）。
     *
     * @return
     */
    int getTopLevel();

    /**
     * 返回元素个数
     */
    int getSize();

    /**
     * 在跳表中查找元素。返回查找路径。
     * 如果跳表中有该节点，返回该节点的查找路径。
     * 如果跳表中没有该节点，返回目标位置的前一个节点的查找路径。
     */
    List<SkipNode> find(int value);

    /**
     * 插入一个数值。
     */
    void insert(int value);
//
//    /**
//     * 删除一个数值。
//     * @return 是否删除成功，没找到认为删除失败，返回false
//     */
//    boolean delete(int value);


    /**
     * 跳表节点。包含数据，以及多层索引指针。
     */
    class SkipNode {

        protected final int value;

        protected SkipNode[] next;
        protected SkipNode[] pre;

        public SkipNode(int value) {
            this.value = value;
        }

        public SkipNode getNext(int level) {
            if (next != null && next.length -1 >= level) {
                return next[level];
            } else {
                return null;
            }
        }

        @Override
        public String toString() {
            return "DataNode(" + value + ")";
        }
    }

}
