package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.DirectionEnum;
import datastructure.skiplist.orderbook.enums.ExecStrategyEnum;
import datastructure.skiplist.orderbook.enums.OrderTypeEnum;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class GtcStrategyTest {

    private String symbol = "BTCUSD";
    private GtcStrategy strategy = new GtcStrategy();

    @Test
    public void first_buy_order() {
        MatchEngine engine = new MatchEngine(symbol);

        String orderId = "firstBuyOrder";
        long priceEv = 1000;
        long amountEv = 50;
        Order buyOrder = createBuyOrder(orderId, priceEv, amountEv);

        MatchResult matchResult = strategy.execOrder(engine, buyOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_NOT_FILLED);
        assertEquals(matchResult.getTradeList().size(), 0);

        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        assertNotNull(buyOrderQueue);
        assertEquals(buyOrderQueue.size(), 1);
    }

    @Test
    public void buyThenSell_equalAmount_both_over() {
        MatchEngine engine = new MatchEngine(symbol);

        final long amountEv = 50;
        Order buyOrder = createBuyOrder("buyOrder", 1000, amountEv);
        Order sellOrder = createSellOrder("sellOrder", 999, amountEv);

        strategy.execOrder(engine, buyOrder);
        MatchResult matchResult = strategy.execOrder(engine, sellOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
        assertEquals(matchResult.getTradeList().size(), 1);

        // check trade
        Trade trade = matchResult.getTradeList().get(0);
        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
        assertEquals(trade.getPriceEv(), buyOrder.getPriceEv());
        assertEquals(trade.getAmountEv(), amountEv);

        // check buy order book
        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        assertNull(buyOrderQueue);

        // check sell order book
        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
        assertNull(sellOrderQueue);
    }

    @Test
    public void sellThenBuy_equalAmount_both_over() {
        MatchEngine engine = new MatchEngine(symbol);

        final long amountEv = 50;
        Order buyOrder = createBuyOrder("buyOrder", 1000, amountEv);
        Order sellOrder = createSellOrder("sellOrder", 999, amountEv);

        strategy.execOrder(engine, sellOrder);
        MatchResult matchResult = strategy.execOrder(engine, buyOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
        assertEquals(matchResult.getTradeList().size(), 1);

        // check trade
        Trade trade = matchResult.getTradeList().get(0);
        assertEquals(trade.getTakerOrderId(), buyOrder.getOrderId());
        assertEquals(trade.getMakerOrderId(), sellOrder.getOrderId());
        assertEquals(trade.getPriceEv(), sellOrder.getPriceEv());
        assertEquals(trade.getAmountEv(), amountEv);

        // check buy order book
        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        assertNull(buyOrderQueue);

        // check sell order book
        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
        assertNull(sellOrderQueue);
    }

    private Order createBuyOrder(String orderId, long priceEv, long amountEv) {
        return createOrder(orderId, DirectionEnum.BUY, priceEv, amountEv);
    }

    private Order createSellOrder(String orderId, long priceEv, long amountEv) {
        return createOrder(orderId, DirectionEnum.SELL, priceEv, amountEv);
    }

    private Order createOrder(String orderId, DirectionEnum direction, long priceEv, long amountEv) {
        return Order.builder()
                .orderId(orderId)
                .direction(direction.getCode())
                .priceEv(priceEv)
                .amountEv(amountEv)
                .orderType(OrderTypeEnum.LIMIT.getCode())
                .execStrategy(ExecStrategyEnum.GTC.getCode())
                .build();
    }
}
