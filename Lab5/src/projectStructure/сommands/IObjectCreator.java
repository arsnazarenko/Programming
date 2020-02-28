package projectStructure.сommands;

import projectStructure.сlassModel.Organization;

import java.util.Scanner;
/**
 * Интерфейс, содержащий два перегруженных метода.
 */

public interface IObjectCreator {
    Organization create(Scanner reader);
    Organization create(Scanner reader, Long strId);
}
