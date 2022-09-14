package datastructure.skiplist.orderbook.strategy;

import datastructure.skiplist.orderbook.MatchEngine;
import datastructure.skiplist.orderbook.MatchResult;
import datastructure.skiplist.orderbook.Order;

public interface ExecStrategy {

    MatchResult execOrder(MatchEngine engine, Order order);
}
