package dao.custom;

import dao.CrudDAO;
import dto.OrderDTO;
import entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<OrderDTO, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDTO order);
}
