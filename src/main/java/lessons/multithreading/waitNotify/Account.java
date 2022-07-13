package lessons.multithreading.waitNotify;

import java.util.concurrent.TimeUnit;

public class Account {
    /**
     * Пример для методов wait(), notifyAll()
     *
     * Класс аккаунта, на который возможно первести или снять деньги, а также просто проверить счет
     *
     * getBalance(), deposit(long amount), withdraw(long amount), waitAndWithdraw(long amount) -
     * synchronized методы, которые блокруются на объекте Account, т к взаимодействуют с ресурсами этого объектами,
     * а взаимодействие может происходить одновременно из нескольких потоков, к примеру когда один счет отправляет нам деньги, а
     * на другой мы параллеьлно их переводим
     *
     * метод waitAndWithdraw(long amount) - метод, который совершает снятие средств с счета, при том, что средств на счету нет,
     * но их поступление ожидается
     *
     * wait() - меотд, который отправляет поток, заблокировавшийся на мониторе, в редим ожидания(блокировка с объекта снимается, теперь с ним могут взаимодействовать другие потоки),
     * Поток жидает команды notifyAll(), которая будит все потоки, который работали с монитором этого объекта, при этом выполнение кода начинается с команды, последущей за методом wait()
     * При этом процессорное время не тратится, мы блокируемсяя
     *
     * Рекомендации по использованию wait(), notify():
     * 1) wait и notify() неразрывно связаны с конструкцией synchronized, могут быть
     * прописаны только внутри такой секции, т к именно она диктует, в wait лист какого
     * объекта добавляются потоки, и потоки какого экрана проснутся при команла notifyAll()
     *
     * 2) wait и notify() должны вызываться только на мониторе объекта, который захвачен
     * Критической Секцией, а не на моинторе другого объекта
     *
     * 3) инвариант должен проверяться только в цикле while()!!!
     *
     * 4) не ждать на глобальных объектах, потому что если он глобальный, то много потоков
     * имеют доступ к нему, значит блокироваться на wait() будет очень много потоков, к примеру синхронизироваться
     * на объекте new String("") нельзя!!!!
     */

    private long balance;

    public Account() {
        this(0L);
    }


    public Account(long balance) {
        this.balance = balance;
    }


    public synchronized long getBalance() {
        return balance;
    }

    public void deposit(long amount) {
        checkAmountNonNegative(amount);
        synchronized (this) {
            balance += amount;
            notifyAll();
        }

    }

    public void withdraw(long amount) {
        checkAmountNonNegative(amount);
        synchronized (this) {
            if (balance < amount) {
                throw new IllegalArgumentException("not enough money");
            }
            balance -= amount;
        }
    }
    public void waitAndWithdraw(long amount) throws InterruptedException {
        checkAmountNonNegative(amount);
        synchronized (this) {
            while(balance < amount) {
                wait();
            }
            balance -= amount;
        }
    }

    public static void checkAmountNonNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(0);

        new DepositThread(account).start();

        System.out.println("Calling waitAndWithdraw()....");

        account.waitAndWithdraw(50_000_000);

        System.out.println("waitAndWithdraw() finished");
        System.out.println(account.getBalance());
    }

    static class DepositThread extends Thread {

        private Account account;

        public DepositThread(Account account) {
            this.account = account;
        }
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(4);
                account.deposit(190);
                TimeUnit.SECONDS.sleep(4);
                account.deposit(100_000_000);
                System.out.println(account.getBalance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
