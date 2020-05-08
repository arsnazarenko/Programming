package lessons.threads.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SemaphoreTest {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /**
         * Одновременно входить в некий блок кода, взаиможействуя с каким то ресурсом,
         * смогут только два трэда, остальные будут ждать
         *
         * Параметр permits указывает на количество допустимых разрешений для доступа к ресурсу.
         * Параметр fair во втором конструкторе позволяет установить очередность получения доступа.
         * Если он равен true, то разрешения будут предоставляться ожидающим потокам в том порядке,
         * в каком они запрашивали доступ. Если же он равен false, то разрешения будут предоставляться
         * в неопределенном порядке.
         */

        /*
        Указываем, что одновременнно могут работать 8 потоков, причем если таксков больше, то
        после того как один поток выполнит такс, он тут же станет выполнять другой
        */
        Semaphore semaphore = new Semaphore(2, true);
        Service serviceForWork = new Service();

        ExecutorService service = Executors.newFixedThreadPool(6);

        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            futures.add(service.submit(new Task(semaphore, serviceForWork)));
        }


        /*
        Упорядоченное завершение работы, при котором ранее отправленные задачи выполняются, а новые задачи не принимаются
        Ждет завершение всех задач
        Все которые были засабмитчены, будут выполнены, а нвые приниматься не будут
         */
        service.shutdown();


        /*
        awaitTermination() - Блокировка до тех пор, пока все задачи не завершат выполнение
         после запроса на завершение работы или пока не наступит тайм-аут или
          не будет прерван текущий поток, в зависимости от того, что произойдет раньше
         */
        service.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println(" RESULT\n________________________________");

        for (Future<String> f : futures) {
            System.out.println(f.get());
        }


    }


    //Сервис, одновременнно с которым может взаимодействовать только два потоко
    private static class Service {
        private String name = "service";
        private List<String> visitors;

        public Service() {
            this.visitors = new ArrayList<>();
        }

        public void sayHello(String name) {
            visitors.add(name);
        }

        @Override
        public String toString() {
            return "Service{" +
                    "visitors=" + visitors +
                    '}';
        }
    }

    private static class Task implements Callable<String> {
        private Semaphore semaphore;
        private Service service;

        public Task(Semaphore semaphore, Service service) {
            this.semaphore = semaphore;
            this.service = service;
        }

        @Override
        public String call() {
            try {
               return runUnsafe();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName());
                return e.getClass().getName();
            }
        }

        private String runUnsafe() throws InterruptedException {
            semaphore.acquire();
            try {
                System.out.println(Thread.currentThread().getName() + " acquired semaphore");
                service.sayHello(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } finally {
                System.out.println(Thread.currentThread().getName() + " released semaphore");
                semaphore.release();
                return Thread.currentThread().getName() + " finished: " + service.name;
            }
        }
    }
}
