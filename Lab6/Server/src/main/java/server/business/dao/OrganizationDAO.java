package server.business.dao;

import library.сlassModel.Address;
import library.сlassModel.Coordinates;
import library.сlassModel.Location;
import library.сlassModel.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class OrganizationDAO implements DAO<Organization, Long> {

    private static final Logger logger = LogManager.getLogger(OrganizationDAO.class);
    private final Connection connection;

    public OrganizationDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public long create(final Organization organization) {
        Long i = 0L;
        Long j = 0L;
        Long k = 0L;

        long result = 0L;

        Address address = organization.getOfficialAddress();
        Location town = address.getTown();
        Coordinates coordinates = organization.getCoordinates();

        try {
            if (town != null) {
                try (PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_LOCATIONS.query)) {
                    ps.setLong(1, town.getX());
                    ps.setDouble(2, town.getY());
                    ps.setDouble(3, town.getZ());
                    ps.setString(4, town.getName());
                    try(ResultSet rs = ps.executeQuery()) {
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

            try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_COORDINATES.query)) {
                ps.setDouble(1, coordinates.getX());
                ps.setDouble(2, coordinates.getY());
                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        k = rs.getLong(1);
                    }
                }
            }

            try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.INSERT_ORGANIZATIONS.query)) {
                ps.setString(1, organization.getName());
                ps.setLong(2, k);
                ps.setTimestamp(3, new Timestamp(organization.getCreationDate().getTime()));
                ps.setInt(4, organization.getEmployeesCount());
                ps.setString(5, organization.getType().name());
                if (organization.getAnnualTurnover()!= null) {
                    ps.setDouble(6, organization.getAnnualTurnover());
                } else {
                    ps.setNull(6, Types.DOUBLE);
                }
                if (address != null) {
                    ps.setLong(7, j);
                } else {
                    ps.setNull(7, Types.BIGINT);
                }

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result = rs.getLong(1);
                    }
                }
                connection.commit();
                logger.debug("INSERT is complete");
                return result;

            }

        } catch (SQLException e) {
            logger.error("INSERT ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {

            }
            return 0L;
        }
    }

    @Override
    public Organization read(Long id) {
        return null;
    }

    @Override
    public boolean update(Long id) {
        return false;
    }

    @Override
    public long delete(Long id) {
        long result = 0;
        try(PreparedStatement ps = connection.prepareStatement(SQLOrganizations.DELETE_ORGANIZATIONS.query)) {
            ps.setLong(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    result = rs.getLong(1);
                }
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            logger.error("DELETE ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {

            }
            return 0L;
        }

    }

    @Override
    public boolean deleteByKeys(Long[] ids) {
        return false;
    }

    enum SQLOrganizations {

        INSERT_ORGANIZATIONS("INSERT INTO Organizations (name, coordinates, creation_date, employees_count, type, annualTurnover, officialAddress) VALUES ((?), (?), (?), (?), (?), (?), (?)) RETURNING id"),
        INSERT_LOCATIONS("INSERT INTO Locations (x, y, z, name) VALUES ((?), (?), (?), (?)) RETURNING id"),
        INSERT_ADDRESSES("INSERT INTO Address (street, zipcode, town) VALUES ((?), (?), (?)) RETURNING id"),
        INSERT_COORDINATES("INSERT INTO Coordinates (x, y) VALUES ((?), (?)) RETURNING id"),

        DELETE_ORGANIZATIONS("DELETE FROM Organizations WHERE id = (?)");




        String query;



        SQLOrganizations(String query) {
            this.query = query;
        }
    }
}
