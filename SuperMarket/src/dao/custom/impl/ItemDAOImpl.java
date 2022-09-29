package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.ItemDAO;
import db.DbConnection;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)",dto.getItemCode(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?",dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getItemCode());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?",code);
    }

    @Override
    public ItemDTO search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", code);
        rst.next();
        return new ItemDTO(code, rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5));
    }

    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()){
            allItems.add(new ItemDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4),rst.getInt(5)));
        }
        return allItems;
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT itemCode FROM Item WHERE code=?", code).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;");
      /*  if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }*/
        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;
            if (tempId<9){
                return "I-00"+tempId;
            }else if (tempId<99){
                return "I-0"+tempId;
            }else {
                return "I-"+tempId;
            }
        }
        return "I-001";
    }

    @Override
    public List<Double> getAllUnitPrice() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        List<Double> price = new ArrayList<>();
        while (rst.next()){
            price.add(rst.getDouble(4));
        }
        return price;
    }
/*    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
       *//* Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Item");
        ResultSet rst = pstm.executeQuery();*//*
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()){
            allItems.add(new ItemDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4),rst.getInt(5)));
        }
        return allItems;

    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        *//*Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?)");
        pstm.setString(1,dto.getItemCode());
        pstm.setString(2,dto.getDescription());
        pstm.setString(3,dto.getPackSize());
        pstm.setDouble(4,dto.getUnitPrice());
        pstm.setInt(5,dto.getQtyOnHand());
        return pstm.executeUpdate() > 0;*//*
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)",dto.getItemCode(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand());

    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
    *//*    Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?");
        pstm.setString(1, dto.getDescription());
        pstm.setString(2, dto.getPackSize());
        pstm.setDouble(3,dto.getUnitPrice());
        pstm.setInt(4,dto.getQtyOnHand());
        pstm.setString(5,dto.getItemCode());
        return pstm.executeUpdate() > 0;*//*
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?",dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getItemCode());
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
       *//* Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Item WHERE itemCode=?");
        pstm.setString(1,code);
        return pstm.executeUpdate() > 0;*//*
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?",code);

    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
       *//* Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT itemCode FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();*//*
        return CrudUtil.executeQuery("SELECT itemCode FROM Item WHERE code=?", code).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
       *//* Connection con = DbConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("SELECT itemCode FROM Item ORDER BY code DESC LIMIT 1;");*//*
        ResultSet rst = CrudUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }*/
 /*
    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", code);
        rst.next();
        return new Item(code, rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4));
    }*/

}
