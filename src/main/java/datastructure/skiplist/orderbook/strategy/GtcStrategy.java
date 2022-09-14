package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.DirectionEnum;
import datastructure.skiplist.orderbook.enums.OrderType;


public class GtcStrategy implements ExecStrategy {

    @Override
    public MatchResult execOrder(MatchEngine engine, Order order) {
        OrderBook makerOrderBook =
                order.getDirection() == DirectionEnum.BUY.getCode()
                        ? engine.getSellOrders() : engine.getBuyOrders();

        long takerAmountEv = order.getAmountEv();

        for (;;) {
            OrderQueue orderQueue = makerOrderBook.getOrders().getFirst();
            if (null == orderQueue) {
                // 没有流动性 todo
                return new MatchResult();
            }

            // 价格不匹配
            if (!isPriceMatch(order, orderQueue)) {
                // todo
                return new MatchResult();
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
            makerOrderBook.getOrders().removeFirst();
        }
    }


    private boolean isPriceMatch(Order order, OrderQueue orderQueue) {
        if (order.getOrderType() == OrderType.MARKET.getCode()) {
            return true;
        }
        if (order.isBuy()) {
            return order.getPriceEv() >= orderQueue.peek().getPriceEv();
        } else {
            return orderQueue.peek().getPriceEv() >= order.getPriceEv();
        }
    }

}
