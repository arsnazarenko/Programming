package lessons.threads.LockCondition;

import lessons.threads.waitNotify.MyBlockingQueue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LockBlockingQueue<E> implements MyBlockingQueue<E> {
    private Queue<E> queue;
    private final int max;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    new BlockingQueue


    public LockBlockingQueue(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
    }

    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == max) {
                notFull.await();
            }
            queue.add(e);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }


    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await();
            }
            E item = queue.remove();
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "MyBlockingQueue{" +
                "queue=" + queue +
                '}';
    }

}
