package lessons;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class JavaIO {

    public static void main(String[] args) throws IOException {

        //demo();
        transferDemo();
    }
    public static void demo() throws IOException {
        //Java IO
        java.io.File java = new java.io.File("D:\\Files\\Documents\\Java\\Work\\numbers.txt");
        System.out.println(java.isAbsolute() + " namePath: " + java.getPath() + " nameParent: " + java.getParent() + " name: " + java.getName());
        System.out.println(java.canRead());
        System.out.println(java.
                length());


        //Java NIO
        Path dirFile = Paths.get("D:\\Files\\Documents\\Java\\GitHub\\Education\\in.txt");
        System.out.println(Files.size(dirFile));



        Scanner sc = new Scanner(System.in);
        Long res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("Введите число: ");
            try{line = sc.nextLine();
                res = Long.parseLong(line);
                }

            catch (NumberFormatException e) {
                flag = false;
            }
            if (flag == true) {
                break;
            }
        }


        //java nio
        FileInputStream fis = new FileInputStream("D:\\Files\\Documents\\Java\\GitHub\\Education\\in.txt"); //открыли поток для чтения из файла
        FileChannel inChannel = fis.getChannel();//создали канал для чтения из файла
        FileOutputStream fos = new FileOutputStream("D:\\Files\\Documents\\Java\\GitHub\\Education\\out.txt");//аналогично для записи из буфера в файл
        FileChannel outChannel = fos.getChannel();
        ByteBuffer inBb = ByteBuffer.allocate(4096);//создали буфер
        ByteBuffer outBb = ByteBuffer.allocate(4096);
        int r  = inChannel.read(inBb);//прочитали из канала в буфер
        while (r != 1) {
            inBb.flip(); //отправили указатель в начало, чтобы читать из буфера
            while (inBb.hasRemaining()) { //пока что то есть, читать
                byte get = inBb.get();
                outBb.put(get);//доюавляли в буфер другой
            }
            outBb.flip(); //отправили указатель второго буфера в начало для чтения
            outChannel.write(outBb); // записаи из буфера в файл
            inBb.clear(); //отчистили буфер
            outBb.clear();
            r = inChannel.read(inBb); //заново начинаем чтение из канала в входной буфер
        }
        fis.close();
        fos.close();

    }



    public static void transferDemo() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\Files\\Documents\\Java\\GitHub\\Education\\in.txt");
        FileOutputStream fos = new FileOutputStream("D:\\Files\\Documents\\Java\\GitHub\\Education\\out.txt");


        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        inChannel.transferTo(0, inChannel.size(), outChannel);
    }
}
