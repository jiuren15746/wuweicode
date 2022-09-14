package datastructure.skiplist.orderbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进入撮合引擎的订单。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
