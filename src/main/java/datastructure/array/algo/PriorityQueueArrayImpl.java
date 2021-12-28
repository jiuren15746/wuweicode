package datastructure.array.algo;

import datastructure.PriorityQueueJiuren;


public class PriorityQueueArrayImpl<T extends Comparable>
        implements PriorityQueueJiuren<T> {

    private Object[] array;
    private int head;
    private int tail;

    public PriorityQueueArrayImpl(int capacity) {
        array = new Comparable[capacity];


    }

    @Override
    public void offer(T element) {

    }

    @Override
    public T poll() {
        return null;
    }
}
