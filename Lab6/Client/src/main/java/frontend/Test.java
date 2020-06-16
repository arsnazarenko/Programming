package frontend;

import library.сlassModel.Coordinates;
import library.сlassModel.Organization;
import library.сlassModel.OrganizationType;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

public class Test {
    static DrawTest drawTest;

    public static void main(String[] args) throws InterruptedException {


        Deque<Organization> organizations;
        organizations = new ArrayDeque<>(Arrays.asList(new Organization("awdawd", 123L, "adw", new Coordinates(25, 25), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 12L, "adw", new Coordinates(-1, -1), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123L, "adw", new Coordinates(15, 15), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 3123L, "adw", new Coordinates(56, 56), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 12323L, "adw", new Coordinates(23, 23), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 12313L, "adw", new Coordinates(2, 2), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123123L, "adw", new Coordinates(18, 18), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123L, "adw", new Coordinates(11, 1), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 1123L, "adw", new Coordinates(30, 25), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123123L, "adw", new Coordinates(25, 30), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 3123L, "adw", new Coordinates(25, 17), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123123L, "adw", new Coordinates(34, 43), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 34123L, "adw", new Coordinates(14, 41), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 1323123L, "adw", new Coordinates(66, 55), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 1323123L, "adw", new Coordinates(0, 0), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null)
        ));

        Deque<Organization> organizations1 = new ArrayDeque<>(Arrays.asList(new Organization("awdawd", 123L, "adw", new Coordinates(4, 4), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 12L, "adw", new Coordinates(-1, -1), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123L, "adw", new Coordinates(0, 0), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 3123L, "adw", new Coordinates(1, 1), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 12323L, "adw", new Coordinates(2, 2), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 12313L, "adw", new Coordinates(3, 3), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null),
                new Organization("awdawd", 123123L, "adw", new Coordinates(5, 5), new Date(), 123, OrganizationType.COMMERCIAL, 12313d, null)

        ));
        SwingUtilities.invokeLater(() -> {drawTest = new DrawTest(organizations);
        });

        TimeUnit.SECONDS.sleep(5);

        drawTest.panelUpdate(new ArrayDeque<>());
        TimeUnit.SECONDS.sleep(5);

        drawTest.panelUpdate(organizations1);


    }
}
