package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.DirectionEnum;
import datastructure.skiplist.orderbook.enums.ExecStrategyEnum;
import datastructure.skiplist.orderbook.enums.OrderTypeEnum;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class IocStrategyTest {

    private String symbol = "BTCUSD";
    private GtcStrategy strategy = new IocStrategy();

    @Test
    public void first_buy_order() {
        MatchEngine engine = new MatchEngine(symbol);

        String orderId = "firstBuyOrder";
        long priceEv = 1000;
        long amountEv = 50;
        Order iocBuyOrder = createIocBuyOrder(orderId, priceEv, amountEv);

        // IOC订单，撮合不成功，不加入订单簿
        MatchResult matchResult = strategy.execOrder(engine, iocBuyOrder);

        // 校验result
        assertEquals(matchResult.getResult(), MatchResult.RESULT_NOT_FILLED);
        assertEquals(matchResult.getTradeList().size(), 0);

        // 订单簿为空
        assertEquals(engine.getBuyOrderBook().size(), 0);
        assertEquals(engine.getSellOrderBook().size(), 0);
    }

//    @Test
//    public void buyThenSell_equalAmount_both_over() {
//        MatchEngine engine = new MatchEngine(symbol);
//
//        final long amountEv = 50;
//        Order buyOrder = createBuyOrder("buyOrder", 1000, amountEv);
//        Order sellOrder = createSellOrder("sellOrder", 999, amountEv);
//
//        strategy.execOrder(engine, buyOrder);
//        MatchResult matchResult = strategy.execOrder(engine, sellOrder);
//
//        // 校验result
//        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
//        assertEquals(matchResult.getTradeList().size(), 1);
//
//        // check trade
//        Trade trade = matchResult.getTradeList().get(0);
//        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
//        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
//        assertEquals(trade.getPriceEv(), buyOrder.getPriceEv());
//        assertEquals(trade.getAmountEv(), amountEv);
//
//        // check order book
//        assertEquals(engine.getBuyOrderBook().size(), 0);
//        assertNull(engine.getBuyOrderBook().getFirst());
//        assertEquals(engine.getSellOrderBook().size(), 0);
//        assertNull(engine.getSellOrderBook().getFirst());
//    }
//
//    @Test
//    public void sellThenBuy_equalAmount_both_over() {
//        MatchEngine engine = new MatchEngine(symbol);
//
//        final long amountEv = 50;
//        Order buyOrder = createBuyOrder("buyOrder", 1000, amountEv);
//        Order sellOrder = createSellOrder("sellOrder", 999, amountEv);
//
//        strategy.execOrder(engine, sellOrder);
//        MatchResult matchResult = strategy.execOrder(engine, buyOrder);
//
//        // 校验result
//        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
//        assertEquals(matchResult.getTradeList().size(), 1);
//
//        // check trade
//        Trade trade = matchResult.getTradeList().get(0);
//        assertEquals(trade.getTakerOrderId(), buyOrder.getOrderId());
//        assertEquals(trade.getMakerOrderId(), sellOrder.getOrderId());
//        assertEquals(trade.getPriceEv(), sellOrder.getPriceEv());
//        assertEquals(trade.getAmountEv(), amountEv);
//
//        // check order book
//        assertEquals(engine.getBuyOrderBook().size(), 0);
//        assertNull(engine.getBuyOrderBook().getFirst());
//        assertEquals(engine.getSellOrderBook().size(), 0);
//        assertNull(engine.getSellOrderBook().getFirst());
//    }
//
//    @Test
//    public void buyThenSell_priceNotMatch() {
//        MatchEngine engine = new MatchEngine(symbol);
//
//        final long amountEv = 50;
//        Order buyOrder = createBuyOrder("buyOrder", 999, amountEv);
//        Order sellOrder = createSellOrder("sellOrder", 1000, amountEv);
//
//        strategy.execOrder(engine, buyOrder);
//        MatchResult matchResult = strategy.execOrder(engine, sellOrder);
//
//        // 校验result
//        assertEquals(matchResult.getResult(), MatchResult.RESULT_NOT_FILLED);
//        assertEquals(matchResult.getTradeList().size(), 0);
//
//        // check buy order book
//        assertEquals(engine.getBuyOrderBook().size(), 1);
//        assertEquals(engine.getSellOrderBook().size(), 1);
//        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
//        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
//        assertEquals(buyOrderQueue.size(), 1);
//        assertEquals(sellOrderQueue.size(), 1);
//    }
//
//    @Test
//    public void buyThenSell_priceMatch_partialFill() {
//        MatchEngine engine = new MatchEngine(symbol);
//
//        final long buyAmtEv = 100;
//        final long sellAmtEv = 150;
//        Order buyOrder = createBuyOrder("buyOrder", 1000, buyAmtEv);
//        Order sellOrder = createSellOrder("sellOrder", 999, sellAmtEv);
//
//        strategy.execOrder(engine, buyOrder);
//        MatchResult matchResult = strategy.execOrder(engine, sellOrder);
//
//        // 校验result
//        assertEquals(matchResult.getResult(), MatchResult.RESULT_PARTIAL_FILLED);
//        assertEquals(matchResult.getTradeList().size(), 1);
//
//        // check trade
//        Trade trade = matchResult.getTradeList().get(0);
//        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
//        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
//        assertEquals(trade.getPriceEv(), 1000);
//        assertEquals(trade.getAmountEv(), 100);
//
//        // check order book
//        assertEquals(engine.getBuyOrderBook().size(), 0);
//        assertEquals(engine.getSellOrderBook().size(), 1);
//        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
//        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
//        assertNull(buyOrderQueue);
//        assertEquals(sellOrderQueue.size(), 1);
//        // check order
//        assertEquals(sellOrderQueue.peek().getAmountEv(), 50);
//    }
//
//    @Test
//    public void buyThenSell_priceMatch_fullFill() {
//        MatchEngine engine = new MatchEngine(symbol);
//
//        final long buyAmtEv = 150;
//        final long sellAmtEv = 100;
//        Order buyOrder = createBuyOrder("buyOrder", 1000, buyAmtEv);
//        Order sellOrder = createSellOrder("sellOrder", 999, sellAmtEv);
//
//        strategy.execOrder(engine, buyOrder);
//        MatchResult matchResult = strategy.execOrder(engine, sellOrder);
//
//        // 校验result
//        assertEquals(matchResult.getResult(), MatchResult.RESULT_FULL_FILLED);
//        assertEquals(matchResult.getTradeList().size(), 1);
//
//        // check trade
//        Trade trade = matchResult.getTradeList().get(0);
//        assertEquals(trade.getTakerOrderId(), sellOrder.getOrderId());
//        assertEquals(trade.getMakerOrderId(), buyOrder.getOrderId());
//        assertEquals(trade.getPriceEv(), 1000);
//        assertEquals(trade.getAmountEv(), 100);
//
//        // check order book
//        assertEquals(engine.getBuyOrderBook().size(), 1);
//        assertEquals(engine.getSellOrderBook().size(), 0);
//        OrderQueue buyOrderQueue = engine.getBuyOrderBook().getFirst();
//        OrderQueue sellOrderQueue = engine.getSellOrderBook().getFirst();
//        assertEquals(buyOrderQueue.size(), 1);
//        assertNull(sellOrderQueue);
//        // check order
//        assertEquals(buyOrderQueue.peek().getAmountEv(), 50);
//    }

    private Order createIocBuyOrder(String orderId, Long priceEv, long amountEv) {
        return createIocOrder(orderId, DirectionEnum.BUY, priceEv, amountEv);
    }

    private Order createIocSellOrder(String orderId, Long priceEv, long amountEv) {
        return createIocOrder(orderId, DirectionEnum.SELL, priceEv, amountEv);
    }

    // priceEv传值表示现价单，空表示市价单
    protected static Order createIocOrder(String orderId, DirectionEnum direction, Long priceEv, long amountEv) {
        return Order.builder()
                .orderId(orderId)
                .direction(direction.getCode())
                .priceEv(priceEv)
                .amountEv(amountEv)
                .orderType(null != priceEv ? OrderTypeEnum.LIMIT.getCode() : OrderTypeEnum.MARKET.getCode())
                .execStrategy(ExecStrategyEnum.IOC.getCode())
                .build();
    }
}
