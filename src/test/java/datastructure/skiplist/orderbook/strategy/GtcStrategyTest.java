package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.DirectionEnum;
import datastructure.skiplist.orderbook.enums.ExecStrategyEnum;
import datastructure.skiplist.orderbook.enums.OrderTypeEnum;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class GtcStrategyTest {

    private String symbol = "BTCUSD";

    @Test
    public void first_buy_order() throws Throwable {
        MatchEngine engine = new MatchEngine(symbol);

        String orderId = "firstBuyOrder";
        long priceEv = 1000;
        long amountEv = 50;
        Order buyOrder = createGtcBuyOrder(orderId, priceEv, amountEv);

        MatchResult matchResult = engine.submit(buyOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_NOT_FILLED);
        assertEquals(matchResult.getTradeList().size(), 0);

        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        assertNotNull(buyOrderQueue);
        assertEquals(buyOrderQueue.size(), 1);
    }

    @Test
    public void buyThenSell_equalAmount_both_over() throws Throwable {
        MatchEngine engine = new MatchEngine(symbol);

        final long amountEv = 50;
        Order buyOrder = createGtcBuyOrder("buyOrder", 1000L, amountEv);
        Order sellOrder = createGtcSellOrder("sellOrder", 999L, amountEv);

        engine.submit(buyOrder);
        MatchResult matchResult = engine.submit(sellOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
        assertEquals(matchResult.getTradeList().size(), 1);

        // check trade
        Trade trade = matchResult.getTradeList().get(0);
        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
        assertEquals(trade.getPriceEv(), buyOrder.getPriceEv());
        assertEquals(trade.getAmountEv(), amountEv);

        // check order book
        assertEquals(engine.getBuyOrderBook().size(), 0);
        assertNull(engine.getBuyOrderBook().getFirst());
        assertEquals(engine.getSellOrderBook().size(), 0);
        assertNull(engine.getSellOrderBook().getFirst());
    }

    @Test
    public void sellThenBuy_equalAmount_both_over() throws Throwable {
        MatchEngine engine = new MatchEngine(symbol);

        final long amountEv = 50;
        Order buyOrder = createGtcBuyOrder("buyOrder", 1000L, amountEv);
        Order sellOrder = createGtcSellOrder("sellOrder", 999L, amountEv);

        engine.submit(sellOrder);
        MatchResult matchResult = engine.submit(buyOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
        assertEquals(matchResult.getTradeList().size(), 1);

        // check trade
        Trade trade = matchResult.getTradeList().get(0);
        assertEquals(trade.getTakerOrderId(), buyOrder.getOrderId());
        assertEquals(trade.getMakerOrderId(), sellOrder.getOrderId());
        assertEquals(trade.getPriceEv(), sellOrder.getPriceEv());
        assertEquals(trade.getAmountEv(), amountEv);

        // check order book
        assertEquals(engine.getBuyOrderBook().size(), 0);
        assertNull(engine.getBuyOrderBook().getFirst());
        assertEquals(engine.getSellOrderBook().size(), 0);
        assertNull(engine.getSellOrderBook().getFirst());
    }

    @Test
    public void buyThenSell_priceNotMatch() throws Throwable {
        MatchEngine engine = new MatchEngine(symbol);

        final long amountEv = 50;
        Order buyOrder = createGtcBuyOrder("buyOrder", 999L, amountEv);
        Order sellOrder = createGtcSellOrder("sellOrder", 1000L, amountEv);

        engine.submit(buyOrder);
        MatchResult matchResult = engine.submit(sellOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_NOT_FILLED);
        assertEquals(matchResult.getTradeList().size(), 0);

        // check buy order book
        assertEquals(engine.getBuyOrderBook().size(), 1);
        assertEquals(engine.getSellOrderBook().size(), 1);
        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
        assertEquals(buyOrderQueue.size(), 1);
        assertEquals(sellOrderQueue.size(), 1);
    }

    @Test
    public void buyThenSell_priceMatch_partialFill() throws Throwable {
        MatchEngine engine = new MatchEngine(symbol);

        final long buyAmtEv = 100;
        final long sellAmtEv = 150;
        Order buyOrder = createGtcBuyOrder("buyOrder", 1000L, buyAmtEv);
        Order sellOrder = createGtcSellOrder("sellOrder", 999L, sellAmtEv);

//        strategy.execOrder(engine, buyOrder);
        engine.submit(buyOrder);
        MatchResult matchResult = engine.submit(sellOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_PARTIAL_FILLED);
        assertEquals(matchResult.getTradeList().size(), 1);

        // check trade
        Trade trade = matchResult.getTradeList().get(0);
        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
        assertEquals(trade.getPriceEv(), 1000);
        assertEquals(trade.getAmountEv(), 100);

        // check order book
        assertEquals(engine.getBuyOrderBook().size(), 0);
        assertEquals(engine.getSellOrderBook().size(), 1);
        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
        assertNull(buyOrderQueue);
        assertEquals(sellOrderQueue.size(), 1);
        // check order
        assertEquals(sellOrderQueue.peek().getAmountEv(), 50);
    }

    @Test
    public void buyThenSell_priceMatch_fullFill() throws Throwable {
        MatchEngine engine = new MatchEngine(symbol);

        final long buyAmtEv = 150;
        final long sellAmtEv = 100;
        Order buyOrder = createGtcBuyOrder("buyOrder", 1000L, buyAmtEv);
        Order sellOrder = createGtcSellOrder("sellOrder", 999L, sellAmtEv);

//        strategy.execOrder(engine, buyOrder);
//        MatchResult matchResult = strategy.execOrder(engine, sellOrder);

        engine.submit(buyOrder);
        MatchResult matchResult = engine.submit(sellOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
        assertEquals(matchResult.getTradeList().size(), 1);

        // check trade
        Trade trade = matchResult.getTradeList().get(0);
        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
        assertEquals(trade.getPriceEv(), 1000);
        assertEquals(trade.getAmountEv(), 100);

        // check order book
        assertEquals(engine.getBuyOrderBook().size(), 1);
        assertEquals(engine.getSellOrderBook().size(), 0);
        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
        assertEquals(buyOrderQueue.size(), 1);
        assertNull(sellOrderQueue);
        // check order
        assertEquals(buyOrderQueue.peek().getAmountEv(), 50);
    }

    protected static Order createGtcBuyOrder(String orderId, Long priceEv, long amountEv) {
        return createGtcOrder(orderId, DirectionEnum.BUY, priceEv, amountEv);
    }

    protected static Order createGtcSellOrder(String orderId, Long priceEv, long amountEv) {
        return createGtcOrder(orderId, DirectionEnum.SELL, priceEv, amountEv);
    }

    // priceEv传值表示现价单，空表示市价单
    protected static Order createGtcOrder(String orderId, DirectionEnum direction, Long priceEv, long amountEv) {
        return Order.builder()
                .orderId(orderId)
                .direction(direction.getCode())
                .priceEv(priceEv)
                .amountEv(amountEv)
                .orderType(null != priceEv ? OrderTypeEnum.LIMIT.getCode() : OrderTypeEnum.MARKET.getCode())
                .execStrategy(ExecStrategyEnum.GTC.getCode())
                .build();
    }
}
