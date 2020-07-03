package frontend.graphicsInterface;


import frontend.graphicsInterface.controllers.Controllers;
import library.сlassModel.Organization;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class Collection {
    private ArrayList<Organization> collection = new ArrayList<>();

    public ArrayList<Object[]> getTableData() {
        return  collection.stream().map(o -> toLocalizedArray(Controllers.getLocale(), o)).collect(Collectors.toCollection(ArrayList::new));


    }

    public ArrayList<Object[]> getTableData(ArrayList<Organization> arrayList) {
        return  arrayList.stream().map(o -> toLocalizedArray(Controllers.getLocale(), o)).collect(Collectors.toCollection(ArrayList::new));


    }

    public ArrayList<Organization> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<Organization> collection) {
        this.collection = collection;
    }

    private Object[] toLocalizedArray(Locale locale, Organization organization) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        String street = null;
        String zipcode = null;
        Long x = null;
        Double y = null;
        Double z = null;
        String n = null;
        if (organization.getOfficialAddress() != null) {
            street = organization.getOfficialAddress().getStreet();
            zipcode = organization.getOfficialAddress().getZipCode();
            if (organization.getOfficialAddress().getTown() != null) {
                x = organization.getOfficialAddress().getTown().getX();
                y = organization.getOfficialAddress().getTown().getY();
                z = organization.getOfficialAddress().getTown().getZ();
                n = organization.getOfficialAddress().getTown().getName();
            }
        }
        return new Object[]{
                nf.format(organization.getId()),
                organization.getUserLogin(),
                organization.getName(),
                nf.format(organization.getCoordinates().getX()),
                nf.format(organization.getCoordinates().getY()),
                df.format(organization.getCreationDate()),
                nf.format(organization.getEmployeesCount()),
                organization.getType(),
                organization.getAnnualTurnover() == null? null:nf.format(organization.getAnnualTurnover()),
                street,
                zipcode,
                x == null?null:nf.format(x),
                y == null?null:nf.format(y),
                z == null?null:nf.format(z),
                n
        };
    }
}
