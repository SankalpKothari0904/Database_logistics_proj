package Classes;

public class Product{
    private Integer productID;
    private String productName;
    private Double price;

    public Product(Integer id, String name, Double price){
        this.productID = id;
        this.productName = name;
        this.price = price;
    }

    public Integer getProductID() {
        return this.productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

  
    
}