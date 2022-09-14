package datastructure.skiplist.v5.impl;


import datastructure.skiplist.v5.SkipListV5;

import java.util.Comparator;

public class OrderBook {

    private static Comparator<Long> ASCEND = new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o1.equals(o2) ? 0 : (o1 < o2 ? -1 : 1);
        }
    };

    private static Comparator<Long> DESCEND = new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o1.equals(o2) ? 0 : (o1 < o2 ? 1 : -1);
        }
    };

    private String symbol;
    private SkipListV5 buyOrders;
    private SkipListV5 sellOrders;

    public OrderBook(String symbol) {
        this.symbol = symbol;
        this.buyOrders = new SkipListV5Impl(DESCEND);
        this.sellOrders = new SkipListV5Impl(ASCEND);
    }
}
