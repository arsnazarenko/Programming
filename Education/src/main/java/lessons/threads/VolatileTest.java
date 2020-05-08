package lessons.threads;

public class VolatileTest {
    /**
     * volatile обеспечвает безопасные операции чтения и записи из нескольких потоков
     * НО!!!
     * при создании volatile массива, безопасными будут только операции чтения и записи массива целиком:
     *
     */

    public volatile int x = 2;
    public volatile int[] mass = new int[] {1, 2, 4, 5, 6, 7};



    public static void main(String[] args) {
        VolatileTest vlt = new VolatileTest();
        vlt.x = 6; //безопасно
        System.out.println(vlt.x);//безопасно

        vlt.mass[1] = 34;//небезопасно
        System.out.println(vlt.mass[1]);//,безопасно

        vlt.mass = new int[12];//безопасно
        System.out.println(vlt.mass);//безопасно
        System.out.println();
    }


}
