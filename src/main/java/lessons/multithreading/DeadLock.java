package lessons.multithreading;

import java.util.logging.Logger;

public class DeadLock {
    /**
     * DeadLock - ситуация, когда два потока взаимодействуют следующим образом:
     * Поток 1 захватывает монитор объекта A, и внутри блокировки захватывает объект B
     * Поток 2 захватывает монитор объекта B, и внутри блоировки захватывает объект A
     * Таким образом, 1 поток захватил А, ожидает освобождение B, но ведь поток 2 уже захватил B,
     * и ожидает освобождения A
     * Таким образом оба потока вечно ожидают освобождения ресурса
     * Решение tryLock
     * или упорядочивание блокировок
     */
    static final Logger logger = Logger.getLogger(DeadLock.class.getName());


    public static void main(String[] args) {
        final Account a1 = new Account(1);
        a1.sum = 100;
        final Account a2 = new Account(2);
        a2.sum = 300;

        Thread t1 = new Thread(() -> {Account.transact(a1, a2, 10);},
                "<Transact a1 to a2>");
        Thread t2 = new Thread(() -> {Account.transact(a2, a1, 20);},
                "<Transact a2 to a1>");

        t1.start();
        t2.start();
    }

    static class Account {
        int sum;
        int id;

        public Account(int id) {
            this.id = id;
        }
        static void transact(final Account from, final Account to, int amount) {
            Account lock1;
            Account lock2;

//            lock1 = from;
//            lock2 = to;

            /*
            Упорядочиваем блокировку по id, теперь при первой транзакции блокируется аккаунт отправителя(1), а затем получателя(2)
            При второй тразакции мы блокируем получателя(1), а затем отправителя(2)
            При этом deadLock не происходит, вторая транзация обращается к объекту, и если вдруг он заблокирован другой транзакцией
            то он будет ожидать
             */
            if(from.id < to.id) {
                lock1 = from;
                lock2 = to;
            } else {
                lock1 = to;
                lock2 = from;
            }
            synchronized (lock1) {
                logger.info("Lock1(" + lock1.id + ") was acquired by thread " + Thread.currentThread().getName() + ". Waiting for lock2(" + lock2.id + ")");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    logger.info("Lock2(" + lock2.id + ") was acquired by thread " + Thread.currentThread().getName() + ". Waiting for lock1(" + lock1.id + ")");
                    from.sum -= amount;
                    to.sum =+  amount;
                }
            }




        }
    }
}
