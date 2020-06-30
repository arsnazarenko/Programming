package frontend;

import frontend.mvc.ObjectsMapController;
import frontend.mvc.ObjectsMapModel;
import frontend.mvc.ObjectsMapView;
import library.сlassModel.Coordinates;
import library.сlassModel.Organization;
import library.сlassModel.OrganizationType;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;

public class Test {
    static ObjectsMapView objectsMapView;

    public static void main(String[] args) throws InterruptedException {


        Deque<Organization> organizations;
        organizations = new ArrayDeque<>(Arrays.asList(new Organization("a", 1L, "adw", new Coordinates(25, 25), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("curry", 2L, "adw", new Coordinates(-1, -1), new Date(), 10, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("rose", 3L, "adw", new Coordinates(15, 15), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("ivan vladimirovich", 4L, "adw", new Coordinates(56, 56), new Date(), 512, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("polina", 5L, "adw", new Coordinates(23, 23), new Date(), 10, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("polina", 6L, "adw", new Coordinates(2, 2), new Date(), 330, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("ivan123", 7L, "adw", new Coordinates(18, 18), new Date(), 1230, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("Zion", 8L, "adw", new Coordinates(11, 1), new Date(), 5, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("lebron", 9L, "adw", new Coordinates(30, 25), new Date(), 12, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("opara", 10L, "adw", new Coordinates(25, 30), new Date(), 10230, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("kobe", 11L, "adw", new Coordinates(25, 17), new Date(), 12, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("", 12L, "adw", new Coordinates(34, 43), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("sena", 13L, "adw", new Coordinates(14, 41), new Date(), 6230, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("sena", 14L, "adw", new Coordinates(66, 55), new Date(), 12, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("sena8980990", 15L, "adw", new Coordinates(0, 100), new Date(), 1230, OrganizationType.COMMERCIAL, 12313d, null)
        ));
        SwingUtilities.invokeLater(() -> {
            ObjectsMapModel model = new ObjectsMapModel(organizations);
            int size = model.getCellSize();
            int count = model.getCellCount();
            ObjectsMapController controller = new ObjectsMapController(new ObjectsMapView(model.getOrganizationsCoordinateInfo(),
                    size, count), model);
        });




    }
}
