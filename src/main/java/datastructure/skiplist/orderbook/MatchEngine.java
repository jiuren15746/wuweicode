package datastructure.skiplist.orderbook;

import datastructure.skiplist.orderbook.disruptor.MatchEngineDisruptor;
import datastructure.skiplist.orderbook.enums.DirectionEnum;
import datastructure.skiplist.orderbook.enums.ExecStrategyEnum;
import datastructure.skiplist.orderbook.strategy.ExecStrategy;
import datastructure.skiplist.orderbook.strategy.GtcStrategy;
import datastructure.skiplist.orderbook.strategy.IocStrategy;
import lombok.Data;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一个symbol对应一个撮合引擎。每个撮合引擎使用一个Disruptor。
 */
@Data
public class MatchEngine {
    public static final ExecStrategy GTC_STRATEGY = new GtcStrategy();
    public static final ExecStrategy IOC_STRATEGY = new IocStrategy();

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

    /**
     *
     * @param order
     * @return
     * @throws Throwable 如果当前线程等待响应时被中断，抛出 InterruptedException。
     *                   如果任务执行中抛出异常，抛出执行异常，可能是任何一种异常。
     */
    public MatchResult submit(Order order) throws Throwable {
        FutureTask futureTask = new FutureTask(() -> executeOrder(order));
        matchEngineDisruptor.publish(futureTask);
        try {
            return (MatchResult) futureTask.get();
        } catch (ExecutionException ee) {
            throw ee.getCause();
        }
    }

    public MatchResult executeOrder(Order order) {
        System.out.println(Thread.currentThread() + " -- execute order");

        switch (ExecStrategyEnum.getByCode(order.getExecStrategy())) {
            case GTC:
                return GTC_STRATEGY.execOrder(this, order);
            case IOC:
                return IOC_STRATEGY.execOrder(this, order);
            default:
                return null;
        }
    }

}
