package frontend.mvc;

import library.сlassModel.Organization;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Deque;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectsMapModel {
    private final Deque<Organization> organizations;
    private Map<Organization, Map.Entry<Integer, Point>> organizationsCoordinateInfo;
    private int offset;
    private int cellCount;
    private final int CELL_SIZE = 15;


    public ObjectsMapModel(Deque<Organization> organizations) {
        this.organizations = organizations;
        this.offset = calcOffset(organizations);
        this.cellCount = offset * 2;
        this.organizationsCoordinateInfo = calcCoordinate(organizations);
    }



    private int calcOffset(Deque<Organization> organizations) {
        return organizations.stream().
                flatMap(o -> Stream.of(Math.round(o.getCoordinates().getX()), Math.round(o.getCoordinates().getY()))).
                mapToInt(Number::intValue).
                map(Math::abs).
                filter(o -> o > 27).
                max().
                orElse(25) + 4;
    }

    private Map<Organization, Map.Entry<Integer, Point>> calcCoordinate(Deque<Organization> organizations) {
        return organizations.stream().collect(Collectors.
                toMap(o -> o, // id
                        o -> new AbstractMap.SimpleEntry<>(Sizes.calcSize(o.getEmployeesCount()).getSizeValue(), //size
                                new Point((int) Math.round((o.getCoordinates().getX()) + offset) * CELL_SIZE, //x
                                        (cellCount - (Math.round(o.getCoordinates().getY()) + offset)) * CELL_SIZE)))); //y
    }

    public void setOrganizationsCoordinateInfo(Map<Organization, Map.Entry<Integer, Point>> organizationsCoordinateInfo) {
        this.organizationsCoordinateInfo = organizationsCoordinateInfo;
    }

    public int getCellCount() {
        return cellCount;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }
    public Deque<Organization> getOrganizations() {
        return organizations;
    }

    public Map<Organization, Map.Entry<Integer, Point>> getOrganizationsCoordinateInfo() {
        return organizationsCoordinateInfo;
    }
}
