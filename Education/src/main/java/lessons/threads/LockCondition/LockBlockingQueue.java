package lessons.threads.LockCondition;
import lessons.threads.waitNotify.MyBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LockBlockingQueue<E> implements MyBlockingQueue<E> {
    private Queue<E> queue;
    private final int max;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();


    public LockBlockingQueue(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
    }

    public void put(E e) throws InterruptedException {

        /*
        Здесь важно знать механизм прерывания потока: после вызова метода interrupt он лишь выставляет некий флаг у потока,
         если этот флаг не проверить, что потока сам и не прервется. НО! если прерывание было вызвано в момент, когда поток
        был в некоторых состояниях: wait(), sleep(), или если вдруг был вызван метод lock(), но в этот момент поток был заблокирован,
        то бросается исключение
        Если поток стоит в ожидании на блокировку (методом lockInterruptibly()) или он уже захватил блокировку, и его прерывают,
         то выбрасывается исключение и обработав его, можно завершить поток
         с обычным lock такое не пройдет
        лучше использовать lock.lockInterruptibly() - он при прерывании потока кинет эксепшн и не
         станет захватывать блокировку, unlock делать не надо
         */
        lock.lockInterruptibly();

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
        lock.lockInterruptibly();
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
