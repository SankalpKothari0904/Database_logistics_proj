import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Classes.*;
import java.sql.*;

public class DAO_JDBC implements DAO_interface {
    Connection dbconn ;
    
    public DAO_JDBC(Connection con){
        this.dbconn = con;
    }
    
    @Override
    public void createOrder(Integer CustomerID, Date delDate, ArrayList<Integer> productIds, ArrayList<Integer> quantities){
        
        Statement stmt = null;
        String sql;

        Integer orderID;

        try {
            stmt = dbconn.createStatement();
            sql = "SELECT max(orderID) as maxID from order";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() == false){
                orderID = 1;
            }else{
                orderID = rs.getInt("maxID") + 1;
            }
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        PreparedStatement pstmt = null;
        sql = "INSERT INTO order VALUES (?, ?, ?)";
        
        try {
            pstmt = dbconn.prepareStatement(sql);

            pstmt.setInt(1, orderID);
            pstmt.setInt(2, CustomerID);
            pstmt.setDate(3, new java.sql.Date(delDate.getTime()));

            pstmt.executeUpdate();
            
            for (int i=0; i<productIds.size(); i++){
                createOrderDesc(orderID, productIds.get(i), quantities.get(i), "D", "Order Placed");
            }
            
            System.out.println("Order placed successfully");
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try{
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
        
    }

    @Override
    public void addShipment(Integer delID, Integer orderID, String mos){
        PreparedStatement pst = null;
        Statement smt = null;
        String sql;
        Integer slNo=0;
        
        try{
            smt = dbconn.createStatement();
            sql = "select max(slNo) as maxslno from shipments";
            ResultSet rs = smt.executeQuery(sql);
            
            if(rs.next()==false){
                slNo = 1;
            }else{
                slNo = rs.getInt("maxslno") + 1;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "insert into shipments values(?,?,?,?)";
        
        try{
            pst = dbconn.prepareStatement(sql);
            
            pst.setInt(1,slNo);
            pst.setInt(2,delID);
            pst.setInt(3,orderID);
            pst.setString(4,mos);

            pst.executeUpdate();
            System.out.println("Database updated successfully");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        try{
            if(pst!=null){
                pst.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createOrderDesc(Integer OrderID,Integer ProductId,Integer Quantity,String type,String status)
    {
        PreparedStatement pst = null;
        String sql;
        Statement stmt = null;
        Integer slno=0;
        sql = "select max(slNo) from orderDesc;";
        try{
            stmt = dbconn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);  
            slno = rs.getInt("slNo");

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        try{
            sql = "insert into orderDesc values(?,?,?,?,?,?);";
            pst = dbconn.prepareStatement(sql);
            pst.setInt(1, slno+1);
            pst.setInt(2,ProductId);
            pst.setInt(3, OrderID);
            pst.setInt(4,Quantity);
            pst.setString(5,type);
            pst.setString(6,status);
            pst.executeUpdate();
            System.out.println("The databases has been updated successfully\n");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        try{
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		} 
    }


    @Override
    public void keepTrackOrder(Integer CustomerID){
        String sql = "";
        Statement stmt = null;
        try{
            stmt = dbconn.createStatement();
            sql = "select or.orderID, or.delDate,ord.productID,ord.quantity,p.productName from"+
                "order as or, orderDesc as ord, product as p where or.orderID = ord.orderID and"+
                "ord.productID = p.productID;";
            ResultSet rt = stmt.executeQuery(sql);
            System.out.println("orderID "+"delDate "+"productID "+"quantity "+"productName");
            while(rt.next())
            {
                Integer OrderID = rt.getInt("orderID");
                Date date = rt.getDate("delDate");
                Integer ProductID = rt.getInt("productID");
                Integer quantity = rt.getInt("quantity");
                String name = rt.getString("productName");
                System.out.println(OrderID+" "+date+" "+ProductID+" "+quantity+" "+name);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void sellerUpdateOrderStatus(Integer SellerId,Integer OrderId,Integer prodID,String status)
    {
        String sql1;
        sql1 = "select sellerID from shipments where sellerID="+Integer.toString(SellerId)+"and orderID="+Integer.toString(OrderId);
        Statement st = null;
        PreparedStatement pst = null;
        try{
            st = dbconn.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            if (rs.next() == false){
                return;
                //
            }
            else{
                String sql = "update orderDesc set status ="+status+"where productID="+prodID+"and orderID="+OrderId;
                pst = dbconn.prepareStatement(sql);
                pst.executeUpdate();
                if(status=="Packed")
                {
                    Scanner input = new Scanner(System.in);
                    System.out.println("Enter the mode of shipment");
                    String mos = input.nextLine();
                    System.out.println("Enter the delivery agent ID");
                    Integer delid = input.nextInt();
                    addShipment(delid, OrderId,mos);
                    input.close();
                }
                System.out.println("Successfully Updated");
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        try{
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
        }
    }

    @Override
    public void showInventory(Integer sellerID){
        Statement stmt = null;
        
        try {
            stmt = dbconn.createStatement();
            String sql;
            sql = "SELECT in.productID, p.productName, in.quantity from inventory in, product p where in.productID = p.productID and in.sellerID = " + Integer.toString(sellerID);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("productID productName quantity");
            while (rs.next()){
                Integer productID = rs.getInt("productID");
                String name = rs.getString("productName");
                Integer qty = rs.getInt("quantity");
                System.out.println(Integer.toString(productID)+" "+name+" "+Integer.toString(qty));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void seeShipments(Integer delID){
        Statement smt = null;
        String sql = "select o.orderID as orderID, c.customerName as name, c.address as address, c.phNo as phoneNumber from shipments s, order o, customer c where s.orderID = o.orderID and o.customerID = c.customerID and s.delID = "+Integer.toString(delID);

        try{
            smt = dbconn.createStatement();
            ResultSet rs = smt.executeQuery(sql);
            System.out.println("orderID " + "Name" + " address " + "phoneNumber");
            while(rs.next()){
                Integer orderid = rs.getInt("orderID");
                String custname = rs.getString("name");
                String address = rs.getString("address");
                String phno = rs.getString("phoneNumber");
                System.out.println(Integer.toString(orderid)+" "+custname+" "+address+" "+phno);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteShipment(Integer delID, Integer orderID){
        Statement smt = null;
        String sql = "select * from shipments where delID = " + Integer.toString(delID) + " and orderID = " + Integer.toString(orderID);

        try{
            smt = dbconn.createStatement();
            ResultSet rs = smt.executeQuery(sql);

            if(rs.next()==false){
                System.out.println("Error - No matching shipment");
                return;
            }

            PreparedStatement pst = null;
            sql = "delete from shipments where delID = ?  and orderID = ?";
            
            pst.setInt(1, delID);
            pst.setInt(2, orderID);
            pst.executeUpdate();
            System.out.println("Shipment records updated");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delUpdateOrderStatus(Integer delId,Integer OrderID,String status){
        Statement stmt = null;
        PreparedStatement pstmt = null;
        
        try {
            stmt = dbconn.createStatement();
            String sql;
            sql = "SELECT delID FROM shipments where delID = "+Integer.toString(delId)+" and orderID = "+Integer.toString(OrderID);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()){
                System.out.println("Error - no matching shipment");
                return;
            }

            sql = "UPDATE order SET status = ? where orderID = ?";

            pstmt.setString(1, status);
            pstmt.setInt(2, OrderID);
            pstmt.executeUpdate();

            System.out.println("Status updated");
            if (status == "Delivered"){
                deleteShipment(delId, OrderID);
            }
                
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try{
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
        }
        
    }

    public void delUpdateOrderdelDate(Integer delId,Integer OrderID,Date dt) {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        
        try {
            stmt = dbconn.createStatement();
            String sql;
            sql = "SELECT delID FROM shipments where delID = "+Integer.toString(delId)+" and orderID = "+Integer.toString(OrderID);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()){
                System.out.println("Error - no matching shipment");
                return;
            }

            sql = "UPDATE order SET delDate = ? where orderID = ?";

            pstmt.setDate(1, new java.sql.Date(dt.getTime()));
            pstmt.setInt(2, OrderID);
            pstmt.executeUpdate();

            System.out.println("Status updated");

                
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try{
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
        }
    }

}
