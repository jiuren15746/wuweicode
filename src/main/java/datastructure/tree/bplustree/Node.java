package datastructure.tree.bplustree;

import java.util.Arrays;

class Node {
    InternalNode parent;

    static class InternalNode extends Node {
        int maxDegree;
        int minDegree;
        // degree表示有多少个孩子? todo
        int degree;

        // 中间节点也用链表连接
        InternalNode pre;
        InternalNode next;

        long[] keys;
        Node[] children;
    }


    static class LeafNode extends Node {
        int maxSize;
        int minSize;
        int size = 0;
        LeafNode pre;
        LeafNode next;
        DictionaryPair[] dictionary;

        public LeafNode(int m, DictionaryPair dp) {
            this.maxSize = m;
            this.minSize = (int) Math.ceil(m / 2);
            this.dictionary = new DictionaryPair[m];
            this.insert(dp);
        }

        public LeafNode(int m, DictionaryPair[] dps, InternalNode parent) {
            this.parent = parent;
            this.maxSize = m;
            this.minSize = (int) Math.ceil(m / 2);
            this.dictionary = dps;
            for (int i = 0; i < dps.length; i++) {
                if (dps[i] != null) {
                    size++;
                } else {
                    break;
                }
            }
        }

        public boolean insert(DictionaryPair dp) {
            if (isFull()) {
                return false;
            }
            dictionary[size++] = dp;
            Arrays.sort(dictionary, 0, size);
            return true;
        }

        public boolean isFull() {
            return size == maxSize;
        }
    }

    static class DictionaryPair implements Comparable<DictionaryPair> {
        long key;
        double value;

        public DictionaryPair(long key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(DictionaryPair o) {
            return key == o.key ? 0 : (key < o.key ? -1 : 1);
        }
    }
}
