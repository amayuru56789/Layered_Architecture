package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.CustomerDAO;
import dao.custom.DAOFactory;
import db.DbConnection;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return false;
        //return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?),"dto.getId(),dto.getTitle(),dto.getName(),dto.getAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode());
    }

    @Override
    public boolean update(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=? WHERE custId=?", dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode(), dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE custId=?", id);
    }

    @Override
    public CustomerDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer", id);
        rst.next();
        return new CustomerDTO(id, rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7));
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new CustomerDTO(rst.getString(1), rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7)));
        }
        return allCustomers;
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT custId FROM Customer WHERE id=?", id).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT custId FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }

    }

    /*@Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Customer");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            allCustomers.add(new CustomerDTO(rst.getString(1),rst.getString(2),
                    rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7)));

        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)");
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getTitle());
        pstm.setString(3,dto.getName());
        pstm.setString(4,dto.getAddress());
        pstm.setString(5,dto.getCity());
        pstm.setString(6,dto.getProvince());
        pstm.setString(7,dto.getPostalCode());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Customer SET custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode WHERE custId=?");
        pstm.setString(1,dto.getTitle());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4,dto.getCity());
        pstm.setString(5,dto.getProvince());
        pstm.setString(6,dto.getPostalCode());
        pstm.setString(7, dto.getId());
        return pstm.executeUpdate() > 0;

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Customer WHERE custId=?");
        pstm.setString(1,id);
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT custId FROM Customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("SELECT custId FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        }else {
            return "C001";
        }

    }*/
}
