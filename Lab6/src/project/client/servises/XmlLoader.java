package project.client.servises;

import project.client.сlassModel.Organization;
import project.server.CollectionManager;
import project.server.services.JaxbWorker;

import java.io.File;
import java.util.Comparator;
import java.util.Deque;

public class XmlLoader {

    public static CollectionManager fromXmlToCollection(File file) {
        CollectionManager collectionManager = new CollectionManager();
        Deque<Organization> organizations =JaxbWorker.fromXmlToObject(file).getOrgCollection();
        CollectionManager.OBJECT_ID_COUNTER = organizations.stream()
                .max(Comparator.comparing(Organization::getId)).
                        get().
                        getId();
        collectionManager.setOrgCollection(organizations);
        return collectionManager;

    }
}
