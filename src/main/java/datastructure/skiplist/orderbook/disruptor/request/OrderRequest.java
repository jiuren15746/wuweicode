package datastructure.skiplist.orderbook.disruptor.request;

import datastructure.skiplist.orderbook.Order;
import lombok.Data;

@Data
public class OrderRequest {
    private final Order order;

    public OrderRequest(Order order) {
        this.order = order;
    }
}
