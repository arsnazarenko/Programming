package frontend.graphicsInterface.mainWindow.table;



import frontend.mvc.ColorGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class Renderer extends DefaultTableCellRenderer {

    public Renderer() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setBackground(ColorGenerator.generate(( (String) table.getValueAt(row, 1))));
//        for (int i = 0; i < table.getRowCount(); i++) {
//            String login = (String) table.getValueAt(i, 1);
//            setBackground(ColorGenerator.generate(login));
//        }
        return cell;
    }
}
