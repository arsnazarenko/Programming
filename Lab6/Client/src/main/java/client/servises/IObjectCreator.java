package client.servises;



import library.сlassModel.Organization;

import java.util.Scanner;

/**
 * Интерфейс, содержащий перегруженный метод для создания объекта коллекции.
 */

public interface IObjectCreator {
    Organization create(Scanner reader);
}
