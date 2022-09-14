package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.OrderTypeEnum;


public class GtcStrategy implements ExecStrategy {

    @Override
    public MatchResult execOrder(MatchEngine engine, Order order) {
        MatchResult result = new MatchResult();
        OrderBook makerOrderBook = order.isBuy() ? engine.getSellOrders() : engine.getBuyOrders();
        long takerAmountEv = order.getAmountEv();

        for (;;) {
            OrderQueue orderQueue = makerOrderBook.getFirst();

            // 没有流动性, 或价格不匹配
            if (null == orderQueue || !isPriceMatch(order, orderQueue)) {
                break;
            }

            for (Order makerOrder; (makerOrder = orderQueue.peek()) != null; ) {
                if (takerAmountEv < makerOrder.getAmountEv()) {
                    // taker is over todo
                    makerOrder.decreaseAmount(takerAmountEv);
                    return new MatchResult();
                } else {
                    // maker is over
                    orderQueue.dequeue();
                    takerAmountEv -= makerOrder.getAmountEv();
                }
            }
            makerOrderBook.removeFirst();
        }

        // 剩余order加入taker订单簿
        order.setAmountEv(takerAmountEv);

        OrderBook takerOrderBook = order.isBuy() ? engine.getBuyOrders() : engine.getSellOrders();
        takerOrderBook.addOrder(order);
        return result;
    }


    private boolean isPriceMatch(Order order, OrderQueue orderQueue) {
        if (order.getOrderType() == OrderTypeEnum.MARKET.getCode()) {
            return true;
        }
        if (order.isBuy()) {
            return order.getPriceEv() >= orderQueue.peek().getPriceEv();
        } else {
            return orderQueue.peek().getPriceEv() >= order.getPriceEv();
        }
    }

}
