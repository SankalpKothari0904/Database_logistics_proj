import java.util.*;

import com.mysql.jdbc.PreparedStatement;

import java.text.SimpleDateFormat;
import java.time.*;

public class DAO_Driver {
    public static DAO_factory dao_factory;

    public static void placeOrder(Integer custId, ArrayList<Integer> productIds, ArrayList<Integer> quantities){
        LocalDate lt = LocalDate.now();
        Date date = Date.from(lt.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            dao_factory.activateConnection();
            DAO_interface dao = dao_factory.getDAO();

            dao.createOrder(custId, date, productIds, quantities);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT );

        }catch (Exception e){
            dao_factory.deactivateConnection( DAO_factory.TXN_STATUS.ROLLBACK );
            e.printStackTrace();
        }
    }

    public static void cusCheckOrder(Integer CustomerID)
    {
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.keepTrackOrder(CustomerID);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }
        catch (Exception e)
        {
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }

    public static void seePendingOrders(Integer sellerID){
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.sellerPendingOrders(sellerID);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }catch(Exception e){
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }

    public static void sellerUpdateOrderStatus(Integer SellerId,Integer OrderId,Integer prodID,String status){
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.sellerUpdateOrderStatus(SellerId, OrderId, prodID, status);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT); 
        }catch (Exception e){
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }
    public static void delAgentUpdateStatus(Integer delid,Integer OrderId,String status)
    {
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.delUpdateOrderStatus(delid, OrderId, status);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }catch (Exception e)
        {
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }      
    }
    
    public static void delAgentSeesOrders(Integer delid){
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.seeShipments(delid);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }catch (Exception e)
        {
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }

    public static void orderDelivered(Integer delId,Integer OrderID){
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.delUpdateOrderStatus(delId, OrderID, "Delivered");
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }catch (Exception e)
        {
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }
    
    public static void cusOrderReturn(Integer CustomerID,Integer ProductID,Integer OrderID)
    {
        try{
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.orderReturn(CustomerID, ProductID, OrderID);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }
        catch (Exception e)
        {
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }
    
    public static void sellerSeesInventory(Integer sellerID){
        try {
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.showInventory(sellerID);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }catch (Exception e){
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }

    public static void updateDelDate(Integer delID, Integer orderID, Date date){
        try {
            dao_factory.activateConnection();
            DAO_interface daoi = dao_factory.getDAO();
            daoi.delUpdateOrderdelDate(delID, orderID, date);
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.COMMIT);
        }catch (Exception e){
            dao_factory.deactivateConnection(DAO_factory.TXN_STATUS.ROLLBACK);
            e.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        try{
            dao_factory = new DAO_factory();
            System.out.println("Welcome to Merch Lounge\n");
            Scanner sc = new Scanner(System.in);
            System.out.println("The menu will be shown below: ");
            System.out.println("Enter 1 for customer, 2 for seller, 3 for delivery agent, 0 to exit: ");
            Integer menu = sc.nextInt();
            Integer submenu=-1;
            while(true)
            {
                if(menu==0)
                {
                    break;
                }
                else if(menu==1)
                {
                    System.out.println("Enter 1 for viewing you orders\nEnter 2 for placing order\nEnter 3 for returning the product");
                    submenu = sc.nextInt();
                    if(submenu==1)
                    {
                        System.out.println("Please enter your ID");
                        submenu = sc.nextInt();
                        cusCheckOrder(submenu); 
                    }
                    else if(submenu==2)
                    {
                        System.out.println("Please enter your ID");
                        submenu = sc.nextInt();
                        ArrayList<Integer> prodIDs = new ArrayList<>();
                        ArrayList<Integer>qtys = new ArrayList<>();
                        System.out.println("Please enter the number of products you wish to order");
                        Integer prods = sc.nextInt();
                        System.out.println("For"+prods+" number of times enter the prodID and the qty that you wish to order");
                        Integer n1=-1;
                        Integer n2=-1;
                        for(int i=0;i<prods;i++)
                        {
                            n1 = sc.nextInt();
                            n2 = sc.nextInt();
                            prodIDs.add(n1);
                            qtys.add(n2);
                        }
                        placeOrder(submenu, prodIDs, qtys);
                    }
                    else if(submenu==3)
                    {
                        System.out.println("Please enter your ID");
                        submenu = sc.nextInt();
                        Integer n1=-1;
                        Integer n2=-1;
                        System.out.println("Enter the productId and the orderID of the order that you wish to return");
                        n1=sc.nextInt();
                        n2=sc.nextInt();
                        cusOrderReturn(submenu, n1, n2);
                    }else{
                        System.out.println("Invalid option");
                        continue;
                    }
                }
                else if(menu==2)
                {
                    System.out.println("Enter 1 to update order staus\nEnter 2 to see pending orders\nEnter 3 to see your inventory");
                    submenu = sc.nextInt();
                    
                    if(submenu==1)
                    {
                        System.out.println("Enter your SellerID");
                        Integer sellid=-1;
                        sellid = sc.nextInt();
                        System.out.println("Enter the orderID for which you wish to update the status");
                        Integer orderID = sc.nextInt();
                        System.out.println("Enter the prodID for which you wish to update the status");
                        Integer prodID = sc.nextInt();
                        System.out.println("Enter the status");
                        String st = sc.next();
                        sellerUpdateOrderStatus(sellid, orderID, prodID, st);
                    }
                    else if(submenu==2)
                    {   
                        System.out.println("Enter your SellerID");
                        Integer sellid=-1;
                        sellid = sc.nextInt();

                        seePendingOrders(sellid);
                    }
                    else if(submenu==3)
                    {
                        System.out.println("Enter your SellerID");
                        Integer sellid=-1;
                        sellid = sc.nextInt();
                        sellerSeesInventory(sellid);
                    }
                    else{
                        System.out.println("Invalid option");
                        continue;
                    }
                }
                else if(menu == 3)
                {
                    System.out.println("Enter 1 to update order staus\nEnter 2 to see pending shipments\nEnter 3 to deliver the order\nEnter 4 to update the delivery date. ");
                    submenu = sc.nextInt();
                    
                    if(submenu==1)
                    {
                        System.out.println("Enter your deliveryID");
                        Integer delid = -1;
                        delid = sc.nextInt();
                        System.out.println("Enter the orderID to update the status of");
                        Integer orderID = sc.nextInt();
                        System.out.println("Enter status");
                        String status = sc.next();
                        delAgentUpdateStatus(delid, orderID, status);
                    }
                    else if(submenu==2)
                    {
                        System.out.println("Enter your deliveryID");
                        Integer delid = -1;
                        delid = sc.nextInt();
                        delAgentSeesOrders(delid);
                    }
                    else if(submenu==3)
                    {
                        System.out.println("Enter your deliveryID");
                        Integer delid = -1;
                        delid = sc.nextInt();
                        System.out.println("Enter the orderID that has to be delivered");
                        Integer orderID = sc.nextInt();
                        orderDelivered(delid,orderID);
                    }
                    else if(submenu==4)
                    {
                        System.out.println("Enter delID");
                        Integer did = sc.nextInt();
                        System.out.println("Please enter orderID");
                        Integer oid = sc.nextInt();
                        System.out.println("Enter the delivery date in YYYY-MM-DD format");
                        String date = sc.next();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date2=null;
                        try 
                        {
                            date2 = dateFormat.parse(date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateDelDate(did,oid,date2);
                        
                    }
                    else{
                        System.out.println("Invalid option");
                        continue;
                    }
                }
                else{
                    System.out.println("Invalid option, try again");
                }
                System.out.println("\nEnter 1 for customer, 2 for seller, 3 for delivery agent, 0 to exit: ");
                menu=sc.nextInt();
            }
            sc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}