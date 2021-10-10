package datastructure.skiplist;

public class DataNode {

    private final int data;

    private DataNode previous;
    private DataNode next;
    private IndexNode indexNode;

    public DataNode(int value) {
        this.data = value;
        this.next = null;
        this.previous = null;
    }

    public int getData() {
        return data;
    }

    public DataNode getNext() {
        return next;
    }

    public void setNext(DataNode next) {
        this.next = next;
    }

    public DataNode getPrevious() {
        return previous;
    }

    public void setPrevious(DataNode previous) {
        this.previous = previous;
    }

    public IndexNode getIndexNode() {
        return indexNode;
    }

    public void setIndexNode(IndexNode indexNode) {
        this.indexNode = indexNode;
    }

    @Override
    public String toString() {
        return "data(" + this.data + ")";
    }
}
