package lessons.multithreading.counter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {
    /**
     * Операция называется атомарной, если несколько потоков могут безопасно
     * изменять общие данные с помощью такой операции, не прибегая к механизму синхронизации
     */
    private AtomicLong val = new AtomicLong(0);


    @Override
    public long inc() {
        return val.getAndIncrement(); //операция атомарна! читай про механизм CAS

    }
}
