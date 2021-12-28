package datastructure;

/**
 * 优先级队列接口。
 */
public interface PriorityQueueJiuren<T extends Comparable> {

    /**
     * 向优先级队列插入元素
     * @param element
     */
    void offer(T element);

    /**
     * 从优先级队列取元素
     * @return
     */
    T poll();
}
