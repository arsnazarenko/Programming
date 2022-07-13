package lessons.multithreading.completableFuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Example {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        testRunAsync();
//        Integer cfResult = testSupplyAsync().get();
        testAsyncPipeline().get();


        /**
         * Задача выполняет в другом потоке, а поток этот продоставляется глобальным ForkJoinPool.commonPool()
         * НО можно создать собственный пул потоков, и передать это параметром в метод, тогда будет использован
         * поток из этого пула
         *
         * Main поток не блокируется, но можно явно вызвать .get(), и тогда
         * по аналогии с Future мы заблокируеся до получения результата
         * для CompletableFuture<Void> - get() дает всегда null
         */


    }

    private static CompletableFuture<Void> testAsyncPipeline() {
        /**
         * Вы можете использовать метод thenApply() для обработки и преобразования результата CompletableFuture при его поступлении.
         * В качестве аргумента он принимает Function<T, R>.
         * Принимает значение предыдущего CompletableFuture и возвращает преобразованное значение следующему
         * Когда у метода нет окончания async, знвчит он использует поток, в котором выполнялось предыдущее дейтвие
         */

        /**
         * CompletableFuture.thenAccept() принимает Consumer<T> и возвращает CompletableFuture<Void>.
         * Он имеет доступ к результату CompletableFuture, к которому он прикреплён.
         */

        return CompletableFuture.supplyAsync(() -> {
            //продолжительная операция
            try {
                System.out.println("First async operation in thread: " + Thread.currentThread().getName() + "\n");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            int[] arr = new int[]{166, 121, 113, 124, 175, 167, 117, 118, 90, 11, 222};

            return arr;
        }).thenApplyAsync((arr) -> { //коллбэк функция, преобразующая значение
            System.out.println("Second async operation in thread: " + Thread.currentThread().getName() + "\n");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Arrays.sort(arr);
            return arr;
        }).thenApplyAsync((arr) -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Third async operation in thread: " + Thread.currentThread().getName() + "\n");
                    return
                            Arrays.stream(arr). //коллбэк функция, преобразующая значение
                                    mapToObj(i -> String.valueOf((char) i)).
                                    reduce(String::concat);
                }
        ).thenAcceptAsync(s -> System.out.println(s.get().toUpperCase())); //колбэк функция принимающая значение прошлого CompletableFuture, но ничего не возращает
    }

    private static CompletableFuture<Integer> testSupplyAsync() {
        /**
         * CompletableFuture.supplyAsync().
         * Принимает Supplier<T> и возвращает CompletableFuture<T>,
         * где T это тип возвращаемого функцией-поставщиком значения:
         */

        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            for (int i = 0; i < 10; i++) {
                result += i;
                System.out.println(i + " итерация. Сумма: " + result);


                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        });
    }

    private static CompletableFuture<Void> testRunAsync() throws InterruptedException, ExecutionException {
        /**
         * Асинхронное выполнение задачи с CompletableFuture.runAsync()
         * возвращает CompletableFuture<Void>
         */
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Work in other thread: " + Thread.currentThread().getName() + " , not in main-thread");

        });


    }






}
