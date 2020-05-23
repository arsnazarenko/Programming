package server.business.dao;

import library.сlassModel.Address;
import library.сlassModel.Coordinates;
import library.сlassModel.Location;
import library.сlassModel.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class OrganizationDAO implements ObjectDAO<Organization, Long, String> {
    //для удобной проверки, совмещает все таблицы в одну (почти)
    //select o.id, o.name, coordinates, creation_date, employees_count, type, annualturnover, street, zipcode, town, x, y, z, b.name from Organizations o left join (select a.id, street, zipcode, town, x, y, z, name from address a left join locations l on a.town = l.id) b on o.id = b.id

    private static final Logger logger = LogManager.getLogger(OrganizationDAO.class);
    private final Connection connection;

    public OrganizationDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public long create(final Organization organization, Long login) {
        long i = 0L;
        long j = 0L;
        long k = 0L;

        long result = 0L;

        Address address = organization.getOfficialAddress();
        Location town = (address != null) ? address.getTown() : null;
        Coordinates coordinates = organization.getCoordinates();

        try {
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
                connection.commit();
                logger.debug("INSERT is complete");
                return result;

            }

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
    public Organization read(Long id, String login) {
        return null;
    }

    @Override
    public boolean update(Long id, String login) {
        return false;
    }

    @Override
    public long delete(Long id) {
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
        DELETE_COORDINATES("DELETE FROM Coordinates WHERE id = (?)");

        //UPDATE_OR


        String query;


        SQLOrganizations(String query) {
            this.query = query;
        }
    }



}
