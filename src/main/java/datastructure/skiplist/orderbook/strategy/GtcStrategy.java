package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.*;
import datastructure.skiplist.orderbook.enums.OrderTypeEnum;
import static datastructure.skiplist.orderbook.MatchResult.*;


public class GtcStrategy implements ExecStrategy {

    protected boolean isAddLeftToOrderBook = true;
    //========

    @Override
    public MatchResult execOrder(MatchEngine engine, Order order) {
        MatchResult result = new MatchResult();
        OrderBook makerOrderBook = order.isBuy() ? engine.getSellOrderBook() : engine.getBuyOrderBook();
        long takerAmountEv = order.getAmountEv();

        while (takerAmountEv > 0) {
            OrderQueue makerOrderQueue = makerOrderBook.getFirst();

            // 没有流动性, 或价格不匹配
            if (null == makerOrderQueue || !isPriceMatch(order, makerOrderQueue)) {
                break;
            }

            for (Order makerOrder; takerAmountEv > 0 && (makerOrder = makerOrderQueue.peek()) != null; ) {
                if (takerAmountEv < makerOrder.getAmountEv()) {
                    // taker is over
                    result.addTrade(order, makerOrder, takerAmountEv);
                    makerOrder.decreaseAmount(takerAmountEv);
                    takerAmountEv = 0;
                } else {
                    // maker is over
                    result.addTrade(order, makerOrder, makerOrder.getAmountEv());
                    takerAmountEv -= makerOrder.getAmountEv();
                    makerOrderQueue.dequeue();
                }
            }

            if (makerOrderQueue.size() == 0) {
                makerOrderBook.removeFirst();
            }
        }

        // 设置结果
        if (takerAmountEv == 0) {
            result.setResult(RESULT_FULL_FILLED);
        } else {
            result.setResult(order.getAmountEv() == takerAmountEv ? RESULT_NOT_FILLED : RESULT_PARTIAL_FILLED);
            if (isAddLeftToOrderBook) {
                // 剩余order加入taker订单簿
                order.setAmountEv(takerAmountEv);
                OrderBook takerOrderBook = order.isBuy() ? engine.getBuyOrderBook() : engine.getSellOrderBook();
                takerOrderBook.addOrder(order);
            }
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
