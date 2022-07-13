package lessons.multithreading;

import lessons.multithreading.queues.LockBlockingQueue;
import lessons.multithreading.queues.WaitBlockingQueue;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        /**
         * 3 Способа создания потока:
         * @see MyThread - 1 способ Создаем класс наследника
         * @see Runnable - 2 способ, Реализуем интерфес
         * @see java.util.Timer - 3 способ, вызова метода schedule() у Timer
         *
         * THREADPOOL
         * @see PoolMain
         *
         * VOLATILE
         * Модификатор volatile гарантирует выполнение отношения happens-before, что ведет к тому,
         * что измененное значение этой переменной увидят все потоки.
         * Чтение и запись volatile поля гарантирует, что для двух потоков данные будут читаться верно!
         * Хорошо это или плохо, JRE неявно обеспечивает синхронизацию при доступе к volatile переменным,
         * но с одной очень большой оговоркой: чтение volatile переменных синхронизировано и запись в volatile переменные
         * синхронизирована, а неатомарные операции – нет.
         *
         * Если происходит только присвоение нового значение переменнной в одном потоке и ее чтение в другом, то volatile
         * обеспечит потокобезопасность
         * Если присвоенное значение volatile переменной зависит от её текущего значения (например, во время операции инкремента),
         * то нужно использовать синхронизацию, если вы хотите, чтобы операция была потокобезопасной.
         *
         *
         *
         * DEADLOCK
         * @see DeadLock
         *
         * ПРОЦЕССЫ
         * Отличие потоков от процессов:
         * - потоки намного легче процессов поскольку требуют меньше времени и ресурсов;
         * - переключение контекста между потоками намного быстрее, чем между процессами, т. к. для каждого процесса
         * выделяется отдельная виртуальная память, в то время как поток имеет только лишь отдельный стэк;
         * - намного проще добиться взаимодействия между потоками, чем между процессами.
         *
         * WAIT, NOTIFY, LOCKS
         * @see lessons.multithreading.waitNotify.Account
         *
         * @see WaitBlockingQueue - простая реализация блок. очереди через wait() notifyAll()
         * @see LockBlockingQueue
         * - Улучшенная версия Queue с использованием Интерфейсов:
         * {@link java.util.concurrent.locks.Lock} and {@link java.util.concurrent.locks.Condition},
         * чтобы улучшить производительность многопоточности.
         *
         *
         * SYNCHRONIZED
         * @see lessons.multithreading.counter.CounterTest
         *
         * INTERRUPT
         * @see MainStopThread
         *
         * SEMAPHORE
         * @see lessons.multithreading.semaphore.SemaphoreTest
         *
         */

        //Взаимодействие двух потоков
        Thread t1 = new MyThread("isParallel1");
        Thread t2 = new MyThread("isParallel2");
        Thread t3 = new MyThread("isParallel3");
        //Метод старт нельзя запустить более одного раза!!!!!!!!!!
        Thread[] ts = new Thread[3];
        ts[0] = t1;
        ts[1] = t2;
        ts[2] = t3;

        for (Thread t : ts) {
            t.start();
        }


        /*
        Метод join() блокирует внешние потоки до завершения работы потока, у которого вызван этот метод
        Удобно, когда один поток создает данные, с которыми будет работать другой поток
         */
//        try {
//            for (Thread t : ts) {
//                t.join();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //параллелно с нашим потоком в потоке Main тоже будем ситтать
        System.out.println("Thread Main: started");
        try{
            for (int i = 0; i < 6; i++) {
                System.out.println("Thread Main: " + i );
                TimeUnit.SECONDS.sleep(2);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread main: finished");

        /*
        Метод run() - обычный метод, выполняющий код внутри него
        метод start() - нативный метод, который создает отдельный поток для выполнения run()
        Соответственно, при обычном вызове метода run() мы получим поледовательное выполнение кода в потоке main
         */

        /*
        метод setDaemon() - сделает поток - демоном. Такой поток будет завершаться тогда, не останется ни одного потока не демона
        Этот же поток не учитывается. Стандартно программа живет до момента, пока существует хотя бы олин поток
         */


        //Методы для прерывания потока:

    }

}
