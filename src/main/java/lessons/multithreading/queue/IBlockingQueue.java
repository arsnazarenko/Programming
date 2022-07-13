package lessons.multithreading.queue;

public interface IBlockingQueue<T> {
    T pop() throws InterruptedException;
    void push(T v) throws InterruptedException;
}
