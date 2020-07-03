package frontend.graphicsInterface.mainWindow.table;


import frontend.graphicsInterface.Collection;
import frontend.graphicsInterface.LocaleActionListener;
import frontend.graphicsInterface.controllers.Controllers;
import library.сlassModel.Organization;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TablePanel extends JPanel implements LocaleActionListener {

    private final String FONT;
    private JScrollPane scrollPane;
    private JTable table;
    private Collection collection;
    private ButtonsPanel buttonsPanel;
    private FilterPanel filterPanel;
    private TableModel tableModel;

    public TablePanel(String FONT, Collection collection) {
        this.FONT = FONT;
        this.collection = collection;
        buttonsPanel = new ButtonsPanel();
        tableModel = new TableModel();
        table = new JTable(tableModel);
        filterPanel = new FilterPanel();
        localeChange(Controllers.getLocale());


        createTable();
        createPanel();

    }

    private void createTable() {
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


        scrollPane = new JScrollPane(table);
    }

    private void createPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(filterPanel, BorderLayout.NORTH);
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

    private boolean filterNum(String command, int i) {
        System.out.println(i);
        if (command.equals("EQUAL") && i == 0) return true;
        else if (command.equals("AFTER") && i > 0) return true;
        else if (command.equals("BEFORE") && i < 0) return true;
        else if (command.equals("NOT_EQUAL") && i != 0) return true;
        return false;
    }

    private void handlerFilter(JTextField filterText) {
        String text = filterText.getText();
        String[] array = text.trim().toUpperCase().split("\\s");
        int indexColumn = table.getSelectedColumn();
        if (text.length() != 0 && indexColumn != -1) {
            ArrayList<Organization> filter = collection.getCollection().stream().filter(o -> {
                int i = 0;
                switch (indexColumn) {
                    case 0:
                        i = o.getId().compareTo(Long.parseLong(array[1]));
                        return filterNum(array[0], i);
                    case 1:
                        return o.getUserLogin().contains(text);
                    case 2:
                        return o.getName().contains(text);
                    case 3:
                        i = Double.compare(o.getCoordinates().getX(), Double.parseDouble(array[1]));
                        return filterNum(array[0], i);
                    case 4:
                        i = Float.compare(o.getCoordinates().getY(), Float.parseFloat(array[1]));
                        return filterNum(array[0], i);
                    case 5:
                        DateFormat date = null;
                        String d = null;
                        if (Controllers.getLocale().equals(new Locale("ru")) || Controllers.getLocale().equals(new Locale("pl"))) {
                            date = new SimpleDateFormat("dd.MM.yyyy");
                            d = array[1];
                        } else if (Controllers.getLocale().equals(new Locale("es_GT"))) {
                            date = new SimpleDateFormat("yyyy-MM-dd");
                            d = array[1];
                        } else if ((Controllers.getLocale().equals(new Locale("sk")))) {
                            date = new SimpleDateFormat("dd. MM. yyyy");
                            d = array[1] + " " + array[2] + " " + array[3];
                        }
                        try {
                            Date date_1 = date.parse(date.format(o.getCreationDate()));
                            Date date_2 = date.parse(d);
                            i = date_1.compareTo(date_2);
                            return filterNum(array[0], i);
                        } catch (ParseException e) {
                            //JOptionPane.showMessageDialog(null, errorConstants.getError_2(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
                            return false;
                        }

                    case 6:
                        i = o.getEmployeesCount().compareTo(Integer.parseInt(array[1]));
                        return filterNum(array[0], i);
                    case 7:
                        return o.getType().toString().contains(text);
                    case 8:
                        if (o.getAnnualTurnover() == null) return false;
                        i = o.getAnnualTurnover().compareTo(Double.parseDouble(array[1]));
                        return filterNum(array[0], i);
                    case 9:
                        if (o.getOfficialAddress() == null) return false;
                        if (o.getOfficialAddress().getStreet() == null) return false;
                        return o.getOfficialAddress().getStreet().contains(text);
                    case 10:
                        if (o.getOfficialAddress() == null) return false;
                        if (o.getOfficialAddress().getZipCode() == null) return false;
                        return o.getOfficialAddress().getZipCode().contains(text);
                    case 11:
                        if (o.getOfficialAddress() == null) return false;
                        if (o.getOfficialAddress().getTown() == null) return false;
                        i = o.getOfficialAddress().getTown().getX().compareTo(Long.parseLong(array[1]));
                        return filterNum(array[0], i);
                    case 12:
                        if (o.getOfficialAddress() == null) return false;
                        if (o.getOfficialAddress().getTown() == null) return false;
                        i = o.getOfficialAddress().getTown().getY().compareTo(Double.parseDouble(array[1]));
                        return filterNum(array[0], i);
                    case 13:
                        if (o.getOfficialAddress() == null) return false;
                        if (o.getOfficialAddress().getTown() == null) return false;
                        i = Double.compare(o.getOfficialAddress().getTown().getZ(), Double.parseDouble(array[1]));
                        return filterNum(array[0], i);
                    case 14:
                        if (o.getOfficialAddress() == null) return false;
                        if (o.getOfficialAddress().getTown() == null) return false;
                        return o.getOfficialAddress().getTown().getName().contains(text);
                }
                return false;
            }).collect(Collectors.toCollection(ArrayList::new));
            tableModel.setDataList(collection.getTableData(filter));
        }
    }


    public void updateData() {
        tableModel.setDataList(collection.getTableData());
    }

    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate", locale);
        buttonsPanel.addButton.setText(bundle.getString("add"));
        buttonsPanel.updateButton.setText(bundle.getString("update"));
        buttonsPanel.removeButton.setText(bundle.getString("remove"));
        filterPanel.filterButton.setText(bundle.getString("filter"));
        filterPanel.filterLabel.setText(bundle.getString("filter"));
        filterPanel.sortButton.setText(bundle.getString("sort"));
        filterPanel.sortButton.setToolTipText(bundle.getString("mg_sort"));
        filterPanel.filterText.setToolTipText(bundle.getString("mg_filter"));
        String[] columnName = new String[]{"id", bundle.getString("login"), bundle.getString("name"), bundle.getString("coordinate") + " X",
                bundle.getString("coordinate") + " Y", bundle.getString("creation_Date"), bundle.getString("employees"),
                bundle.getString("type"), bundle.getString("annual_turnover"), bundle.getString("street"),
                bundle.getString("zipcode"), "X", "Y", "Z", bundle.getString("Lname")};
        for (int i = 0; i < 15; i++) {
            table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(columnName[i]);
        }
        updateData();
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public ButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    public Collection getCollection() {
        return collection;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public class ButtonsPanel extends JPanel {
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

        public JButton getRemoveButton() {
            return removeButton;
        }

        public JButton getUpdateButton() {
            return updateButton;
        }

    }

    public class FilterPanel extends JPanel {
        private JLabel filterLabel;
        private JTextField filterText;
        private JButton filterButton;
        private JButton sortButton;

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

            sortButton = new JButton();
            sortButton.setFont(new Font(FONT, Font.PLAIN, 12));
            sortButton.setBackground(Color.LIGHT_GRAY);
            sortButton.setFocusPainted(false);
            sortButton.addActionListener(a -> sorted());

            add(filterLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 5, 1, 5),
                    0, 0));
            add(filterText, new GridBagConstraints(1, 0, 1, 1, 7, 1,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                    0, 0));
            add(filterButton, new GridBagConstraints(2, 0, 1, 1, 1, 1,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                    0, 0));
            add(sortButton, new GridBagConstraints(3, 0, 1, 1, 1, 1,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                    0, 0));
        }

        public void sorted() {
            int column = table.getSelectedColumn();
            ArrayList<Organization> sort = collection.getCollection().stream().sorted((o1, o2) -> {
                switch (column) {
                    case 0:
                        return o1.getId().compareTo(o2.getId());
                    case 1:
                        return o1.getUserLogin().compareTo(o2.getUserLogin());
                    case 2:
                        return o1.getName().compareTo(o2.getName());
                    case 3:
                        return Double.compare(o1.getCoordinates().getX(), o2.getCoordinates().getX());
                    case 4:
                        return Float.compare(o1.getCoordinates().getY(), o2.getCoordinates().getY());
                    case 5:
                        return o1.getCreationDate().compareTo(o2.getCreationDate());
                    case 6:
                        return o1.getEmployeesCount().compareTo(o2.getEmployeesCount());
                    case 7:
                        return o1.getType().compareTo(o2.getType());
                    case 8:
                        if (o1.getAnnualTurnover() == null) return -1;
                        else if (o2.getAnnualTurnover() == null) return 1;
                        return o1.getAnnualTurnover().compareTo(o2.getAnnualTurnover());
                    case 9:
                        if (o1.getOfficialAddress() == null) return -1;
                        else if (o2.getOfficialAddress() == null) return 1;
                        return o1.getOfficialAddress().getStreet().compareTo(o2.getOfficialAddress().getStreet());
                    case 10:
                        if (o1.getOfficialAddress() == null) return -1;
                        else if (o2.getOfficialAddress() == null) return 1;
                        return o1.getOfficialAddress().getZipCode().compareTo(o2.getOfficialAddress().getZipCode());
                    case 11:
                        if (o1.getOfficialAddress() == null) return -1;
                        else if (o2.getOfficialAddress() == null) return 1;
                        else if (o1.getOfficialAddress().getTown() == null) return -1;
                        else if (o2.getOfficialAddress().getTown() == null) return 1;
                        return o1.getOfficialAddress().getTown().getX().compareTo(o2.getOfficialAddress().getTown().getX());
                    case 12:
                        if (o1.getOfficialAddress() == null) return -1;
                        else if (o2.getOfficialAddress() == null) return 1;
                        else if (o1.getOfficialAddress().getTown() == null) return -1;
                        else if (o2.getOfficialAddress().getTown() == null) return 1;
                        return o1.getOfficialAddress().getTown().getY().compareTo(o2.getOfficialAddress().getTown().getY());
                    case 13:
                        if (o1.getOfficialAddress() == null) return -1;
                        else if (o2.getOfficialAddress() == null) return 1;
                        else if (o1.getOfficialAddress().getTown() == null) return -1;
                        else if (o2.getOfficialAddress().getTown() == null) return 1;
                        return Double.compare(o1.getOfficialAddress().getTown().getZ(), o2.getOfficialAddress().getTown().getZ());
                    case 14:
                        if (o1.getOfficialAddress() == null) return -1;
                        else if (o2.getOfficialAddress() == null) return 1;
                        else if (o1.getOfficialAddress().getTown() == null) return -1;
                        else if (o2.getOfficialAddress().getTown() == null) return 1;
                        return o1.getOfficialAddress().getTown().getName().compareTo(o2.getOfficialAddress().getTown().getName());
                }
                return 0;
            }).collect(Collectors.toCollection(ArrayList::new));
            tableModel.setDataList(collection.getTableData(sort));
        }

        private void filter() {
            ResourceBundle bundle = ResourceBundle.getBundle("translate", Controllers.getLocale());
            Object o[] = {bundle.getString("ok")};
            try {
                handlerFilter(filterText);
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showOptionDialog(null,
                        bundle.getString("error_3"),bundle.getString("error_title"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, o, o[0]);
            } catch (IllegalArgumentException e) {
                JOptionPane.showOptionDialog(null,
                        bundle.getString("error_4"), bundle.getString("error_title"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, o, o[0]);
            }
        }

        public JLabel getFilterLabel() {
            return filterLabel;
        }

        public JTextField getFilterText() {
            return filterText;
        }

        public JButton getFilterButton() {
            return filterButton;
        }

    }
}
