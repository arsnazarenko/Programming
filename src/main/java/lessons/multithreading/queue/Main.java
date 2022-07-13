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
        for (long i = 0; i < 50; ++i) {
            producers.add(() -> {
                Stream.iterate(0, s -> ++s).limit(200).forEach(v -> {
                    try {
                        bq.push(v);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        }


        List<Runnable> consumers = new ArrayList<>();
        for (long i = 0; i < 200; ++i) {
            consumers.add(() -> {
                List<Integer> localData = new ArrayList<>();
                for (int j = 0; j < 50; ++j) {
                    try {
                        localData.add(bq.pop());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
//                System.out.println(Thread.currentThread().getName() + ": " + localData);
            });
        }

        List<Thread> prodThreads = producers.stream().map(Thread::new).collect(Collectors.toList());
        List<Thread> consThreads = consumers.stream().map(Thread::new).collect(Collectors.toList());
        long start = new Date().getTime();
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
        System.out.println("Time: " + (new Date().getTime() - start));
    }
}