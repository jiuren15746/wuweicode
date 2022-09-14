package datastructure.skiplist.orderbook;

import datastructure.skiplist.orderbook.enums.*;
import datastructure.skiplist.orderbook.strategy.ExecStrategy;
import datastructure.skiplist.orderbook.strategy.GtcStrategy;
import lombok.Data;

@Data
public class MatchEngine {
    private static final ExecStrategy gtcStrategy = new GtcStrategy();

    private final String symbol;
    private final OrderBook buyOrders;
    private final OrderBook sellOrders;
    //========

    public MatchEngine(String symbol) {
        this.symbol = symbol;
        this.buyOrders = new OrderBook(symbol, DirectionEnum.BUY);
        this.sellOrders = new OrderBook(symbol, DirectionEnum.SELL);
    }

    public MatchResult match(Order order) {

        ExecStrategyEnum strategy = ExecStrategyEnum.getByCode(order.getExecStrategy());

        switch (strategy) {
            case GTC:
                return gtcStrategy.execOrder(this, order);
            default:
                return null;
        }
    }
}
