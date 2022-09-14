package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.OrderTypeEnum;
import static datastructure.skiplist.orderbook.MatchResult.*;


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

            for (Order makerOrder; takerAmountEv > 0 && (makerOrder = orderQueue.peek()) != null; ) {
                if (takerAmountEv < makerOrder.getAmountEv()) {
                    // taker is over
                    result.addTrade(order, makerOrder, takerAmountEv);
                    makerOrder.decreaseAmount(takerAmountEv);
                    takerAmountEv = 0;
                } else {
                    // maker is over
                    result.addTrade(order, makerOrder, makerOrder.getAmountEv());
                    takerAmountEv -= makerOrder.getAmountEv();
                    orderQueue.dequeue();
                }
            }

            if (takerAmountEv == 0) {
                break;
            } else {
                makerOrderBook.removeFirst();
            }
        }

        // 设置结果
        if (takerAmountEv == 0) {
            result.setResult(RESULT_FULL_FILLED);
        } else {
            result.setResult(order.getAmountEv() == takerAmountEv ? RESULT_NOT_FILLED : RESULT_PARTIAL_FILLED);
            // 剩余order加入taker订单簿
            order.setAmountEv(takerAmountEv);
            OrderBook takerOrderBook = order.isBuy() ? engine.getBuyOrders() : engine.getSellOrders();
            takerOrderBook.addOrder(order);
        }
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
