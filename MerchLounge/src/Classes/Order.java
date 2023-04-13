package Classes;

import java.util.*;

public class Order{
    private Integer orderId;
    private Integer customerId;
    private Date delDate;

    public Order(Integer o, Integer c, Date d){
        this.orderId=o;
        this.customerId=c;
        this.delDate=d;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getDelDate() {
        return this.delDate;
    }

    public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
}
