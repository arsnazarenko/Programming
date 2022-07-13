package lessons.multithreading;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class PoolMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors available: " + cores);
        //Мы указали пулу, что он одновременно может выполнять 2 задачи
        ExecutorService service = Executors.newFixedThreadPool(2);

        List<Future> futures = new ArrayList<>();

        /*
         Здесь мы говорим что хотим 4 раза параллельно запустить наш потоки, котрые считают от 0 до 4
         Наш пул запустит выполнение первыъ двух задач с помощью двух потоков,
         затем задачи выполнятся, наши потоки обратно вернутся в пул, и оставшиеся две задачи
         вновь обратятся к пулу потоков, в котором содержится два свободных потока, и выполнятся
         Пулл позволяет переиспользовать потоки, и регулирует количество одновремнно работающих потоков
         Рекомендуется одновременно использовать кол-во потоков, не большее чем количество логических
         ядер на процессорах, тогда задачи будут испольняться параллельно
         Получить это количество можно так: Runtime.getRuntime().availableProcessors();
         */
        for (int i = 0; i < 4; i++) {
            //Future future = service.submit(new MyThread("t#" + i));
            Future future = service.submit(new Task(i * 2));
            futures.add(future);
        }
        System.out.println("1)============================");
        //Метод get() - блокирующий, он блокирует наш метод до тех пор, пока тред не закончит работу и вернет future
        for (Future f: futures) {
            System.out.println(f.toString() + " result: " + f.get()); // можно запускать и с Runnable, результат всегда будет null
        }
        System.out.println("2)=============================");
        //метод invokeAll блокируется до тез пор, пока не закончат работу все задачи, и возвращает лист future
        List<Future<Integer>> resultFutures = service.invokeAll(Arrays.asList(new Task(4), new Task(7), new Task(9)));
        for (Future<Integer> f : resultFutures) {
            System.out.println(f.toString() + ": " + f.get());
        }
        System.out.println("____________________________________");
        service.shutdown();
    }
    static class Task implements Callable<Integer> {
        private int num;

        public Task(int num) {
            this.num = num;
        }

        @Override
        public Integer call() throws Exception {
            int acc = 0;
            try{
                for (int i=0; i < num; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    acc += i;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return acc;
        }
    }
}
