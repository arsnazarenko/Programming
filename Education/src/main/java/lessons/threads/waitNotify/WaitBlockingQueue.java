package lessons.threads.waitNotify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WaitBlockingQueue<E> implements MyBlockingQueue<E>{
    /**
     * Собственная имплементация BlockingQueue, через wait, notifyAll
     * Принцип работы:
     * Мы сихронизируемся на мониторе объекта MyBlockingQueue<E> при вызове методов put , take.
     * Если вдруг в очереди нет элементов, поток, выполняющий метод take отправляется в очередь ожидания данного монитора, освобождая монитор
     * Поэтому к нему обращается поток, который выполняет метод put, и при удачном выполнении вызывает метод notifyAll, которой пробуждает
     * все потоки, находящийся в ожидающей очереди на этом мониторе. Аналогично происходит ситуация, когда очередь запонена полностью,
     * теперь producer засыпает, а поток - consumer чистит коллекцию и будит producer'a;
     *
     */

    private Queue<E> queue;
    private final int max;



    public WaitBlockingQueue(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
    }

    public void put(E e) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == max) {
                wait();
            }
            queue.add(e);
            notifyAll();
        }

    }

    public E take() throws InterruptedException {
        synchronized (this) {
            while (queue.size() == 0) {
                wait();
            }
            E item = queue.remove();
            notifyAll();
            return item;
        }

    }

    @Override
    public String toString() {
        return "MyBlockingQueue{" +
                "queue=" + queue +
                '}';
    }
}
