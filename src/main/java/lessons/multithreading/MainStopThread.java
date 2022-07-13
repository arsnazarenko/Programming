package lessons.multithreading;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainStopThread {
    /**
     * {@link InterThread}
     *Преимущество обработки остновки через метод Thread.currentThread().isInterrupted()
     *в том, что если в какой то момент времени поток будет спать(находится в join, sleep(), wait()),
     *при вызове interrupt() поток просыпается, и выкидывает исключение, код, который шел далее выполняться не будет,
     *сразу начентся новая итерация и при новой проверке поток прервется
     *
     * {@link FlagThread}
     * C аналогичным подходом через boolean переменнную будет иначе: если вдруг поток спал, и мы изменили флаг, то поток проснется, выполнит
     * дальнейший код и только при новой итерации цикла while выполнится проверка и поток прервется
     */


    static class InterThread extends Thread {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {    //такая проверка непременно обязательна, т к без нее метод interrupt() бессмысленный
                try{
                    System.out.println("Thread::sleep()");
                    TimeUnit.SECONDS.sleep(6);
                    System.out.println("Test output: Thread id working");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); //вновь выствыляем флаг
                }
            }
        }
    }



    public static void interruptThread() {
        Thread thread = new InterThread();
        thread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        thread.interrupt(); // при вызове этого метода просто выставляется флаг у класса треда, но если он вдруг спал, выбросится исключение
    }

    static class FlagThread extends Thread {
        private volatile boolean pleaseStop;

        @Override
        public void run() {
            while(!pleaseStop) {
                try {
                    System.out.println("Thread::sleep()");
                    TimeUnit.SECONDS.sleep(6);
                    System.out.println("Test output: Thread id working");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

        }
        public void stopThread() {
            System.out.println("Stopping...");
            pleaseStop = true;
        }
    }
    public static void flagThread() {
        FlagThread flagThread = new FlagThread();
        flagThread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        flagThread.stopThread(); //просто выставляем флаг, если поток спит, то мы приостановимся только по окончании сна
    }

    public static void main(String[] args) {
        //Тред можно создать просто через лямбду Runnable и передать этот объект в любой констркутор Thread
        /*Runnable r = () -> {
            System.out.println(" I'm third Thread ");
        };
        new Thread(r).start();*/

        interruptThread();

        //flagThread();

    }

}
