package lessons.multithreading.queue;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Реализация блокирующей очереди с помощью таких инструментов стандартной бибилиотеки, как:
 * @see java.util.concurrent.locks.ReentrantLock (рекурсивный мьютекс)
 * @see java.util.concurrent.locks.Condition (условная переменная)
 * За счет condition'ов мы можем разделить наши потоки на две группы, ожидающие двух разных событий.
 * Первые - наличия места в очереди, вторые - наличия элементов в очереди
 * Так, вызов considion.signal пробуждает только те потоки, которые ждали определенного события.
 * Эта реализаця является корректной, deadlock не возникает
 */
public class LockBlockingQueue<T> implements IBlockingQueue<T> {


    private final Queue<T> queue;
    private final int capacity;
    private int size;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    int prodWaitCounter = 0;
    int consWaitCounter = 0;

    int produced = 0;
    int consumed = 0;

    public LockBlockingQueue(final int capacity) {
        this.queue = new ArrayDeque<>();
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public T pop() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size == 0) {
                ++consWaitCounter;
                System.out.println("CONSUMER " + Thread.currentThread().getId() + " wait, cons-waiters: " + consWaitCounter);
                notEmpty.await();
                --consWaitCounter;
                System.out.println("CONSUMER " + Thread.currentThread().getId() + " wake up, cons-waiters: " + consWaitCounter);
            }
            T value = queue.poll();
            size--;
            ++consumed;
            System.out.println("CONSUMER " + Thread.currentThread().getId() + " signal, cons: " + consumed);
            notFull.signal();
            System.out.println("CONSUMER " + Thread.currentThread().getId() + " unlock");
            return value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void push(T v) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            System.out.println("PRODUCER " + Thread.currentThread().getId() + " lock");
            while (size == capacity) {
                ++prodWaitCounter;
                System.out.println("PRODUCER " + Thread.currentThread().getId() + " wait, prod-waiters: " + prodWaitCounter);
                notFull.await();
                --prodWaitCounter;
                System.out.println("PRODUCER " + Thread.currentThread().getId() + " wake up, prod-waiters: " + prodWaitCounter);
            }
            queue.add(v);
            size++;
            ++produced;
            System.out.println("PRODUCER " + Thread.currentThread().getId() + " signal, prod: " + produced);
            notEmpty.signal();
            System.out.println("PRODUCER " + Thread.currentThread().getId() + " unlock");
        } finally {
            lock.unlock();
        }
    }
}


