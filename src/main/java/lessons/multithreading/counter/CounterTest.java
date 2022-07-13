package lessons.multithreading.counter;


public class CounterTest {
    static class Sequencer extends Thread {
        private Counter counter;
        public Sequencer(Counter counter) {
            this.counter = counter;

        }

        @Override
        public void run() {
            for (int i = 0; i < 100_000; i++) {
                counter.inc();
            }

        }
    }
    /*
    Двум потокам передается ссылка на один объект, с этих потоках по этой ссылке
    изменяют числовое поле этого объекта двумя потками одновременно
     */

    public static void testCounter() throws InterruptedException {
        final int threadNum = 2;
        //Counter counter = new SimpleCounter(); //Плохое решение
        //AtomicCounter counter = new AtomicCounter(); //решение через Atomic
        LockCounter counter = new LockCounter(); //решение через synchronized
        Thread[] threads = new Thread[threadNum];
        for (int i =0; i < threadNum; i++) {
            Thread thread = new Sequencer(counter);
            threads[i] = thread;
            thread.start();
        }

        for (Thread t: threads) {   //дожидаемся исполнения обоих потоков
            t.join();
        }
        System.out.printf("Threads : %d\nCounter: %d", threadNum, counter.inc());
    }

    public static void main(String[] args) throws InterruptedException {
        testCounter();

    }
}
