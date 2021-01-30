package lessons.threads.counter;

public class LockCounter implements Counter {

    /**
     * Монитор есть и у класса и у объекта
     *
     * synchronized нестатический метод блокирует монитор объекта, любой другой поток не смодет параллеьльно взаимоействовать с этим объектом
     *
     * synchronized статический метод блокирует весь класс, другой поток не сможет параллельно взаимойдествовать с этим КЛАССОМ
     * (через другой синхронизированный статический методм или этот же),при этом если другой поток взаиможействует чепез нестатический метод,
     * то параллельное взаимодействие осуществить он сможет, так как работает с ОБЪЕКТОМ
     *
     * synchronized блок блокирует только тот объект, который мы указали в скобках
     *
     * Если монитор объекта уже захвачен через конструкцию synchronized каким то потоком,
     * то это не мешает внутри этой конструкции вновь использовать synchronized конструкцию
     * в этом же потоке
     */

    private Object objectLock = new Object();
    static long staticCounter;
    private long counter;

    /*
    Ключевое слово synchronized говорит о том, что
    когда этот метод будет выполняться, он заблокируется
    на мониторе текущего объекта (this)
     */
    @Override
    public synchronized long inc() {
        return counter++;
    }
    /*
    synchronized (obj) - блок, в который заключен код, при исполнении
    которого монитор объекта obj заблокируется
    Преимущество такого блока в том, что можно блокировать монитор только
     на нужных оперциях
    Важное отличие в том, что блокироваться монитор можно совершенно другого
     объекта, иногда это бывает полезным, но редко
     */
    public long inc_() {
        synchronized (objectLock) {
            return counter++;
        }
    }
    /*
    WARNING!!!
    здесь никакой блокировки нет, небезопасно изменять объект
    таким методом из нескольких потоков
     */
    public long unsafeInc() {
        return counter++;
    }

    //статический метод можно синхронизировать
    static synchronized long staticInc() {
        return staticCounter++;
    }
    //метод эквивалентный методы выше через синхр. блок
    static long staticInc_() {
        synchronized (LockCounter.class) {
            return staticCounter++;
        }
    }
}
