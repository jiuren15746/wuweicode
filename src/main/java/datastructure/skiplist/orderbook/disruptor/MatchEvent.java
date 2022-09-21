package datastructure.skiplist.orderbook.disruptor;

import lombok.Data;

import java.util.concurrent.FutureTask;

@Data
public class MatchEvent {
//    public static final int ORDER_EVENT = 1;

    private FutureTask futureTask;

    public MatchEvent() {
        this.futureTask = null;
    }
}
