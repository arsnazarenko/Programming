package graphicsInterface.mainWindow.table;

import library.clientCommands.commandType.RemoveIdCommand;
import library.clientCommands.commandType.ShowCommand;
import library.сlassModel.*;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class TableModel extends AbstractTableModel{
    private  ArrayList<Organization> dataList;

    public TableModel() {
        dataList = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return 15;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Organization organization = dataList.get(rowIndex);
        Coordinates coordinates = organization.getCoordinates();
        Address address = organization.getOfficialAddress();
        Location location = address != null ? address.getTown() : null;
        switch (columnIndex) {
            case 0: return organization.getId();
            case 1: return organization.getUserLogin();
            case 2: return organization.getName();
            case 3: return coordinates != null ? coordinates.getX() : null;
            case 4: return coordinates != null ? coordinates.getY() : null;
            case 5: return organization.getCreationDate();
            case 6: return organization.getEmployeesCount();
            case 7: return organization.getType();
            case 8: return organization.getAnnualTurnover();
            case 9: return address != null ? address.getStreet() : null;
            case 10: return address != null ? address.getZipCode() : null;
            case 11: return location != null ? location.getX() : null;
            case 12: return location != null ? location.getY() : null;
            case 13: return location != null ? location.getZ() : null;
            case 14: return location != null ? location.getName() : null;
        }
        return null;
    }


    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 11:
                return Long.class;
            case 1:
            case 2:
            case 9:
            case 10:
            case 14:
                return String.class;
            case 3:
            case 8:
            case 12:
            case 13:
                return Double.class;
            case 4: return Float.class;
            case 5: return Date.class;
            case 6: return Integer.class;
            case 7: return OrganizationType.class;
        }
        return String.class;
    }

    public void addData(Organization org) {
        dataList.add(org);
    }

    public void removeData(int rowIndex){


    }

    public void updateData(int rowIndex){
        new Organization(null,null,(String)getValueAt(rowIndex,2),
                new Coordinates((Double)getValueAt(rowIndex,3),(Float)getValueAt(rowIndex,4)),
                null,(Integer)getValueAt(rowIndex,6),(OrganizationType)getValueAt(rowIndex,7),
                (Double) getValueAt(rowIndex,8),new Address((String)getValueAt(rowIndex,9),
                (String)getValueAt(rowIndex,10),new Location((Long) getValueAt(rowIndex,11),
                (Double)getValueAt(rowIndex,12),(Double)getValueAt(rowIndex,13),(String)getValueAt(rowIndex,14))));
        fireTableDataChanged();
    }

    public ArrayList<Organization> getDataList() {
        return dataList;

    }

    public void setDataList(ArrayList<Organization> dataList) {
        this.dataList = dataList;
        fireTableDataChanged();
    }
}
