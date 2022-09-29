package dto;

public class orderDetailsDTO {
    private String orderId;
    private String itemCode;
    private int orderQTY;
    private double discount;

    public orderDetailsDTO() {
    }

    public orderDetailsDTO(String orderId, String itemCode, int orderQTY, double discount) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setOrderQTY(orderQTY);
        this.setDiscount(discount);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQTY() {
        return orderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        this.orderQTY = orderQTY;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQTY=" + orderQTY +
                ", discount=" + discount +
                '}';
    }
}
