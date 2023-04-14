package Classes;

public class Customer {
    private Integer customerID;
    private String customerName;
    private String phNo;
	private String Address;

	public String getAddress() {
		return this.Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public Customer(Integer cusId,String cusname,String phno,String Address)
	{
		this.customerID=cusId;
		this.customerName=cusname;
		this.phNo=phno;
		this.Address=Address;
	}
	public int getCustomerID() {
		return this.customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhNo() {
		return this.phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}
    
}
