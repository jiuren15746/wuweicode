package datastructure.skiplist.orderbook.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import datastructure.skiplist.orderbook.MatchEngine;

import java.util.concurrent.ThreadFactory;

public class MatchEngineDisruptor {

    private final MatchEngine matchEngine;
    private Disruptor<MatchEvent> disruptor;
    private RingBuffer<MatchEvent> ringBuffer;
    //========

    public MatchEngineDisruptor(MatchEngine matchEngine) {
        this.matchEngine = matchEngine;
        int bufferSize = 1024;
        disruptor = new Disruptor<>(
                MatchEvent::new, bufferSize, new ConsumerThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
        ringBuffer = disruptor.getRingBuffer();

        disruptor.handleEventsWith(new MatchEventHandler(matchEngine));
        disruptor.start();
    }

    public void publishEvent(Object eventRequest) {
        long sequence = ringBuffer.next();
        ringBuffer.get(sequence).setEventRequest(eventRequest);
        ringBuffer.publish(sequence);
    }

    class ConsumerThreadFactory implements ThreadFactory {
        private int index = 0;
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("MatchEngine-" + matchEngine.getSymbol() + "-disruptor-consumer-" + index++);
            t.setDaemon(false);
            return t;
        }
    }



    public static void main(String[] args) {
        MatchEngine engine = new MatchEngine("BTCUSD");
        MatchEngineDisruptor matchEngineDisruptor = new MatchEngineDisruptor(engine);

        for (int i = 0; i < 1000; ++i) {
            matchEngineDisruptor.publishEvent("This is a random request " + i);
        }

        System.out.println("");
    }
}
