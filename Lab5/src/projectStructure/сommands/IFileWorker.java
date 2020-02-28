package projectStructure.сommands;

import projectStructure.collectionDB.CollectionManager;

/**
 * Интерфейс, содердащий два метода для сериализации объектов и наоборот
 */
public interface IFileWorker {
    void convertObjectToXml(CollectionManager organizationCollection, String filePath);
    CollectionManager fromXmlToObject(String filePath);
}
