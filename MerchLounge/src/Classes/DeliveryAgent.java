package Classes;

public class DeliveryAgent {
    private Integer delId;
    private String phNo;
    public DeliveryAgent(Integer delid,String no)
    {
        this.delId=delid;
        this.phNo=no;
    }
    public Integer getDelId() {
        return this.delId;
    }

    public void setDelId(Integer delId) {
        this.delId = delId;
    }

    public String getPhNo() {
        return this.phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

}
