package frontend.graphicsInterface.mainWindow.table;

import library.сlassModel.*;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class TableModel extends AbstractTableModel{
    private  ArrayList<Object[]> dataList;

    public TableModel() {
        dataList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            dataList.add(new Object[getColumnCount()]);
        }
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
        Object[] o = dataList.get(rowIndex);
      return o[columnIndex];
    }

    public ArrayList<Object[]> getDataList() {
        return dataList;

    }

    public void setDataList(ArrayList<Object[]> data) {
        this.dataList = data;
        fireTableDataChanged();
        dataList.forEach(o-> System.out.println(Arrays.toString(o)));
        System.out.println(dataList.size());
    }


    public void addDate(Object[] row){
        Object[]rowTable = new Object[getColumnCount()];
        rowTable = row;
        dataList.add(rowTable);
    }
}
