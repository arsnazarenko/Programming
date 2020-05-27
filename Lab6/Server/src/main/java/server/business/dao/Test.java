package server.business.dao;


import library.clientCommands.UserData;
import library.сlassModel.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        List<String> list  = Arrays.asList("aaa", "bb", "asdasd", "ddd", "yyyyyy", "rrrrrr", "qqqqq", "adbs");
        System.out.println(list.stream().filter(s -> s.compareTo("lll") < 0).collect(Collectors.toList()));



//        Organization org = new Organization(null, null, "name", new Coordinates(1.1, 2.33f), new Date(), 24, OrganizationType.COMMERCIAL,
//                2.22, new Address("1awdawdaw", "zipzp", new Location(123123L, 2.22, 2.55, "asdads")));
//
//        Organization org2 = new Organization(null, null, "name", new Coordinates(1.1, 2.33f), new Date(), 24, OrganizationType.COMMERCIAL,
//                2.22, new Address("1awdawdaw", "zipzp", null));
//
//        Organization org3 = new Organization(null, null, "name", new Coordinates(1.1, 2.33f), new Date(), 24, OrganizationType.COMMERCIAL,
//                2.22, null);
        DatabaseCreator.createTables();

        OrganizationDAO dao = new OrganizationDAO(DatabaseCreator.getConnection());
        Deque<Organization> organizations = dao.readAll();
        System.out.println(organizations);
//
//        UserDaoImpl userDao = new UserDaoImpl(DatabaseCreator.getConnection());
//
//        userDao.create(new UserData("admin28", "admin123"));

//        byte[] b = userDao.read("admin28");
//        byte[] b2 = PasswordHash.passwordHash("admin123");
//        if (Arrays.equals(b, b2)) {
//            System.out.println("yes");
//        }


//        System.out.println();

//        System.out.println(dao.create(org));
//        System.out.println(dao.create(org2));
//        System.out.println(dao.create(org3));
//        System.out.println(dao.deleteByKeys(new Long[] {8L, 10L, 11L}));
        DatabaseCreator.closeConnection();

    }
}
