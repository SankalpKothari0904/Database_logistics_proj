package Classes;

public class Inventory {
    private Integer slNo;
    private Integer sellerId;
    private Integer productId;
    private Integer quantity;

    public Inventory(Integer slno,Integer selId,Integer qty,Integer prodId)
    {
        this.slNo=slno;
        this.productId=prodId;
        this.quantity=qty;
        this.sellerId=selId;
    }
    public Integer getSlNo() {
        return this.slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public Integer getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
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

}
