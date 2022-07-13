package lessons.multithreading.completableFuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;




public class OtherExamples {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> values = Arrays.asList(1, -2, 3, 4, -23, 12, 3, 43, 4, -123, 21, 22, -87, 90, 54);

        List<CompletableFuture<List<Integer>>> futuresList = values.stream().
                map(OtherExamples::someComputation).collect(Collectors.toList());

        // toArray Требует в аргументе массив, чтобы вычислить тип массива у вовзр. значения, там просто вызывается .getClass()
        // allOf(...) создает CompletableFuture<Void>, то есть результат не переходит к нему
        // Нужен только для того, начать выполнение до момента, когда выполнятся все переданные в него CompletableFuture
        // Поэтому thenApply начнет выполнение тольког, когда завершаться все паралллельные задачи из списка futuresList
        CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0])).thenApply((Void v) -> {
            return futuresList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        }).get().stream().filter(Objects::nonNull).flatMap(Collection::stream).forEach(v -> System.out.print(v + " "));
    }

    static CompletableFuture<List<Integer>> someComputation(int value) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);

            } catch (InterruptedException e) {
                // some exception
                throw new IllegalStateException();
            }
            if (value < 0) {
                // тут можно обойтис обвчным if, но делаем так для демонстрации
                // обработки ошибок
                throw new IllegalArgumentException();
            }
            List<Integer> divisors = new ArrayList<>();
            int val = value;
            int divisor = 2;
            while (val > 1) {
                while (val % divisor == 0) {
                    val = val / divisor;
                    divisors.add(divisor);
                }
                divisor++;
            }
            return divisors;
        }).exceptionally(err -> null);
    }
}