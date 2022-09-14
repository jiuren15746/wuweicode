package datastructure.skiplist.orderbook;

import lombok.Data;

/**
 * 进入撮合引擎的订单。
 */
@Data
public class Order {

    private String orderId;
    private String symbol;
    /**
     * see Direction
     */
    private byte direction;

    private long priceEv;
    private long amountEv;

    /**
     * see OrderType.
     */
    private int orderType;

    /**
     * see ExecStrategy
     */
    private int execStrategy;

}
