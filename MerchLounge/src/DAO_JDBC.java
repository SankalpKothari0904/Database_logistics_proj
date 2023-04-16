import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.sql.*;

public class DAO_JDBC implements DAO_interface {
    private Connection dbconn ;
    
    public DAO_JDBC(Connection con){
        this.dbconn = con;
    }
    @Override
    public void createOrder(Integer CustomerID, Date delDate, ArrayList<Integer> productIds, ArrayList<Integer> quantities){
        Statement stmt = null;
        String sql;

        Integer orderID=-1;

        try {
            stmt = dbconn.createStatement();
            sql = "SELECT customerID from customer where customerID = "+CustomerID;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() == false){
                System.out.println("Invalid customer id");
                return ;
            }
            else{
                sql = "SELECT max(orderID) as maxID from orders";
                rs = stmt.executeQuery(sql);
                if (rs.next() == false){
                    orderID = 1;
                }else{
                    orderID = rs.getInt("maxID") + 1;
                }
            }
            
        } 
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        PreparedStatement pstmt = null;
        sql = "INSERT INTO orders VALUES (?, ?, ?)";
        
        try {
            pstmt = dbconn.prepareStatement(sql);

            pstmt.setInt(1, orderID);
            pstmt.setInt(2, CustomerID);
            pstmt.setDate(3, new java.sql.Date(delDate.getTime()));
            pstmt.executeUpdate();
            boolean flag = false;
            for (int i=0; i<productIds.size(); i++){
                if (updateInventory(productIds.get(i), "D", quantities.get(i)) == true){
                    if (createOrderDesc(orderID, productIds.get(i), quantities.get(i), "D", "Placed") == true){
                        flag = true;
                    }
                }
                
            }
            if (flag){
                System.out.println("Order placed successfully");
            }else{
                sql = "DELETE FROM orders where orderID = ?";
                pstmt = dbconn.prepareStatement(sql);
                pstmt.setInt(1, orderID);
                pstmt.executeUpdate();
                System.out.println("Order could not be placed");
            }
            
            
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
    public boolean createOrderDesc(Integer OrderID,Integer ProductId,Integer Quantity,String type,String status)
    {
        PreparedStatement pst = null;
        String sql;
        Statement stmt = null;
        Integer slNo=0;
        sql = "select max(slNo) as maxslno from orderDesc;";
        try{
            stmt = dbconn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);  

            if(rs.next()==false){
                slNo = 1;
            }else{
                slNo = rs.getInt("maxslno") + 1;
            }

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        
        try{
            sql = "insert into orderDesc values(?,?,?,?,?,?);";
            pst = dbconn.prepareStatement(sql);
            pst.setInt(1, slNo);
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
            return false;
        }
        
        try{
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
            return false;
 		} 
        return true;
    }

    @Override
    public void keepTrackOrder(Integer CustomerID){
        String sql = "";
        Statement stmt = null;
        try{
            stmt = dbconn.createStatement();
            sql = "SELECT customerID from customer where customerID = "+CustomerID;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() == false){
                System.out.println("Invalid customer id");
                return ;
            }
            sql = "select o.orderID, o.delDate,ord.productID,ord.quantity,p.productName from "+
                "orders o, orderDesc ord, product p where o.orderID = ord.orderID and "+
                "ord.productID = p.productID and o.customerID="+CustomerID;
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
            System.out.println(e.getMessage());
        }
    }

    //we have to change this fucntion
    @Override
    public void sellerUpdateOrderStatus(Integer SellerId,Integer OrderId,Integer prodID,String status){
        String sql1;
        Statement st = null;
        sql1 = "select sellerID from inventory where sellerID="+SellerId+" and productID="+prodID;
        try{
            st = dbconn.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            if (rs.next() == false){
                System.out.println("Invalid sellerID or productID");
                return;
            }
        }
        catch (Exception e)
        {   
            System.out.println(e.getMessage());
        }

        sql1 = "select productID from orderDesc where productID="+prodID+" and orderID="+OrderId;
        try{
            st = dbconn.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            if (rs.next() == false){
                System.out.println("Invalid orderID or productID");
                return;
            }
        }
        catch (Exception e)
        {   
            System.out.println(e.getMessage());
        }
        
        PreparedStatement pst = null;
        try{
            st = dbconn.createStatement();
            ResultSet rs;
            // String sql = "update orderDesc set status ="+status+"where productID="+prodID+"and orderID="+OrderId;
            String sql = "update orderDesc set status = ? where productID= ? and orderID= ? ";
            pst = dbconn.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, prodID);
            pst.setInt(3, OrderId);
            pst.executeUpdate();
            sql = "select delID from shipments where orderID="+OrderId;
            rs = st.executeQuery(sql);

            Integer delid = -1;
            while(rs.next())
            {
                delid = rs.getInt("delID");
            }
            if(delid==-1)
            {
                Scanner input = new Scanner(System.in);

                while (true){
                    
                    System.out.println("Enter the delivery agent id: ");
                    delid = input.nextInt();
                    String sql2 = "select delID from deliveryAgent where delID = "+delid;
                    Statement s;
                    s = dbconn.createStatement();
                    ResultSet rt = s.executeQuery(sql2);
                    if (rt.next()){
                        break;
                    }else{
                        System.out.println("Invalid delivery ID, try again");
                    }
                }
                
                System.out.println("Enter mode of shipment: ");
                String mode = input.nextLine();
                mode = input.nextLine();
                addShipment(delid, OrderId, mode);
                System.out.println("Database updated successfully");
                // input.close();
            }
            else
            {
                System.out.println("Database updated successfully");
            }
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
    public void showInventory(Integer sellerID){
        Statement stmt = null;
        
        try {
            stmt = dbconn.createStatement();
            String sql;
            sql = "SELECT i.productID, p.productName, i.quantity from inventory i, product p where i.productID = p.productID and i.sellerID = " +sellerID;
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()==false){
                System.out.println("Invalid sellerID");
                return;
            }
            System.out.println("productID productName quantity");
            do{
                Integer productID = rs.getInt("productID");
                String name = rs.getString("productName");
                Integer qty = rs.getInt("quantity");
                System.out.println(Integer.toString(productID)+" "+name+" "+Integer.toString(qty));
            }while (rs.next());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void seeShipments(Integer delID){
        Statement smt = null;
        String sql = "select delID from deliveryAgent where delID = "+delID;
        try{
            smt = dbconn.createStatement();
            ResultSet rs = smt.executeQuery(sql);
            if (rs.next() == false){
                System.out.println("Invalid delivery ID");
                return;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        sql = "select o.orderID as orderID, c.customerName as name, c.address as address, c.phNo as phoneNumber from shipments s, orders o, customer c where s.orderID = o.orderID and o.customerID = c.customerID and s.delID = "+Integer.toString(delID);

        try{
            smt = dbconn.createStatement();
            ResultSet rs = smt.executeQuery(sql);

            if(rs.next()==false)
            {
                System.out.println("No orders to show");
                return;
            }
            System.out.println("orderID " + "Name" + " address " + "phoneNumber");
            do{
                Integer orderid = rs.getInt("orderID");
                String custname = rs.getString("name");
                String address = rs.getString("address");
                String phno = rs.getString("phoneNumber");
                System.out.println(Integer.toString(orderid)+" "+custname+" "+address+" "+phno);
            }while(rs.next());
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
            pst = dbconn.prepareStatement(sql);
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

            if (rs.next()==false){
                System.out.println("Error - no matching shipment");
                return;
            }

            sql = "UPDATE orderDesc SET status = ? where orderID = ?";
            pstmt = dbconn.prepareStatement(sql);
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
    @Override
    public void delUpdateOrderdelDate(Integer delId,Integer OrderID,Date dt){ 
        Statement stmt = null;
        PreparedStatement pstmt = null;
        
        try {
            stmt = dbconn.createStatement();
            String sql;
            sql = "SELECT delID FROM shipments where delID = "+Integer.toString(delId)+" and orderID = "+Integer.toString(OrderID);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()==false){
                System.out.println("Error - no matching shipment");
            }
            else{
                sql = "UPDATE orders SET delDate = ? where orderID = ?";
                pstmt = dbconn.prepareStatement(sql);
                pstmt.setDate(1, new java.sql.Date(dt.getTime()));
                pstmt.setInt(2, OrderID);
                pstmt.executeUpdate();

                System.out.println("Status updated");
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

    @Override
    public boolean updateInventory(Integer ProductID,String type,Integer Quantity){
        if (Quantity <= 0){
            System.out.println("ProductID "+ProductID+" has quantity negative");
            return false;
        }
        PreparedStatement pst = null;
        Statement st = null;
        Integer quant=0;
        String sql = "select quantity from inventory where productID="+ProductID;
        try{
            st = dbconn.createStatement();
            ResultSet rt = st.executeQuery(sql);
            if(rt.next())
            {
                if (type == "D"){
                    quant = rt.getInt("quantity");
                    if(quant<Quantity)
                    {
                        System.out.println("Specified quantity not in Stock");
                        return false;
                    }
                    else
                    {   
                        sql = "update inventory set quantity = quantity- ? where productID= ?";
                        try{
                            pst = dbconn.prepareStatement(sql);
                            pst.setInt(1, Quantity);
                            pst.setInt(2, ProductID);
                            pst.executeUpdate();
                            System.out.println("Database updated successfully");
                        }
                        catch (SQLException e)
                        {
                            System.out.println(e.getMessage());
                            return false;
                        }
                    }
                }else if (type == "R"){
                    sql = "update inventory set quantity = quantity + ? where productID= ?";
                    try{
                        pst = dbconn.prepareStatement(sql);
                        pst.setInt(1, Quantity);
                        pst.setInt(2, ProductID);
                        pst.executeUpdate();
                        // System.out.println("Database updated successfully");
                    }
                    catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                        return false;
                    }
                }
                
            }else{
                System.out.println("ProductID "+ProductID+" invalid");
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        try{
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void orderReturn(Integer CustomerID,Integer ProductID,Integer OrderID)
    {
        Statement st=null;
        PreparedStatement pst=null;
        
        String sql;
        ResultSet rs=null;
        sql = "select o.customerID from orders as o, orderDesc as od where od.orderID="+OrderID+" and od.orderID=o.orderID and od.productID="+ProductID+" and o.customerID="+CustomerID;
        
        
        try{
            st = dbconn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()==false)
            {
                System.out.println("Invalid Data");
                return;
            }
            sql = "update orderDesc set type= \"R\", status= \"Returned\" where orderID= ? and productID= ?";
            pst = dbconn.prepareStatement(sql);
            pst.setInt(1, OrderID);
            pst.setInt(2, ProductID);
            pst.executeUpdate();
            System.err.println("Database updated successfully");
            try{
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sellerReturnUpdate(Integer sellerID,Integer OrderID,Integer productID,Integer quantity){
        Statement st=null;        
        String sql;
        ResultSet rs=null;
        sql = "select o.sellerID from orders as o, orderDesc as od where o.sellerID= " + sellerID + " and o.orderID = "+ OrderID+"and od.productID=" + productID;
        try{
            st=dbconn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()==false)
            {
                System.out.println("Invalid Data");
                return ;
            }
            else
            {
                updateInventory(productID,"R",quantity);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sellerPendingOrders(Integer sellerID){
        Statement st = null;
        String sql;
        sql = "SELECT sellerID from seller where sellerID="+sellerID;
        try {
            st = dbconn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next() == false){
                System.out.println("Invalid sellerID");
                return ;
            }

            sql = "SELECT od.orderID, od.productID, od.quantity from orderDesc od, inventory i where od.productID = i.productID and od.status = \"Placed\" and i.sellerID = "+sellerID;
            rs = st.executeQuery(sql);

            System.out.println("OrderID ProductID Quantity");
            while (rs.next()){
                Integer orderID = rs.getInt("orderID");
                Integer productID = rs.getInt("productID");
                Integer quantity = rs.getInt("quantity");
                System.out.println(orderID+" "+productID+" "+quantity);
            }
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}