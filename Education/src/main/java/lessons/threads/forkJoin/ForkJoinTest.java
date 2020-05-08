package lessons.threads.forkJoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    public static void main(String[] args) {
        int[] array = Commons.prepareArray();

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();

        double sum = pool.invoke(new RecursiveCalc(array, 0, array.length));

        long endTime = System.currentTimeMillis();

        System.out.println("sum " + sum);
        System.out.println("time " + (endTime - startTime) + "ms");

        pool.shutdown();


    }


    private static class RecursiveCalc extends RecursiveTask<Double> {

        private static final int SEQUENTIAL_THRESHOLD = 50000;

        private final int[] array;
        private final int start;
        private final int end;


        public RecursiveCalc(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }
        @Override
        protected Double compute() {
            if(end - start <= SEQUENTIAL_THRESHOLD) {
                return Commons.calculate(array, start, end);
            }
            else {
                int mid = start + (end - start) / 2;
                RecursiveCalc left = new RecursiveCalc(array, start, mid);
                RecursiveCalc right = new RecursiveCalc(array, mid, end);
                invokeAll(left, right);
                return left.join() + right.join();

            }
        }

    }
}
