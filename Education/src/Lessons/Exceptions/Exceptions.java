package Lessons.Exceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Exceptions {
    public static void main(String[] args) {
        String a = "hello";//проверка области видимости блоков try, catch, finally, вне блоков не видно объектов, которые были созданы в них
        //данный пример может показать, как можно упустить исключение


        try {
            try {
                //String b = "hello it try";
                throw new MyNewException("MNE");


            } finally {

                System.out.println(a);

                if (true) {
                    throw new IOException("b");

                }
                System.err.println("c");
            }
        } catch (IOException | MyNewException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println("d");
            System.err.println(ex.getMessage());
        }

    }
    public static class Reader{

        public String read1(String path) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(path));
            try {
                return br.readLine();
            } finally {
                if (br != null) br.close();
            }
        }

        //применение try-with-resources
        public String read0(String path) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                return br.readLine();
            }
        }
    }
}
