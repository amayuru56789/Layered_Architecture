package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public void getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select o.oid,o.date,o.customerID,od.oid,od.itemCode,od.qty,od.unitPrice from Orders o inner join OrderDetails od on o.oid=od.oid where o.oid=?;", oid);

    }
}
