package frontend.mvc;
import library.сlassModel.OrganizationType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class OrganizationView {
    private JFrame frame;
    private JLabel name;
    private JLabel coordsX;
    private JLabel coordsY;
    private JLabel employeesCount;
    private JLabel type;
    private JLabel annualTurnover;
    private JLabel address;
    private JLabel addressStreet;
    private JLabel addressZipCode;
    private JLabel town;
    private JLabel townX;
    private JLabel townY;
    private JLabel townZ;
    private JLabel townName;

    private JTextField nameText;
    private JTextField coordsXText;
    private JTextField coordsYText;
    private JTextField employeesCountText;
    private JComboBox<String> typeBox; //fixme: выдвижной список с константами
    private JTextField annualTurnoverText;
    private JRadioButton addressRadioButton; //fixme: кнопка управляющая активностью других
    private JTextField addressStreetText;
    private JTextField addressZipCodeText;
    private JRadioButton townRadioButton; //fixme: кнопка управляющая активностью других
    private JTextField townXText;
    private JTextField townYText;
    private JTextField townZText;
    private JTextField townNameText;

    private JButton saveObjectButton;
    private JButton clearButton;

    private final List<JComponent> addressComponents = new ArrayList<>();
    private final List<JComponent> townComponents = new ArrayList<>();
    private final List<JTextField> addressTextComponents = new ArrayList<>();
    private final List<JTextField> townTextComponents = new ArrayList<>();
    private final List<JTextField> organizationTextComponents = new ArrayList<>();
    private  Map<JTextField, JLabel> componentsPairs;



    private final String[] typeValues = Arrays.stream(OrganizationType.values()).map(Enum::name).toArray(String[]::new);


    public OrganizationView() {
        frame = new JFrame("Organization");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        name = new JLabel("Name ");
        coordsX = new JLabel("x ");
        coordsY = new JLabel("y ");
        employeesCount = new JLabel("EmployeesCount ");
        type = new JLabel("Type ");
        annualTurnover = new JLabel("AnnualTurnover ");
        address = new JLabel("Address ");
        addressStreet = new JLabel("Street ");
        addressZipCode = new JLabel("ZipCode ");
        town = new JLabel("Town ");
        townX = new JLabel("x ");
        townY = new JLabel("y ");
        townZ = new JLabel("z ");
        townName = new JLabel("Name ");

        nameText = new JTextField();
        coordsXText = new JTextField();
        coordsYText = new JTextField();
        employeesCountText = new JTextField();
        typeBox = new JComboBox<>(typeValues);
        annualTurnoverText = new JTextField();
        addressRadioButton = new JRadioButton();
        addressStreetText = new JTextField();
        addressZipCodeText = new JTextField();
        townRadioButton = new JRadioButton();
        townXText = new JTextField();
        townYText = new JTextField();
        townZText = new JTextField();
        townNameText = new JTextField();


        saveObjectButton = new JButton("Save");
        clearButton = new JButton("Clear");
        organizationTextComponents.addAll(Arrays.asList(nameText, coordsXText, coordsYText, employeesCountText, annualTurnoverText));
        addressTextComponents.addAll(Arrays.asList(addressStreetText, addressZipCodeText));
        townTextComponents.addAll(Arrays.asList(townXText, townYText, townZText, townNameText));
        addressComponents.addAll(addressTextComponents);
        addressComponents.addAll(Arrays.asList(addressStreet, addressZipCode, town, townRadioButton));
        townComponents.addAll(townTextComponents);
        townComponents.addAll(Arrays.asList(townX, townY, townZ, townName));

        for(JComponent component: addressComponents) {
            component.setEnabled(false);
        }
        for (JComponent component: townComponents) {
            component.setEnabled(false);
        }

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(name)
                        .addComponent(coordsX)
                        .addComponent(coordsY)
                        .addComponent(employeesCount)
                        .addComponent(type)
                        .addComponent(annualTurnover)
                        .addComponent(address)
                        .addComponent(addressStreet)
                        .addComponent(addressZipCode)
                        .addComponent(town)
                        .addComponent(townX)
                        .addComponent(townY)
                        .addComponent(townZ)
                        .addComponent(townName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameText)
                        .addComponent(coordsXText)
                        .addComponent(coordsYText)
                        .addComponent(employeesCountText)
                        .addComponent(typeBox)
                        .addComponent(annualTurnoverText)
                        .addComponent(addressRadioButton)
                        .addComponent(addressStreetText)
                        .addComponent(addressZipCodeText)
                        .addComponent(townRadioButton)
                        .addComponent(townXText)
                        .addComponent(townYText)
                        .addComponent(townZText)
                        .addComponent(townNameText)
                        .addComponent(clearButton)
                        .addComponent(saveObjectButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup().
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(name).
                        addComponent(nameText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(coordsX).
                        addComponent(coordsXText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(coordsY).
                        addComponent(coordsYText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(employeesCount).
                        addComponent(employeesCountText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(type).
                        addComponent(typeBox))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(annualTurnover).
                        addComponent(annualTurnoverText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(address).
                        addComponent(addressRadioButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(addressStreet).
                        addComponent(addressStreetText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(addressZipCode).
                        addComponent(addressZipCodeText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(town).
                        addComponent(townRadioButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(townX).
                        addComponent(townXText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(townY).
                        addComponent(townYText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(townZ).
                        addComponent(townZText))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(townName).
                        addComponent(townNameText))
                .addComponent(clearButton)
                .addComponent(saveObjectButton)
        );
        frame.getContentPane().setLayout(layout);
    }


    public JRadioButton getAddressRadioButton() {
        return addressRadioButton;
    }

    public JRadioButton getTownRadioButton() {
        return townRadioButton;
    }


    public JButton getSaveObjectButton() {
        return saveObjectButton;
    }

    public List<JComponent> getAddressComponents() {
        return addressComponents;
    }

    public List<JComponent> getTownComponents() {
        return townComponents;
    }

    public List<JTextField> getAddressTextComponents() {
        return addressTextComponents;
    }

    public List<JTextField> getTownTextComponents() {
        return townTextComponents;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public List<JTextField> getOrganizationTextComponents() {
        return organizationTextComponents;
    }

    public JComboBox<String> getTypeBox() {
        return typeBox;
    }

    public JTextField getNameText() {
        return nameText;
    }

    public JTextField getCoordsXText() {
        return coordsXText;
    }

    public JTextField getCoordsYText() {
        return coordsYText;
    }

    public JTextField getEmployeesCountText() {
        return employeesCountText;
    }

    public JTextField getAnnualTurnoverText() {
        return annualTurnoverText;
    }

    public JTextField getAddressStreetText() {
        return addressStreetText;
    }

    public JTextField getAddressZipCodeText() {
        return addressZipCodeText;
    }

    public JTextField getTownXText() {
        return townXText;
    }

    public JTextField getTownYText() {
        return townYText;
    }

    public JTextField getTownZText() {
        return townZText;
    }

    public JTextField getTownNameText() {
        return townNameText;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Map<JTextField, JLabel> getComponentsPairs() {
        if(componentsPairs == null) {
            componentsPairs = new HashMap<>();
            componentsPairs.put(nameText, name);
            componentsPairs.put(coordsXText, coordsX);
            componentsPairs.put(coordsYText, coordsY);
            componentsPairs.put(employeesCountText, employeesCount);
            componentsPairs.put(annualTurnoverText, annualTurnover);
            componentsPairs.put(addressStreetText, addressStreet);
            componentsPairs.put(addressZipCodeText, addressZipCode);
            componentsPairs.put(townXText, townX);
            componentsPairs.put(townYText, townY);
            componentsPairs.put(townZText, townZ);
            componentsPairs.put(townNameText, townName);
            return componentsPairs;
        }
        return componentsPairs;
    }
}
