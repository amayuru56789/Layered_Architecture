package dao.custom;

import java.sql.SQLException;

public interface QueryDAO {
    void getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException;
}
