package lessons.multithreading.counter;

public class SimpleCounter implements Counter{
    private long val;

    public long inc() { return val++; }
}
