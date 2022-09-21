package datastructure.skiplist.orderbook.disruptor;

import com.lmax.disruptor.EventHandler;
import datastructure.skiplist.orderbook.MatchEngine;


public class MatchEventHandler implements EventHandler<MatchEvent> {

    private MatchEngine engine;

    public MatchEventHandler(MatchEngine engine) {
        this.engine = engine;
    }

    @Override
    public void onEvent(MatchEvent matchEvent, long sequence, boolean endOfBatch) {
        System.out.println("handled event request: " + matchEvent.getEventRequest());
    }
}
