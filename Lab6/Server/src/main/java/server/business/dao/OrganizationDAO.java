package server.business.dao;

import library.сlassModel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class OrganizationDAO implements ObjectDAO<Organization, Long> {

    private static final Logger logger = LogManager.getLogger(OrganizationDAO.class);
    private final Connection connection;

    public OrganizationDAO(Connection connection) {
        this.connection = connection;
    }

    public Deque<Organization> readAll() {
        Deque<Organization> organizations = new ArrayDeque<>();
        try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.SELECT_ALL.query)) {
            try(ResultSet resultSet = ps.executeQuery()) {
                while(resultSet.next()) {
                    Organization organization = new Organization();
                    organization.setId(resultSet.getLong("id"));
                    organization.setUserLogin(resultSet.getString("login"));
                    organization.setName(resultSet.getString("name"));
                    organization.setCoordinates(new Coordinates(resultSet.getDouble("coordx"), resultSet.getFloat("coordy")));
                    organization.setCreationDate(resultSet.getTimestamp("creation_date"));
                    organization.setEmployeesCount(resultSet.getInt("employees_count"));
                    organization.setType(OrganizationType.valueOf(resultSet.getString("type")));
                    organization.setAnnualTurnover(resultSet.getDouble("annualturnover"));
                    Address address = null;
                    if(resultSet.getLong("officialaddress") != 0) {
                        String street = resultSet.getString("addrstreet");
                        String zipcode = resultSet.getString("addrzipcode");
                        Location location = null;
                        if (resultSet.getLong("locid") != 0) {
                            Long x = resultSet.getLong("locx");
                            Double y = resultSet.getDouble("locy");
                            Double z = resultSet.getDouble("locz");
                            String name = resultSet.getString("locname");
                            location = new Location(x, y, z, name);
                        }
                        address = new Address(street, zipcode, location);
                    }
                    organization.setOfficialAddress(address);
                    organizations.addLast(organization);
                }
            }
            return organizations;
        } catch (SQLException e) {
            logger.error("SELECT operation is not complete", e);
            return new ArrayDeque<>();
        }
    }


    public Long createOrganization(Organization organization, Long login) throws SQLException {
        long result = 0L;
        Address address = organization.getOfficialAddress();
        Location town = (address != null) ? address.getTown() : null;
        Coordinates coordinates = organization.getCoordinates();
        long i = 0L;
        long j = 0L;
        long k = 0L;
        if (town != null) {
            try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_LOCATIONS.query)) {
                ps.setLong(1, town.getX());
                ps.setDouble(2, town.getY());
                ps.setDouble(3, town.getZ());
                ps.setString(4, town.getName());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        i = rs.getLong(1);
                    }
                }
            }
        }
        if (address != null) {
            try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_ADDRESSES.query)) {
                ps.setString(1, address.getStreet());
                ps.setString(2, address.getZipCode());
                if (address.getTown() != null) {
                    ps.setLong(3, i);
                } else {
                    ps.setNull(3, Types.BIGINT);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        j = rs.getLong(1);
                    }
                }
            }
        }

        try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_COORDINATES.query)) {
            ps.setDouble(1, coordinates.getX());
            ps.setDouble(2, coordinates.getY());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    k = rs.getLong(1);
                }
            }
        }

        try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_ORGANIZATIONS.query)) {

            ps.setLong(1, login);
            ps.setString(2, organization.getName());
            ps.setLong(3, k);
            ps.setTimestamp(4, new Timestamp(organization.getCreationDate().getTime()));
            ps.setInt(5, organization.getEmployeesCount());
            ps.setString(6, organization.getType().name());

            if (organization.getAnnualTurnover() != null) {
                ps.setDouble(7, organization.getAnnualTurnover());
            } else {
                ps.setNull(7, Types.DOUBLE);
            }
            if (address != null) {
                ps.setLong(8, j);
            } else {
                ps.setNull(8, Types.BIGINT);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }


        }
        logger.debug("INSERT is complete");
        return result;
    }



    @Override
    public Long create(final Organization organization, Long login) {
        long result = 0L;
        try {
            result = createOrganization(organization, login);
            connection.commit();
            return result;
        } catch (SQLException e) {
            logger.error("INSERT ERROR", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {

            }
            return 0L;
        }
    }


    @Override
    public boolean update(Long id, Organization org) {
        Coordinates coordinates = org.getCoordinates();
        Address address = org.getOfficialAddress();
        Location location = (address != null)?address.getTown():null;
        Long addressId = 0L;
        Long locationId = 0L;
        Long newLocationId = 0L;
        try{
            /*
            запрос обновляет все простые поля(name, date ...) , которые содержатся в объекте Организация
            и еще и сразу обновляется объект coordinate, т к он обязан быть у любого объекта организации, все его поля не null,
            поэтому мы просто можем изменить значения в таблице coordinates, а id не менять
            также этот запрос возращает два значения: addressId и locationId
             */
            try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.UPDATE_ORGANIZATIONS.query)) {
                ps.setString(1, org.getName());// name organizations
                ps.setTimestamp(2, new Timestamp(org.getCreationDate().getTime())); // creationDate organizations
                ps.setInt(3, org.getEmployeesCount()); //employeesCount organizations
                ps.setString(4, org.getType().name()); //typeOrganizations
                if(org.getAnnualTurnover() != null) { //annualTurnover organizations
                    ps.setDouble(5, org.getAnnualTurnover());
                } else {
                    ps.setNull(5, Types.DOUBLE);
                }
                ps.setLong(6, id); //id organization
                ps.setDouble(7, coordinates.getX()); //x coordinates
                ps.setFloat(8, coordinates.getY());// y coordinates

                try(ResultSet resultSet = ps.executeQuery()) {
                    if(resultSet.next()) {
                        addressId = resultSet.getLong("officialaddress");  // id address в табличке address, если нет - 0
                        locationId = resultSet.getLong("town"); //id town в табличке locations, если нет - 0

                    }
                }

            }

            if(locationId != 0L) {
                if(location != null) {
                    //обновление локации
                    try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.UPDATE_LOCATIONS.query)) {
                        ps.setLong(1, location.getX());
                        ps.setDouble(2, location.getY());
                        ps.setDouble(3, location.getZ());
                        ps.setString(4, location.getName());
                        ps.setLong(5, locationId);
                        ps.executeUpdate();
                        //при обновлении адреса , town не должен измениться, поэтому запомним id текущего town в переменную
                        newLocationId = locationId;
                    }

                } else {
                    //удаление лоакции
                    try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_LOCATIONS.query)) {
                        ps.setLong(1, locationId);
                        ps.executeUpdate();
                    }
                }
            } else {
                if(location != null) {
                    //добавляем в таблицу локаций и сохраняем id
                    try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_LOCATIONS.query)) {
                        ps.setLong(1, location.getX());
                        ps.setDouble(2, location.getY());
                        ps.setDouble(3, location.getZ());
                        ps.setString(4, location.getName());
                        try(ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                //запишем в переменную id нашего нового location, для обновления или добавления адреса
                                newLocationId = rs.getLong("id");
                            }

                        }
                    }
                }
            }
            //если адрес был
            if (addressId != 0L) {
                //а в новом нет
                if (address == null) {
                    //удаляем адрес
                    try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_ADDRESSES_WITHOUT_RET.query)) {
                        ps.setLong(1, addressId);
                        ps.executeUpdate();
                    }
                } else {
                    //обновляем адрес и в него заносим локацию
                    try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.UPDATE_ADDRESS.query)) {
                        ps.setString(1, address.getStreet());
                        ps.setString(2, address.getZipCode());
                        if(newLocationId != 0L) {
                            ps.setLong(3, newLocationId);
                        } else {
                            ps.setNull(3, Types.BIGINT);
                        }
                        ps.setLong(4, addressId);
                        ps.executeUpdate();
                    }
                }
            //адреса не было
            } else {
                //а в новой есть
                if(address != null) {
                    //создем адрес в табличке адресов с локацией, если она была создана и тут же записываем его id в организацию
                    try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.SPECIAL_INSERT_ADDRESS.query)) {
                        ps.setString(1, address.getStreet());
                        ps.setString(2, address.getZipCode());
                        if(newLocationId != 0L) {
                            ps.setLong(3, newLocationId);
                        } else {
                            ps.setNull(3, Types.BIGINT);
                        }
                        ps.setLong(4, id);
                        ps.executeUpdate();
                    }
                }
            }
            connection.commit();
            logger.debug("UPDATE is complete");
            return true;

        } catch (SQLException e) {
            logger.error("UPDATE is not complete", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {

            }
            return false;
        }
    }

    @Override
    public Long delete(Long id) {
        long orgId = 0;//если он остался 0, то такого id нет, можно идти дальше
        try{
            orgId = deleteOrganization(id);
            connection.commit();
            logger.debug("DELETE OPERATION is completed");
            return orgId;
        } catch (SQLException e) {
            logger.error("DELETE ERROR", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {

            }
            return orgId;
        }

    }

    @Override
    public boolean deleteByKeys(List<Long> ids) {
        try {
            for(Long id: ids) {
                deleteOrganization(id);
            }
            connection.commit();
            logger.debug("DELETE_ALL OPERATION is completed");
            return true;
        } catch (SQLException e) {
            try {
                logger.error("DELETE_ALL OPERATION ERROR", e);
                connection.rollback();
            } catch (SQLException ex) {

            }
            return false;
        }


    }
    private long deleteOrganization(long id) throws SQLException {
        long orgId = 0;//если он остался 0, то такого id нет, можно идти дальше
        long coordinatesId = 0;
        long addressId = 0;
        long locationId = 0;

        try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_ORGANIZATIONS.query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) ;
                orgId = rs.getLong("id");
                coordinatesId = rs.getLong("coordinates");
                addressId = rs.getLong("officialAddress");
            }
        }
        if (orgId != 0) {//такой id есть, продолжаемудалять остальные таблицы
            try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_COORDINATES.query)) {
                ps.setLong(1, coordinatesId);
                ps.executeUpdate();
            }

            if (addressId != 0) {
                try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_ADDRESSES.query)) {
                    ps.setLong(1, addressId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) ;
                        locationId = rs.getLong("town");
                    }
                }
                if (locationId != 0) {
                    try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_LOCATIONS.query)) {
                        ps.setLong(1, locationId);
                        ps.executeUpdate();
                    }
                }
            }
        }
        return orgId;

    }

    enum SQLOrganizations {

        INSERT_ORGANIZATIONS("INSERT INTO Organizations (object_user, name, coordinates, creation_date, employees_count, type, annualTurnover, officialAddress) VALUES ((?), (?), (?), (?), (?), (?), (?), (?)) RETURNING id"),
        INSERT_LOCATIONS("INSERT INTO Locations (x, y, z, name) VALUES ((?), (?), (?), (?)) RETURNING id"),
        INSERT_ADDRESSES("INSERT INTO Address (street, zipcode, town) VALUES ((?), (?), (?)) RETURNING id"),
        INSERT_COORDINATES("INSERT INTO Coordinates (x, y) VALUES ((?), (?)) RETURNING id"),

        DELETE_ORGANIZATIONS("DELETE FROM Organizations WHERE id = (?) RETURNING id, coordinates, officialAddress"),
        DELETE_LOCATIONS("DELETE FROM Locations WHERE id = (?)"),
        DELETE_ADDRESSES("DELETE FROM Address WHERE id = (?) RETURNING town"),
        DELETE_ADDRESSES_WITHOUT_RET("DELETE FROM Address WHERE id = (?)"),
        DELETE_COORDINATES("DELETE FROM Coordinates WHERE id = (?)"),


        SELECT_ALL("select o.id, object_user, name, creation_date, employees_count, type, annualturnover, officialaddress, addrid, addrstreet, addrzipcode, locId, locx, locy, locz, locname, login, x as coordx, y as coordy from organizations as o left join (select address.id as addrid, address.street as addrstreet, address.zipcode as addrzipcode, l.id as locId,  l.x as locx, l.y as locy, l.z as locz, l.name as locname from address left join locations l on address.town = l.id) a on o.officialaddress = a.addrid left join users u on o.object_user = u.id left join coordinates c on o.coordinates = c.id"),


        UPDATE_ORGANIZATIONS("with t as (update organizations set (name, creation_date, employees_count, type, annualturnover) = ((?), (?), (?), (?), (?)) where id = (?) returning coordinates, officialaddress) update coordinates set (x, y) = ((?), (?)) where id = (select coordinates from t) returning (select officialaddress from t), (select town from address where id = (select officialaddress from t))"),
        UPDATE_LOCATIONS("update locations set(x, y, z, name) = ((?), (?), (?), (?)) where id = (?)"),
        UPDATE_ADDRESS("update address set(street, zipcode, town) = ((?), (?), (?)) where id = (?)"),
        SPECIAL_INSERT_ADDRESS("with t as (insert into address (street, zipcode, town) values ((?), (?), (?)) returning id) update organizations set officialaddress = (select id from t) where id = (?)");



        String query;


        SQLOrganizations(String query) {
            this.query = query;
        }
    }



}
