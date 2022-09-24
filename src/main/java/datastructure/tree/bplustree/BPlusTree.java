package datastructure.tree.bplustree;

import lombok.Getter;
import lombok.Setter;
import org.testng.Assert;
import static org.testng.Assert.*;


public class BPlusTree {
    @Getter
    private final int maxDegree;

    @Getter
    protected Node root;
    //========

    public BPlusTree(int maxDegree) {
        this.maxDegree = maxDegree;
        this.root = new Node(this, true);
    }



    public static void main(String[] args) {
        final int maxDegree = 4;
        BPlusTree tree = new BPlusTree(maxDegree);
        assertEquals(tree.getRoot().getDegree(), 0);

        long[] array = {8, 20, 10, 6, 30};
        for (long key : array) {
            tree.getRoot().insert(key, "" + key);
        }

        assertEquals(tree.getRoot().getDegree(), 2);
    }
}
