package project.client.servises;

import project.client.сlassModel.Organization;

import java.util.Scanner;

/**
 * Интерфейс, содержащий два перегруженных метода.
 */

public interface IObjectCreator {
    Organization create(Scanner reader);
    Organization create(Scanner reader, Long strId);//можно убрать
}
