import Classes.*;
import java.util.*;

public interface DAO_interface {
    public void createOrder(Integer CustomerID, Date delDate, ArrayList<Integer> productIds, ArrayList<Integer> quantities);
    // for creating an order ID for the project we can acces the database and find the max orderID and then increment it by one everytime and pas
    // as an argument. 
    // the delivery date is the current system date + 5 days
        
    public ArrayList<Order> keepTrackOrder(Integer CustomerID);
    //performs the join on order, orderDesc and product
    public void sellerUpdateOrderStatus(Integer SellerId,Integer OrderId,String status);
    // take a boolean variable called shipment. If the status string is packed, then we add entry in the shipments table, when Delivered, remove

    public void createOrderDesc(Integer OrderID,Integer ProductID,Integer Quantity,char type,String status);
    //called by createOrder to create entries 

    public void updateInventory(Integer ProductId,char type,Integer quantity);
    //used by Seller to update his inventory
    public void addShipment(Integer delID, Integer orderID);
    
    public void deleteShipment(Integer delID, Integer orderID);
    public void showInventory(Integer SellerId);
    public void seeShipments(Integer delId);
    public void delUpdateOrderStatus(Integer delId,Integer OrderID,String status);
    
}
