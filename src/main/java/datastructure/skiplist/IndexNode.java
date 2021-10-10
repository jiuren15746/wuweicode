package datastructure.skiplist;

public class IndexNode {

    int       data;
    IndexNode previous;
    IndexNode next;

    IndexNode up;
    Object    down;

    public IndexNode(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public IndexNode getPrevious() {
        return previous;
    }

    public void setPrevious(IndexNode previous) {
        this.previous = previous;
    }

    public IndexNode getNext() {
        return next;
    }

    public void setNext(IndexNode next) {
        this.next = next;
    }

    public IndexNode getUp() {
        return up;
    }

    public void setUp(IndexNode up) {
        this.up = up;
    }

    public Object getDown() {
        return down;
    }

    public void setDown(Object down) {
        this.down = down;
    }

    @Override
    public String toString() {
        return "index(" + this.data + ")";
    }
}
