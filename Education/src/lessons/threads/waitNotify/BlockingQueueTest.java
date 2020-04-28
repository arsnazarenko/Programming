package lessons.threads.waitNotify;

import lessons.threads.LockCondition.LockBlockingQueue;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BlockingQueueTest {

    private static class ProducerThread extends Thread {
        private MyBlockingQueue<Integer> waitBlockingQueue;
        private int start;
        private int end;


        public ProducerThread(MyBlockingQueue<Integer> blockingQueue, int start, int end) {
            this.waitBlockingQueue = blockingQueue;
            this.start = start;
            this.end = end;


        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                try {
                    waitBlockingQueue.put(i);
                    System.out.println(Thread.currentThread().getName() + " added: " + i);
                    TimeUnit.MILLISECONDS.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static class ConsumerThread extends Thread {
        private MyBlockingQueue<Integer> waitBlockingQueue;

        public ConsumerThread(MyBlockingQueue<Integer> blockingQueue) {

            this.waitBlockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " removed: " + waitBlockingQueue.take());
                    TimeUnit.MILLISECONDS.sleep(700);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        //MyBlockingQueue<Integer> blockingQueue = new WaitBlockingQueue<>(10);
        MyBlockingQueue<Integer> blockingQueue = new LockBlockingQueue<>(10);
        Thread[] cThreads = new Thread[2];

        ProducerThread pt_1 = new ProducerThread(blockingQueue, 0, 35);
        ProducerThread pt_2 = new ProducerThread(blockingQueue, 100, 135);
        ProducerThread pt_3 = new ProducerThread(blockingQueue, 200, 235);

        ConsumerThread ct_1 = new ConsumerThread(blockingQueue);
        ConsumerThread ct_2 = new ConsumerThread(blockingQueue);

        cThreads[0] = ct_1;
        cThreads[1] = ct_2;


        pt_1.start();
        pt_2.start();
        pt_3.start();
        for(Thread ct: cThreads) {
            ct.start();
        }
        TimeUnit.SECONDS.sleep(48);

        for(Thread ct: cThreads) {
            ct.interrupt();
        }
        System.out.println(blockingQueue);






    }
}
