package concurrency.blockingqueue;

import java.util.List;

/**
 * 公平阻塞队列。公平指的是在该队列上等待的线程，按照FIFO顺序排队，先等待的线程，优先被唤醒。
 */
public interface FairBlockingQueue {

    // 当队列满时，需要等待
    boolean offer(Object obj) throws InterruptedException;

    // 当队列空时，需要等待
    Object poll() throws InterruptedException;

    List toList();
}
