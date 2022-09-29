package dto;

import java.time.LocalDate;

public class OrderDTO {
    private String orderId;
    private String orderDate;
    private String custId;

    public OrderDTO(String orderId, LocalDate orderDate, String customerId) {
    }

    public OrderDTO(String orderId, String orderDate, String custId) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustId(custId);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", custId='" + custId + '\'' +
                '}';
    }
}
