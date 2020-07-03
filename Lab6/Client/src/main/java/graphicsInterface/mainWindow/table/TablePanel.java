package graphicsInterface.mainWindow.table;

import graphicsInterface.LocaleActionListener;
import graphicsInterface.ErrorConstants;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.PatternSyntaxException;

public class TablePanel extends JPanel implements LocaleActionListener {

    private JScrollPane scrollPane;
    private JTable table;
    private TableModel tableModel;
    private TableRowSorter<TableModel> sorter;
    private ButtonsPanel buttonsPanel;
    private FilterPanel filterPanel;
    private ErrorConstants errorConstants;

    private final String FONT;

    public TablePanel(String FONT, Locale locale, ErrorConstants errorConstants) {
        this.FONT = FONT;
        this.errorConstants = errorConstants;
        tableModel = new TableModel();
        table = new JTable(tableModel);
        buttonsPanel = new ButtonsPanel();
        filterPanel = new FilterPanel();
        localeChange(locale);
        createTable();
        createPanel();

    }
    private void createTable(){
        table.getTableHeader().setFont(new Font(FONT, Font.BOLD, 12));
        table.setFont(new Font(FONT, Font.PLAIN, 12));

        //Задаем Ширину столбцов
        setColumnsWidth(table);

        //Выделение по столбцам
        table.setColumnSelectionAllowed(true);

        //Запрещает перетаскивать колонки
        table.getTableHeader().setReorderingAllowed(false);
        //Раскрашивание объектов
        //  for (int i = 0; i < table.getColumnCount(); i++) {
        //      table.getColumnModel().getColumn(i).setCellRenderer(render);
        // }


        //Cортировка
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        scrollPane = new JScrollPane(table);
    }

    private void createPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(filterPanel,BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setColumnsWidth(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(170);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(205);
        table.getColumnModel().getColumn(8).setPreferredWidth(175);
        table.getColumnModel().getColumn(9).setPreferredWidth(100);
        table.getColumnModel().getColumn(10).setPreferredWidth(70);
        table.getColumnModel().getColumn(11).setPreferredWidth(35);
        table.getColumnModel().getColumn(12).setPreferredWidth(35);
        table.getColumnModel().getColumn(13).setPreferredWidth(35);
        table.getColumnModel().getColumn(14).setPreferredWidth(150);

    }

    private RowFilter<Object, Object> handlerFilter(JTextField filterText) throws ParseException {
        String text = filterText.getText();
        String[] array = text.trim().split("\\s");
        int indexColumn = table.getSelectedColumn();
        System.out.println(indexColumn);
        if (text.length() == 0 || indexColumn == -1) {
            return null;
        } else {
            switch (indexColumn) {
                case 0:
                case 11:
                    return RowFilter.numberFilter(RowFilter.ComparisonType.valueOf(array[0]), Long.parseLong(
                            array[1]), indexColumn);
                case 1:
                case 2:
                case 7:
                case 9:
                case 10:
                case 14:
                    return RowFilter.regexFilter(filterText.getText(), indexColumn);
                case 3:
                case 8:
                case 12:
                case 13:
                    return RowFilter.numberFilter(RowFilter.ComparisonType.valueOf(array[0]), Double.parseDouble(
                            array[1]), indexColumn);
                case 4:
                    return RowFilter.numberFilter(RowFilter.ComparisonType.valueOf(array[0]), Float.parseFloat(
                            array[1]), indexColumn);
                case 5:
                    return RowFilter.dateFilter(RowFilter.ComparisonType.valueOf(array[0]),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").
                                    parse(array[1] + " " + array[2]), 5);
                case 6:
                    return RowFilter.numberFilter(RowFilter.ComparisonType.valueOf(array[0]), Integer.parseInt(
                            array[1]), indexColumn);
                default:
                    return null;
            }
        }

    }

    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        buttonsPanel.addButton.setText(bundle.getString("add"));
        buttonsPanel.updateButton.setText(bundle.getString("update"));
        buttonsPanel.removeButton.setText(bundle.getString("remove"));
        filterPanel.filterButton.setText(bundle.getString("filter"));
        filterPanel.filterLabel.setText(bundle.getString("filter"));
        filterPanel.filterText.setToolTipText(bundle.getString("mg_filter"));
        String[] columnName = new String[]{"id", bundle.getString("login"), bundle.getString("name"), bundle.getString("coordinate") + " X",
                bundle.getString("coordinate") + " Y", bundle.getString("creation_Date"), bundle.getString("employees"),
                bundle.getString("type"), bundle.getString("annual_turnover"), bundle.getString("street"),
                bundle.getString("zipcode"), "X", "Y", "Z", bundle.getString("Lname")};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderValue(columnName[i]);
        }
    }


    public class ButtonsPanel extends JPanel{
        private JButton addButton;
        private JButton removeButton;
        private JButton updateButton;

        {
            setLayout(new GridLayout(1, 3, 1, 1));
            setBackground(Color.WHITE);

            addButton = new JButton();
            addButton.setFont(new Font(FONT, Font.PLAIN, 12));
            addButton.setFocusPainted(false);
            addButton.setBackground(Color.LIGHT_GRAY);

            removeButton = new JButton();
            removeButton.setFont(new Font(FONT, Font.PLAIN, 12));
            removeButton.setFocusPainted(false);
            removeButton.setBackground(Color.LIGHT_GRAY);

            updateButton = new JButton();
            updateButton.setFont(new Font(FONT, Font.PLAIN, 12));
            updateButton.setFocusPainted(false);
            updateButton.setBackground(Color.LIGHT_GRAY);

            add(addButton);
            add(updateButton);
            add(removeButton);

        }

        public JButton getAddButton() {
            return addButton;
        }

        public void setAddButton(JButton addButton) {
            this.addButton = addButton;
        }

        public JButton getRemoveButton() {
            return removeButton;
        }

        public void setRemoveButton(JButton removeButton) {
            this.removeButton = removeButton;
        }

        public JButton getUpdateButton() {
            return updateButton;
        }

        public void setUpdateButton(JButton updateButton) {
            this.updateButton = updateButton;
        }
    }

    public class FilterPanel extends JPanel{
        private JLabel filterLabel;
        private JTextField filterText;
        private JButton filterButton;

        {
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);

            filterLabel = new JLabel();
            filterLabel.setFont(new Font(FONT, Font.PLAIN, 12));
            filterText = new JTextField();
            filterText.setFont(new Font(FONT, Font.PLAIN, 12));

            filterButton = new JButton();
            filterButton.setFont(new Font(FONT, Font.PLAIN, 12));
            filterButton.setBackground(Color.LIGHT_GRAY);
            filterButton.setFocusPainted(false);
            filterButton.addActionListener(a -> filter());


            add(filterLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 5, 1, 5),
                    0, 0));
            add(filterText, new GridBagConstraints(1, 0, 1, 1, 7, 1,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                    0, 0));
            add(filterButton, new GridBagConstraints(2, 0, 1, 1, 1, 1,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                    0, 0));

        }

        private void filter(){
            try {
                sorter.setRowFilter(handlerFilter(filterText));
            } catch (PatternSyntaxException e) {
                JOptionPane.showMessageDialog(null,errorConstants.getError_1(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.err.println("Ошибка в регулярном выражении!");
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null,errorConstants.getError_2(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.out.println("Неверный формат даты");
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null,errorConstants.getError_3(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.out.println("Неверный формат команды!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null,errorConstants.getError_4(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.out.println("Некорректный ввод!");
            }
        }

        public JLabel getFilterLabel() {
            return filterLabel;
        }

        public void setFilterLabel(JLabel filterLabel) {
            this.filterLabel = filterLabel;
        }

        public JTextField getFilterText() {
            return filterText;
        }

        public void setFilterText(JTextField filterText) {
            this.filterText = filterText;
        }

        public JButton getFilterButton() {
            return filterButton;
        }

        public void setFilterButton(JButton filterButton) {
            this.filterButton = filterButton;
        }
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }


    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    public void setSorter(TableRowSorter<TableModel> sorter) {
        this.sorter = sorter;
    }

    public ButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public void setButtonsPanel(ButtonsPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    public void setFilterPanel(FilterPanel filterPanel) {
        this.filterPanel = filterPanel;
    }

    public ErrorConstants getErrorConstants() {
        return errorConstants;
    }

    public void setErrorConstants(ErrorConstants errorConstants) {
        this.errorConstants = errorConstants;
    }


    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
}
