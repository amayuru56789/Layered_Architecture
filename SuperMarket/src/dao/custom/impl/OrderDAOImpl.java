package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.OrderDAO;
import db.DbConnection;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.Order;
import views.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES (?,?,?)",dto.getOrderId(),dto.getOrderDate(),dto.getCustId());
    }

    @Override
    public boolean update(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDTO search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` WHERE oid=?", oid);
        rst.next();
        return new OrderDTO(rst.getString(1), rst.getString(2), rst.getString(3));
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Orders` WHERE oid=?", oid);
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1;");
        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;
            if (tempId<9){
                return "O-00"+tempId;
            }else if (tempId<99){
                return "O-0"+tempId;
            }else {
                return "O-"+tempId;
            }
        }
        return "O-001";
        //return rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString(1).replace("OD", "")) + 1)): "OD001";
    }

    @Override
    public boolean placeOrder(OrderDTO order) {
        /*Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            stm.setObject(1,order.getOrderId());
            stm.setObject(2,order.getOrderDate());
            stm.setObject(3,order.getCustId());

            if (stm.executeUpdate()>0){
                if (saveOrderDetail(order.getOrderId(), order.getOrderDate())){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }*/
        try {
            Connection con = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            pstm.setObject(1,order.getOrderId());
            pstm.setObject(2,order.getOrderDate());
            pstm.setObject(3,order.getCustId());

            return pstm.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
     return false;
    }
/*    private boolean saveOrderDetail(OrderDTO order, ItemDTO item, CartTm cart) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO `Order detail` VALUES (?,?,?,?)");
        pstm.setObject(1,order.getOrderId());
        pstm.setObject(2,item.getItemCode());
        pstm.setObject(3,cart.getQty());
        pstm.setObject(4,cart.getDiscount());

        return pstm.executeUpdate() > 0;
    }*/
}
