package lessons.multithreading.queue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Это плохой пример реализации блокирующей очереди. Здесь используются базовые инструменты Java:
 * synchronized, wait, notify.
 * Наша задача предусматривает усыпление потоков до наступления каких то событий. Для потребителей событие - наличие элементов в очереди,
 * а для производителей - наличие свободного места в очереди.
 * Проблема инструментов выше в том, что при вызове Wait мы не можем разделить потоки на группы по событиям, которыз они дожидаются.
 * В итоге, вызов notify пробуждает потоки независимо от того, какое событие наступило. К примеру, когда очередь заполнилась, логично пробудить thread-потребитель
 * Из-за этого может наступить ситуация, и потребители и производители уснут в ожидании пробуждения, но никто их не пробудит, что по сути своей является deadlock'ом
 *
  */

public class BlockingQueue<T> implements IBlockingQueue<T> {

    private final Queue<T> queue;
    private final int capacity;
    private int size;

    int prodWaitCounter = 0;
    int consWaitCounter = 0;

    int produced = 0;
    int consumed = 0;

    public BlockingQueue(final int capacity) {
        this.queue = new ArrayDeque<>();
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public synchronized T pop() throws InterruptedException {
        System.out.println("CONSUMER " + Thread.currentThread().getId() + " lock");
        while (size == 0) {
            ++consWaitCounter;
            System.out.println("CONSUMER " + Thread.currentThread().getId() + " wait, cons-waiters: " + consWaitCounter);
            wait();
            --consWaitCounter;
            System.out.println("CONSUMER " + Thread.currentThread().getId() + " wake up, cons-waiters: " + consWaitCounter);
        }
        T value = queue.poll();
        size--;
        ++consumed;
        System.out.println("CONSUMER " + Thread.currentThread().getId() + " signal, cons: " + consumed);
        notify();
        System.out.println("CONSUMER " + Thread.currentThread().getId() + " unlock");
        return value;
    }

    @Override
    public synchronized void push(T v) throws InterruptedException {
        System.out.println("PRODUCER " + Thread.currentThread().getId() + " lock");
        while (size == capacity) {
            ++prodWaitCounter;
            System.out.println("PRODUCER " + Thread.currentThread().getId() + " wait, prod-waiters: " + prodWaitCounter);
            wait();
            --prodWaitCounter;
            System.out.println("PRODUCER " + Thread.currentThread().getId() + " wake up, prod-waiters: " + prodWaitCounter);
        }
        queue.add(v);
        size++;
        ++produced;
        System.out.println("PRODUCER " + Thread.currentThread().getId() + " signal, prod: " + produced);
        notify();
        System.out.println("PRODUCER " + Thread.currentThread().getId() + " unlock");
    }
}


