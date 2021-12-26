package concurrency.blockingqueue;

import java.util.List;

/**
 * 阻塞队列
 */
public interface Queue {

    // 当队列满时，需要等待
    boolean offer(Object obj) throws InterruptedException;

    // 当队列空时，需要等待
    Object poll() throws InterruptedException;

    List toList();
}
