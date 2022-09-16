package datastructure.skiplist.orderbook.strategy;

public class IocStrategy extends GtcStrategy implements ExecStrategy {

    public IocStrategy() {
        super.isAddLeftToOrderBook = false;
    }
}
