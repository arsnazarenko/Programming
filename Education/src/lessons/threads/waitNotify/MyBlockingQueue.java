package lessons.threads.waitNotify;

public interface MyBlockingQueue<E> {
    void put(E e) throws InterruptedException;
    E take() throws InterruptedException;
}
