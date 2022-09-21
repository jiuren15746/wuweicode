package datastructure.skiplist.orderbook.disruptor;

import lombok.Data;

@Data
public class MatchEvent {
    private Object eventRequest;

    public MatchEvent() {
        this.eventRequest = null;
    }
}
