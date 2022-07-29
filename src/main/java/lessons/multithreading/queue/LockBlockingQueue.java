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
                notEmpty.await();
            }
            T value = queue.poll();
            size--;
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void push(T v) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size == capacity) {
                notFull.await();
            }
            queue.add(v);
            size++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}


