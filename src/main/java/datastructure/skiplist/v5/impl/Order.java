package datastructure.skiplist.v5.impl;

import lombok.Data;

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

    private int orderType;
    private int execStrategy;

}
