package server.business;



import library.сlassModel.Organization;

import java.util.Date;


public class FieldSetter {
    public Organization setId(Organization organization, Long id) {
        organization.setId(id);
        return organization;
    }

    public Organization setDateNow(Organization organization) {
        organization.setCreationDate(new Date());
        return organization;
    }
}
