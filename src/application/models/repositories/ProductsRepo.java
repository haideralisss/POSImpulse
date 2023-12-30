package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Products;
import application.models.entities.Suppliers;
import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.DateFormatter;
import application.utils.backendUtils.NumberFormatter;

public class ProductsRepo 
{
	
	public ArrayList<Products> getAllProducts()
	{
		ArrayList<Products> productsList = new ArrayList<Products>();
		Connection connection = DatabaseConnection.connect();
	    try
	    {
			String query = "SELECT p.*, c.name AS companyName FROM products p " +
                    "INNER JOIN companies c ON p.companyId = c.id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				productsList.add(new Products(
						count,
						resultSet.getString("name"),
						resultSet.getInt("packSize"),
						resultSet.getDouble("purchasePrice"),
						resultSet.getDouble("retailPrice"),
						resultSet.getInt("companyId"),
						resultSet.getString("companyName")
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
		return productsList;
	}
	
	public Products getProduct(int id)
	{
	    Products product = null;
		Connection connection = DatabaseConnection.connect();
	    try
	    {
	        String query = "SELECT p.*, c.name AS companyName FROM products p " +
	                       "INNER JOIN companies c ON p.companyId = c.id " +
	                       "WHERE p.id = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, id);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) 
	        {
	            product = new Products(
	                    resultSet.getInt("id"),
	                    resultSet.getString("name"),
	                    resultSet.getInt("packSize"),
	                    resultSet.getDouble("purchasePrice"),
	                    resultSet.getDouble("retailPrice"),
	                    resultSet.getInt("companyId"),
	                    resultSet.getString("companyName")
	            );
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
	    return product;
	}
	
	public ArrayList<Products> addProduct(Products product)
	{
		Connection connection = DatabaseConnection.connect();
		try
	    {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, product.getName());
			statement.setInt(2, product.getPackSize());
			statement.setDouble(3, product.getPurchasePrice());
			statement.setDouble(4, product.getRetailPrice());
			statement.setInt(5, product.getCompanyId());
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
		return getAllProducts();
	}
	
	public ArrayList<Products> updateProduct(int id, Products updatedProduct) 
	{
		Connection connection = DatabaseConnection.connect();
		try
	    {
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE products SET name = ?, packSize = ?, purchasePrice = ?, retailPrice = ?, companyId = ? WHERE id = ?");
	        statement.setString(1, updatedProduct.getName());
			statement.setInt(2, updatedProduct.getPackSize());
			statement.setDouble(3, updatedProduct.getPurchasePrice());
			statement.setDouble(4, updatedProduct.getRetailPrice());
			statement.setInt(5, updatedProduct.getCompanyId());
			statement.setInt(6, id);
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
	    return getAllProducts();
	}

	public ArrayList<Products> deleteProduct(int id) 
	{

		Connection connection = DatabaseConnection.connect();
		try
	    {
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?");
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
	    return getAllProducts();
	}
	
	public ArrayList<Products> fetchByProductName(String productName) 
	{
        ArrayList<Products> searchData = new ArrayList<>();
        Connection connection = DatabaseConnection.connect();
        int count = 1;
        try
        {
        	String query = "SELECT p.id, p.name, p.packSize, p.purchasePrice, p.retailPrice, " +
                    "c.name AS companyName " +
                    "FROM products p " +
                    "JOIN companies c ON p.companyId = c.id " +
                    "WHERE p.name LIKE ? COLLATE NOCASE LIMIT 10";

            try (PreparedStatement statement = connection.prepareStatement(query)) 
            {
                statement.setString(1, "%" + productName + "%");

                try (ResultSet resultSet = statement.executeQuery()) 
                {
                    while (resultSet.next()) 
                    {
                        Products product = new Products(
                        		count,
                                resultSet.getString("name"),
                                resultSet.getInt("packSize"),
                                resultSet.getDouble("purchasePrice"),
                                resultSet.getDouble("retailPrice"),
                                1,
                                resultSet.getString("companyName")
                        );
                        searchData.add(product);
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
