package datastructure.skiplist;

import static datastructure.skiplist.SkipListJiuren.*;

public class Test {

    static public void main(String[] args) throws Exception {

        // insert
        SkipListJiuren skiplist = new SkipListJiuren();

        for (int i = 0; i < 100; ++i) {
            int data = (int) System.nanoTime() % 1000;
            skiplist.insert(data);
        }

        System.out.println(skiplist);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
