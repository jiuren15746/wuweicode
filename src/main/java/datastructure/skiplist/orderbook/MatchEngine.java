package datastructure.skiplist.orderbook;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import datastructure.skiplist.orderbook.disruptor.MatchEngineDisruptor;
import datastructure.skiplist.orderbook.enums.*;
import datastructure.skiplist.orderbook.strategy.ExecStrategy;
import datastructure.skiplist.orderbook.strategy.GtcStrategy;
import datastructure.skiplist.orderbook.strategy.IocStrategy;
import lombok.Data;

import java.util.concurrent.ThreadFactory;

/**
 * 一个symbol对应一个撮合引擎。每个撮合引擎使用一个Disruptor。
 */
@Data
public class MatchEngine {
    private static final ExecStrategy gtcStrategy = new GtcStrategy();
    private static final ExecStrategy iocStrategy = new IocStrategy();

    private final String symbol;
    private final OrderBook buyOrderBook;
    private final OrderBook sellOrderBook;

    private MatchEngineDisruptor matchEngineDisruptor;
    //========

    public MatchEngine(String symbol) {
        this.symbol = symbol;
        this.buyOrderBook = new OrderBook(symbol, DirectionEnum.BUY);
        this.sellOrderBook = new OrderBook(symbol, DirectionEnum.SELL);

        matchEngineDisruptor = new MatchEngineDisruptor(this);
    }

    public MatchResult execute(Order order) {
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
