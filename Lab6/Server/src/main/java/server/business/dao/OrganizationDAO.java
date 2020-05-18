package server.business.dao;

import library.сlassModel.Organization;

import java.sql.Connection;

public class OrganizationDAO implements DAO<Organization, Long> {

    private final Connection connection;

    public OrganizationDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(final Organization organization) {
        return false;
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
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean deleteByKeys(Long[] ids) {
        return false;
    }
}
