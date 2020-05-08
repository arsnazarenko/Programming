package lessons.threads.forkJoin;

import java.util.concurrent.*;

public class ExecCalc {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] array = Commons.prepareArray();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        long startTime = System.currentTimeMillis();

        Future<Double> f1 = executor.submit(new PartialCalc(array, 0, array.length / 2));
        Future<Double> f2 = executor.submit(new PartialCalc(array, array.length / 2, array.length));

        double sum = f1.get() + f2.get();

        long endTime = System.currentTimeMillis();

        System.out.println("sum: " + sum);
        System.out.println("time: " + (endTime - startTime) + " ms");
        
        executor.shutdown();



    }
    static class PartialCalc implements Callable<Double> {
        private final int start;
        private final int end;
        private final int[] array;

        public PartialCalc(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public Double call() throws Exception {
            return Commons.calculate(array, start, end);
        }
    }
}
