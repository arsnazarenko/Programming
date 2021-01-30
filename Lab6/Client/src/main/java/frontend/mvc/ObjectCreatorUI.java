package frontend.mvc;

import client.servises.ObjectDataValidatorConsole;
import library.сlassModel.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class ObjectCreatorUI {
    private List<String> validateResults;
    private ObjectDataValidatorConsole validator;
    private OrganizationView view;


    public ObjectCreatorUI(ObjectDataValidatorConsole validator) {
        this.validator = validator;
    }

    public Organization create(boolean addressEnabled, boolean townEnabled) {
        this.validateResults = new ArrayList<>();
        String name = setName();
        double coordX = setX();
        float coordY = setY();
        Double annualTurnover = setAnnualTurnover();
        Integer employeesCount = setEmployeesCount();
        OrganizationType type = setOrganizationType();
        Address officialAddress = null;
        Location town = null;
        if(addressEnabled) {
            String street = setStreet();
            String zipCode = setZipCode();
            if(townEnabled) {
                Long x = setTownX();
                Double y = setTownY();
                Double z = setTownZ();
                String townName = setTownName();
                town = new Location(x, y, z, name);
            }
            officialAddress = new Address(street, zipCode, town);
        }

        if(!validateResults.isEmpty()) {
            return null;
        }
        System.out.println(name + " " + coordX + " " + coordY + " " + annualTurnover + " " + employeesCount + " " + type + " " + officialAddress);
        return new Organization(null, null, name, new Coordinates(coordX, coordY), null,
                employeesCount, type, annualTurnover, officialAddress);
    }

    private double setTownZ() {
        JTextField zText = view.getTownZText();
        String str = zText.getText();
        if (validator.locationZValidate(str)) {
            return Double.parseDouble(str);
        }
        addIncorrectFieldMessage(zText);
        return 0;
    }

    private String setTownName() {
        JTextField nameText = view.getTownNameText();
        String str = nameText.getText();
        if (validator.stringFieldValidate(str)) {
            return str;
        }
        addIncorrectFieldMessage(nameText);
        return null;
    }

    private Double setTownY() {
        JTextField yText = view.getTownYText();
        String str = yText.getText();
        if (validator.locationYValidate(str)) {
            return Double.parseDouble(str);
        }
        addIncorrectFieldMessage(yText);
        return null;
    }

    private Long setTownX() {
        JTextField xText = view.getTownXText();
        String str = xText.getText();
        if (validator.locationXValidate(str)) {
            return Long.parseLong(str);
        }
        addIncorrectFieldMessage(xText);
        return null;
    }

    private String setZipCode() {
        JTextField zipCodeText = view.getAddressZipCodeText();
        String str = zipCodeText.getText();
        if (validator.zipCodeValidate(str)) {
            return str;
        }
        addIncorrectFieldMessage(zipCodeText);
        return null;
    }

    private String setStreet() {
        JTextField streetText = view.getAddressStreetText();
        String str = streetText.getText();
        if (validator.stringFieldValidate(str)) {
            return str;
        }
        addIncorrectFieldMessage(streetText);
        return null;
    }

    private OrganizationType setOrganizationType() {
        JComboBox<String> type = view.getTypeBox();
        String str = type.getItemAt(type.getSelectedIndex());
        if (validator.organizationTypeValidate(str)) {
            return OrganizationType.valueOf(str);
        }
        return null;
    }

    private Integer setEmployeesCount() {
        JTextField emplCountText = view.getEmployeesCountText();
        String str = emplCountText.getText();
        if (validator.employeesCountValidate(str)) {
            return Integer.parseInt(str);
        }
        addIncorrectFieldMessage(emplCountText);
        return null;
    }

    private Double setAnnualTurnover() {
        JTextField annualTurnoverText = view.getAnnualTurnoverText();
        String str = annualTurnoverText.getText();
        if (validator.annualTurnoverValidate(str)) {
            if (str.equals("")) return null;
            return Double.parseDouble(str);
        }
        addIncorrectFieldMessage(annualTurnoverText);
        return null;
    }

    private float setY() {
        JTextField yText = view.getCoordsYText();
        String str = yText.getText();
        if (validator.coordsYValidate(str)) {
            return Float.parseFloat(str);
        }
        addIncorrectFieldMessage(yText);
        return 0;
    }

    private double setX() {
        JTextField xText = view.getCoordsXText();
        String str = xText.getText();
        if (validator.coordsXValidate(str)) {
            return Double.parseDouble(str);
        }
        addIncorrectFieldMessage(xText);
        return 0;
    }


    private String setName() {
        JTextField nameText = view.getNameText();
        String str = nameText.getText();
        if (validator.stringFieldValidate(str)) {
            return str;
        }
        addIncorrectFieldMessage(nameText);
        return null;
    }

    private void addIncorrectFieldMessage(JTextField text) {
        validateResults.add(view.getComponentsPairs().get(text).getText());
    }

    public void setView(OrganizationView view) {
        this.view = view;
    }

    public List<String> getValidateResults() {
        return validateResults;
    }
}
