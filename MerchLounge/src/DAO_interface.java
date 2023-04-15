import java.util.*;

public interface DAO_interface {

    public void createOrder(Integer CustomerID, Date delDate, ArrayList<Integer> productIds, ArrayList<Integer> quantities); 
    // for creating an order ID for the project we can acces the database and find the max orderID and then increment it by one everytime and pass
    // as an argument. 
    // the delivery date is the current system date + 5 days

    public void keepTrackOrder(Integer CustomerID);
    //performs the join on order, orderDesc and product
    public void sellerUpdateOrderStatus(Integer SellerId,Integer OrderId,Integer prodID,String status);
    // take a boolean variable called shipment. If the status string is packed, then we add entry in the shipments table, when Delivered, remove

    public void createOrderDesc(Integer OrderID,Integer ProductID,Integer Quantity,String type,String status);
    //called by createOrder to create entries 

    public boolean updateInventory(Integer ProductId,String type,Integer quantity);
    //used by Seller to update his inventory
    public void addShipment(Integer delID, Integer orderID, String mos);
    
    public void deleteShipment(Integer delID, Integer orderID);
    public void showInventory(Integer SellerId);
    public void seeShipments(Integer delId);
    public void delUpdateOrderStatus(Integer delId,Integer OrderID,String status);
    public void delUpdateOrderdelDate(Integer delId,Integer OrderID,Date dt);   
    public void orderReturn(Integer CustomerID, Integer productID,Integer OrderID); 
    public void sellerReturnUpdate(Integer sellerID,Integer OrderID,Integer productID,Integer quantity);
    public void sellerPendingOrders(Integer sellerID);
}