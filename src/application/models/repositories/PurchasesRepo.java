package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import application.models.entities.Purchases;
import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.DateFormatter;

public class PurchasesRepo {
	
	public PurchasesRepo()
	{

	}
	
	public ArrayList<Purchases> getAllPurchases()
	{
		ArrayList<Purchases> purchasesList = new ArrayList<Purchases>();
		Connection connection = DatabaseConnection.connect();
		try
		{
			String query = "SELECT p.*, s.name AS supplierName FROM purchases p " +
                    "INNER JOIN suppliers s ON p.supplierId = s.id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				purchasesList.add(new Purchases(
						resultSet.getInt("id"),
						count,
						resultSet.getInt("supplierId"),
						resultSet.getString("supplierName"),
						resultSet.getObject("purchaseDate").toString(),
						resultSet.getString("invoiceNum"),
						resultSet.getDouble("grossTotal"),
						resultSet.getString("salesTax"),
						resultSet.getString("discount"),
						resultSet.getDouble("otherCharges"),
						resultSet.getDouble("netTotal"),
						resultSet.getBoolean("isReturn"),
						resultSet.getBoolean("isLoose"),
						resultSet.getString("shift"),
						resultSet.getDouble("amountPaid")
					));
				count++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return purchasesList;
	}
	
	public Purchases getPurchase(int id) {
		Purchases purchase = null;
		Connection connection = DatabaseConnection.connect();
		try
		{
			String query = "SELECT p.*, s.name AS supplierName FROM purchases p " +
                    "INNER JOIN suppliers s ON p.supplierId = s.id WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				purchase = new Purchases(
						resultSet.getInt("id"),
						0,
						resultSet.getInt("supplierId"),
						resultSet.getString("supplierName"),
						resultSet.getObject("purchaseDate").toString(),
						resultSet.getString("invoiceNum"),
						resultSet.getDouble("grossTotal"),
						resultSet.getString("salesTax"),
						resultSet.getString("discount"),
						resultSet.getDouble("otherCharges"),
						resultSet.getDouble("netTotal"),
						resultSet.getBoolean("isReturn"),
						resultSet.getBoolean("isLoose"),
						resultSet.getString("shift"),
						resultSet.getDouble("amountPaid")
					);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return purchase;
	}
	
	public int addPurchase(int supplierId, String purchaseDate, String invoiceNum, double grossTotal,
            String salesTax, String discount, double otherCharges, double netTotal,
            boolean isReturn, boolean isLoose, String shift, double amountPaid) 
	{
		Connection connection = DatabaseConnection.connect();
		int generatedId = -1;
		
		try {
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO purchases " +
		     "(supplierId, purchaseDate, invoiceNum, grossTotal, salesTax, discount, otherCharges, netTotal, isReturn, isLoose, shift, amountPaid) " +
		     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		preparedStatement.setInt(1, supplierId);
		preparedStatement.setString(2, purchaseDate);
		preparedStatement.setString(3, invoiceNum);
		preparedStatement.setDouble(4, grossTotal);
		preparedStatement.setString(5, salesTax);
		preparedStatement.setString(6, discount);
		preparedStatement.setDouble(7, otherCharges);
		preparedStatement.setDouble(8, netTotal);
		preparedStatement.setBoolean(9, isReturn);
		preparedStatement.setBoolean(10, isLoose);
		preparedStatement.setString(11, shift);
		preparedStatement.setDouble(12, amountPaid);
		
		int affectedRows = preparedStatement.executeUpdate();
		
		if (affectedRows > 0) {
		 try (Statement statement = connection.createStatement();
		      ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid()")) {
		     if (resultSet.next()) {
		         generatedId = resultSet.getInt(1);
		     }
		 }
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
		} finally {
		try {
		 connection.close();
		} catch (SQLException e) {
		 e.printStackTrace();
		}
}

return generatedId;
}

	
	public ArrayList<Purchases> updatePurchase(int id, Purchases updatedPurchase) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE purchases SET supplierId = ?, purchaseDate = ?, invoiceNum = ?, grossTotal = ?, salesTax = ?, discount = ?, otherCharges = ?, netTotal = ?, isReturn = ?, isLoose = ?, shift = ?, amountPaid = ? WHERE id = ?");
	        statement.setInt(1, updatedPurchase.getSupplierId());
			statement.setObject(2, updatedPurchase.getPurchaseDate());
			statement.setString(3, updatedPurchase.getInvoiceNum());
			statement.setDouble(4, updatedPurchase.getGrossTotal());
			statement.setString(5, updatedPurchase.getSalesTax());
			statement.setString(6, updatedPurchase.getDiscount());
			statement.setDouble(7, updatedPurchase.getOtherCharges());
			statement.setDouble(8, updatedPurchase.getNetTotal());
			statement.setBoolean(9, (updatedPurchase.getIsReturn() == "Yes" ? true : false));
			statement.setBoolean(10, updatedPurchase.getIsLoose());
			statement.setString(11, updatedPurchase.getShift());
			statement.setDouble(12, updatedPurchase.getAmountPaid());
			statement.setInt(13, id);
	        statement.executeUpdate();
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    } finally {
	    	try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return getAllPurchases();
	}

	public ArrayList<Purchases> deletePurchase(int id) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM purchases WHERE id = ?");
	        statement.setInt(1, id);

	        statement.executeUpdate();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    } finally {
	    	try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return getAllPurchases();
	}
	
	public ArrayList<Purchases> fetchPurchasesByInvoiceNumber(String invoiceNumber) 
	{
        ArrayList<Purchases> searchData = new ArrayList<>();
        Connection conn = DatabaseConnection.connect();
        int count = 1;
        try
        {
        	String query = "SELECT p.*, " +
                    "s.name AS supplierName " +
                    "FROM purchases p " +
                    "JOIN suppliers s ON p.supplierId = s.id " +
                    "WHERE p.invoiceNum LIKE ? COLLATE NOCASE";

            try (PreparedStatement statement = conn.prepareStatement(query)) 
            {
            	statement.setString(1, "%" + invoiceNumber + "%");

                try (ResultSet resultSet = statement.executeQuery()) 
                {
                    while (resultSet.next())
                    {
                    	Purchases purchase = new Purchases(
        						resultSet.getInt("id"),
        						count,
        						resultSet.getInt("supplierId"),
        						resultSet.getString("supplierName"),
        						resultSet.getObject("purchaseDate").toString(),
        						resultSet.getString("invoiceNum"),
        						resultSet.getDouble("grossTotal"),
        						resultSet.getString("salesTax"),
        						resultSet.getString("discount"),
        						resultSet.getDouble("otherCharges"),
        						resultSet.getDouble("netTotal"),
        						resultSet.getBoolean("isReturn"),
        						resultSet.getBoolean("isLoose"),
        						resultSet.getString("shift"),
        						resultSet.getDouble("amountPaid")
                    	);
                        searchData.add(purchase);
                        count++;
                    }
                }
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
	    {
	    	try 
	    	{
				conn.close();
			} 
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
	    }
        return searchData;
    }
	
	public ArrayList<Purchases> fetchPurchaseByDate(LocalDate date) 
	{
		ArrayList<Purchases> searchData = new ArrayList<>();
		Connection connection = DatabaseConnection.connect();
		int count = 1;
        try
        {
        	String query = "SELECT p.*, " +
                    "s.name AS supplierName " +
                    "FROM purchases p " +
                    "JOIN suppliers s ON p.supplierId = s.id " +
                    "WHERE p.purchaseDate= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, DateFormatter.formatDate(date));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) 
            {
            	Purchases purchase = new Purchases(
						resultSet.getInt("id"),
						count,
						resultSet.getInt("supplierId"),
						resultSet.getString("supplierName"),
						resultSet.getObject("purchaseDate").toString(),
						resultSet.getString("invoiceNum"),
						resultSet.getDouble("grossTotal"),
						resultSet.getString("salesTax"),
						resultSet.getString("discount"),
						resultSet.getDouble("otherCharges"),
						resultSet.getDouble("netTotal"),
						resultSet.getBoolean("isReturn"),
						resultSet.getBoolean("isLoose"),
						resultSet.getString("shift"),
						resultSet.getDouble("amountPaid")
                );
            	searchData.add(purchase);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally
	    {
	    	try 
	    	{
				connection.close();
			} 
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
	    }
        return searchData;
    }
}