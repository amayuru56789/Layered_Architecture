package entity;

public class Order {
    private String orderId;
    private String orderDate;
    private String custId;

    public Order() {
    }

    public Order(String orderId, String orderDate, String custId) {
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
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", custId='" + custId + '\'' +
                '}';
    }
}
