package Classes;
public class OrderDesc {
    private Integer slNo;
    private Integer productId;
    private Integer quantity;
    private Character type;
    private Integer orderId;
    private String status;

    public OrderDesc(Integer slno, Integer productID, Integer orderID, Integer quantity, Character type, String status) {
        this.slNo = slno;
        this.productId = productID;
        this.orderId = orderID;
        this.quantity = quantity;
        this.type = type;
        this.status = status;
    }

    public Integer getSlNo() {
        return this.slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Character getType() {
        return this.type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
