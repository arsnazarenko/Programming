package server.business.dao;


import library.сlassModel.*;

import java.util.Date;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {

        Organization org = new Organization(null, "name", new Coordinates(1.1, 2.33f), new Date(), 24, OrganizationType.COMMERCIAL,
                2.22, new Address("1awdawdaw", "zipzp", new Location(123123L, 2.22, 2.55, "asdads")));

        Organization org2 = new Organization(null, "name", new Coordinates(1.1, 2.33f), new Date(), 24, OrganizationType.COMMERCIAL,
                2.22, new Address("1awdawdaw", "zipzp", null));

        Organization org3 = new Organization(null, "name", new Coordinates(1.1, 2.33f), new Date(), 24, OrganizationType.COMMERCIAL,
                2.22, null);
        DatabaseCreator.createTables();

        OrganizationDAO dao = new OrganizationDAO(DatabaseCreator.getConnection());

//        System.out.println(dao.create(org));
//        System.out.println(dao.create(org2));
//        System.out.println(dao.create(org3));

        System.out.println(dao.deleteByKeys(new Long[] {8L, 10L, 11L}));
        DatabaseCreator.closeConnection();
    }
}
