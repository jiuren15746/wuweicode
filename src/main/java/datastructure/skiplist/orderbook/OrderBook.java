package datastructure.skiplist.orderbook;

import datastructure.skiplist.orderbook.enums.DirectionEnum;
import datastructure.skiplist.v5.SkipListV5;
import datastructure.skiplist.v5.impl.SkipListV5Impl;
import lombok.Data;

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

    private final String symbol;
    private final DirectionEnum direction;
    private final SkipListV5<OrderQueue> orders;
    //========

    public OrderBook(String symbol, DirectionEnum direction) {
        this.symbol = symbol;
        this.direction = direction;
        this.orders = new SkipListV5Impl(direction == DirectionEnum.BUY ? DESCEND : ASCEND);
    }

    public OrderQueue getFirst() {
        return orders.getFirst();
    }

    public void removeFirst() {
        orders.removeFirst();
    }
}
