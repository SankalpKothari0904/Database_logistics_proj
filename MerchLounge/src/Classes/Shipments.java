package Classes;

public class Shipments {
    private Integer slNo;
    private Integer delId;
    private Integer orderId;
    private String mode_of_shipments;

    public Shipments(Integer slno,Integer delid,Integer orid, String mos )
    {
        this.slNo=slno;
        this.delId=delid;
        this.orderId=orid;
        this.mode_of_shipments=mos;
    }
    public Integer getSlNo() {
        return this.slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public Integer getDelId() {
        return this.delId;
    }

    public void setDelId(Integer delId) {
        this.delId = delId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMode_of_shipments() {
        return this.mode_of_shipments;
    }

    public void setMode_of_shipments(String mode_of_shipments) {
        this.mode_of_shipments = mode_of_shipments;
    }

}
