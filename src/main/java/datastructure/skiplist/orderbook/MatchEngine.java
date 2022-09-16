package datastructure.skiplist.orderbook;

import datastructure.skiplist.orderbook.enums.*;
import datastructure.skiplist.orderbook.strategy.ExecStrategy;
import datastructure.skiplist.orderbook.strategy.GtcStrategy;
import datastructure.skiplist.orderbook.strategy.IocStrategy;
import lombok.Data;

@Data
public class MatchEngine {
    private static final ExecStrategy gtcStrategy = new GtcStrategy();
    private static final ExecStrategy iocStrategy = new IocStrategy();

    private final String symbol;
    private final OrderBook buyOrderBook;
    private final OrderBook sellOrderBook;
    //========

    public MatchEngine(String symbol) {
        this.symbol = symbol;
        this.buyOrderBook = new OrderBook(symbol, DirectionEnum.BUY);
        this.sellOrderBook = new OrderBook(symbol, DirectionEnum.SELL);
    }

    public MatchResult match(Order order) {
        switch (ExecStrategyEnum.getByCode(order.getExecStrategy())) {
            case GTC:
                return gtcStrategy.execOrder(this, order);
            case IOC:
                return iocStrategy.execOrder(this, order);
            default:
                return null;
        }
    }
}
