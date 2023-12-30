package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Purchases;
import application.utils.backendUtils.DatabaseConnection;

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
	
	public ArrayList<Purchases> addPurchase(Purchases purchase)
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO purchases VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, purchase.getSupplierId());
			statement.setObject(2, purchase.getPurchaseDate());
			statement.setString(3, purchase.getInvoiceNum());
			statement.setDouble(4, purchase.getGrossTotal());
			statement.setString(5, purchase.getSalesTax());
			statement.setString(6, purchase.getDiscount());
			statement.setDouble(7, purchase.getOtherCharges());
			statement.setDouble(8, purchase.getNetTotal());
			statement.setBoolean(9, purchase.getIsReturn());
			statement.setBoolean(10, purchase.getIsLoose());
			statement.setString(11, purchase.getShift());
			statement.setDouble(12, purchase.getAmountPaid());
			statement.executeUpdate();
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
		return getAllPurchases();
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
			statement.setBoolean(9, updatedPurchase.getIsReturn());
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
}