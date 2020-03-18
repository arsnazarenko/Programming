import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import project.client.ui.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        try {
            String pathFile = args[0];
            File fileForSaveAndLoad = new File(pathFile);
            if (!fileForSaveAndLoad.exists()) {
                throw new FileNotFoundException();
            } else if (!fileForSaveAndLoad.canRead()) {
                throw new FileNotFoundException();
            } else if (!fileForSaveAndLoad.canWrite()) {
                throw new FileNotFoundException();
            } else {
                Reader reader = new Reader();
                ValidateManager validateManager= new ValidateManager();
                ObjectCreator objectCreator = new ObjectCreator();
                Validator validator = new Validator(objectCreator, validateManager);
                FileLoader fileLoader = new FileLoader();
                //UI ui = new UI(reader, validator, fileForSaveAndLoad, fileLoader);
                //ui.loadOnServer();
                //ui.execute(System.in);

            }
        } catch (FileNotFoundException e) {
            System.err.println("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ");

        } catch (IndexOutOfBoundsException e) {
            System.err.println("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ");
        } catch (
                NoSuchElementException e) {
            System.err.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        }
    }
}
