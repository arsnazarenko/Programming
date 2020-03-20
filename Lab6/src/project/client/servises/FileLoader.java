package project.client.servises;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public String fileData(File file) {
        String result = "";
        try(FileReader in = new FileReader(file)) {
            char[] arr = new char[(int) file.length()];
            int read = in.read(arr);
            result = new String(arr);
        } catch (FileNotFoundException e) {
            System.out.println("ОШИБКА ФАЙЛА");
        } catch (IOException e) {
            System.out.println("ОШИБКА ВВОДА");
        }
        return result;
    }

}
