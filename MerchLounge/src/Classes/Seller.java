package Classes;

public class Seller{
    private Integer sellerID;
    String sellerName;
    String address;
    String phno;

    public Seller(Integer id, String name, String address, String phone){
        this.sellerID = id;
        this.sellerName = name;
        this.address = address;
        this.phno = phone;
    }

    public Integer getSellerID(){
        return this.sellerID;
    }

    public String getSellerName(){
        return this.sellerName;
    }

    public String getAddress(){
        return this.address;
    }

    public String getPhone(){
        return this.phno;
    }

    public void setName(String name){
        this.sellerName = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setPhone(String phone){
        this.phno = phone;
    }
}