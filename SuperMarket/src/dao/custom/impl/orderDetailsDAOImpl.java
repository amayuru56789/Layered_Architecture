package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.orderDetailsDAO;
import dto.ItemDTO;
import dto.orderDetailsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderDetailsDAOImpl implements orderDetailsDAO {
    @Override
    public boolean add(orderDetailsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order details` VALUES (?,?,?,?)",dto.getOrderId(),dto.getItemCode(),dto.getOrderQTY(),dto.getDiscount());

}

    @Override
    public boolean update(orderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public orderDetailsDTO search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<orderDetailsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<orderDetailsDTO> allDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order detail`");
        while (rst.next()){
            allDetails.add(new orderDetailsDTO(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getDouble(4)));
        }
        return allDetails;
    }
}
