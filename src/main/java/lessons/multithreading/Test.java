package lessons.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private static class MyResourse {

        private final Lock lock = new ReentrantLock();

        public void dodo(Thread other) {
            try {
                System.out.println(Thread.currentThread().getName() + " ожидает захвата");
                lock.lockInterruptibly();
                try {
                    System.out.println(Thread.currentThread().getName() + " захватил блокировку: " + ((ReentrantLock) lock).getHoldCount());
                    TimeUnit.SECONDS.sleep(5);
                    if (other != null) {
                        other.interrupt();
                    }
                } finally {
                    System.out.println(Thread.currentThread().getName() + " завершил работу");
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " выбросил исключение и прервался");
            }
        }
    }






    private static class TestT extends Thread {
        private MyResourse m;
        private Thread other;

        public TestT(MyResourse m, Thread other) {
            this.m = m;
            this.other = other;
        }

        @Override
        public void run() {
            m.dodo(other);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyResourse m = new MyResourse();
        TestT t1 = new TestT(m, null);
        TestT t2 = new TestT(m, t1);


        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t1.start();


    }

}
