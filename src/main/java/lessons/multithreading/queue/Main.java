package lessons.multithreading.queue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        IBlockingQueue<Integer> bq = new BlockingQueue<>(20);
        List<Runnable> producers = new ArrayList<>();
        for (long i = 1; i <= 100; ++i) {
            final long thread_id = i;
            producers.add(() -> {
                Thread.currentThread().setName("Producer thread №" + thread_id);
                Stream.iterate(1, s -> ++s).limit(100).forEach(v -> {
                    try {
                        bq.push(v);
                        System.out.println(Thread.currentThread().getName() + " push " + v);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        }


        List<Runnable> consumers = new ArrayList<>();
        for (long i = 1; i <= 500; ++i) {
            final long thread_id = i;
            consumers.add(() -> {
                Thread.currentThread().setName("Consumer thread №" + thread_id);
                for (int j = 1; j <= 20; ++j) {
                    try {
                        Integer v = bq.pop();
                        System.out.println(Thread.currentThread().getName() + " pop " + v);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        List<Thread> prodThreads = producers.stream().map(Thread::new).collect(Collectors.toList());
        List<Thread> consThreads = consumers.stream().map(Thread::new).collect(Collectors.toList());
        prodThreads.forEach(Thread::start);
        consThreads.forEach(Thread::start);
        try {
            for (Thread prodThread : prodThreads) {
                prodThread.join();
            }
            for (Thread consThread : consThreads) {
                consThread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}