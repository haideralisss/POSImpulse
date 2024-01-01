package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Stock;
import application.screens.billing.BillCartItem;
import application.screens.purchases.PurchaseCartItem;
import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.NumberFormatter;

public class StockRepo {
	
	public StockRepo()
	{

	}
	
	public String fetchStockWorth() 
	{
		String stockWorth = "0";
		double totalAmount = 0;
		Connection conn = DatabaseConnection.connect();
	    try
	    {
            PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT SUM((p.purchasePrice / p.packSize) * s.totalQuantity) AS totalAmount " +
                             "FROM stock s " +
                             "JOIN products p ON s.productId = p.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) 
            {
                totalAmount = resultSet.getDouble("totalAmount");
                stockWorth = NumberFormatter.format(totalAmount);
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
		return stockWorth;
	}
	
	public void updateStocksAfterBill(ArrayList<BillCartItem> items)
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
			for(BillCartItem item : items)
			{
				PreparedStatement preparedStatement = connection.prepareStatement(
	                    "UPDATE stock set totalQuantity= ? WHERE id= ?");
				preparedStatement.setString(1, String.valueOf(item.getNewQuantity()));
				preparedStatement.setString(2, String.valueOf(item.getStockId()));
				preparedStatement.executeUpdate();
			}
		}
		catch(SQLException e)
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
	}
	
	public void updateStocksAfterPurchase(ArrayList<PurchaseCartItem> items)
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
			for(PurchaseCartItem item : items)
			{
				if(item.getStockId() != 0)
				{
					PreparedStatement preparedStatement = connection.prepareStatement(
		                    "UPDATE stock set totalQuantity= ? WHERE id= ?");
					preparedStatement.setString(1, String.valueOf(item.getNewQuantity()));
					preparedStatement.setString(2, String.valueOf(item.getStockId()));
					preparedStatement.executeUpdate();
				}
				else
				{
					Stock stock = new Stock(0, 0, item.getProductId(), "Nothing", (item.getPackSize() / item.getRetailPriceVal()), item.getNewQuantity());
					addStock(stock);
				}
			}
		}
		catch(SQLException e)
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
	}
	
	public ArrayList<Stock> fetchProductByLowStock() 
	{
        ArrayList<Stock> productsData = new ArrayList<>();
        Connection conn = DatabaseConnection.connect();
        try 
        {
            String query = "SELECT " +
                    "products.name AS productId, " +
                    "SUM(stock.totalQuantity) AS totalQuantity " +
                    "FROM stock " +
                    "JOIN products ON stock.productId = products.id " +
                    "GROUP BY stock.productId " + 
                    "HAVING totalQuantity <= IFNULL(products.packSize, totalQuantity)";
        	

            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) 
            {
                while (resultSet.next()) 
                {
                	Stock stock = new Stock();
                    String productId = resultSet.getString("productId");
                    int totalQuantity = resultSet.getInt("totalQuantity");
                    stock.setProductNameAndTotalQuantity(productId, totalQuantity);
                    productsData.add(stock);
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
        return productsData;
    }
	
	public ArrayList<Stock> getAllStock()
	{
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		Connection connection = DatabaseConnection.connect();
		try
		{
			String query = "SELECT s.*, p.name AS productName FROM stock s " +
                    "INNER JOIN products p ON s.productId = p.id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				stockList.add(new Stock(
						resultSet.getInt("id"),
						count,
						resultSet.getInt("productId"),
						resultSet.getString("productName"),
						resultSet.getDouble("unitCost"),
						resultSet.getInt("totalQuantity")
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
		return stockList;
	}
	
	public Stock getStock(int id)
	{
		Stock stock = null;
		Connection connection = DatabaseConnection.connect();
		try
		{
			String query = "SELECT s.*, p.name AS productName FROM stock s " +
                    "INNER JOIN products p ON s.productId = p.id " +
					"WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				stock = new Stock(
						resultSet.getInt("id"),
						0,
						resultSet.getInt("productId"),
						resultSet.getString("productName"),
						resultSet.getDouble("unitCost"),
						resultSet.getInt("totalQuantity")
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
		return stock;
	}
	
	public ArrayList<Stock> addStock(Stock stock)
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO stock (productId, unitCost, totalQuantity) VALUES (?, ?, ?)");
			statement.setInt(1, stock.getProductId());
			statement.setDouble(2, stock.getUnitCost());
			statement.setInt(3, stock.getTotalQuantity());
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
		return getAllStock();
	}
	
	public ArrayList<Stock> updateStock(int id, Stock updatedStock) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE stock SET productId = ?, unitCost = ?, totalQuantity = ? WHERE id = ?");
	        statement.setInt(1, updatedStock.getProductId());
			statement.setDouble(2, updatedStock.getUnitCost());
			statement.setInt(3, updatedStock.getTotalQuantity());
	        statement.setInt(4, id);
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
	    return getAllStock();
	}

	public ArrayList<Stock> deleteStock(int id) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM stock WHERE id = ?");
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
	    return getAllStock();
	}
	
	public ArrayList<Stock> fetchStockByProductName(String productName) 
	{
		ArrayList<Stock> searchData = new ArrayList<>();
		Connection connection = DatabaseConnection.connect();
		int productId = 0;
		int count = 1;
        try
        {
        	String productQuery = "SELECT p.id from products p where name LIKE ? COLLATE NOCASE";
        	try (PreparedStatement statement = connection.prepareStatement(productQuery))
        	{
        		statement.setString(1, '%' + productName + '%');
        		try(ResultSet resultSet = statement.executeQuery())
        		{
        			if(resultSet.next())
        			{
        				productId = resultSet.getInt("id");
        			}
        		}
        	}
            String query = "SELECT s.* " +
                    "FROM stock s " +
                    "JOIN products p ON s.productId = p.id " +
                    "WHERE s.productId = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) 
            {
                statement.setInt(1, productId);

                try (ResultSet resultSet = statement.executeQuery()) 
                {
                    while(resultSet.next()) 
                    {
                    	Stock stock = new Stock(
                    			resultSet.getInt("id"),
        						count,
        						resultSet.getInt("productId"),
        						resultSet.getString("productName"),
        						resultSet.getDouble("unitCost"),
        						resultSet.getInt("totalQuantity")
                        );
                    	searchData.add(stock);
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
	
	public Stock fetchStockByProductId(String productId) 
	{
		Stock stock = null;
		Connection connection = DatabaseConnection.connect();
		int count = 1;
        try
        {
            String query = "SELECT * from stock WHERE productId= ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) 
            {
                statement.setString(1, productId);

                try (ResultSet resultSet = statement.executeQuery()) 
                {
                    while(resultSet.next()) 
                    {
                    	stock = new Stock(
                    			resultSet.getInt("id"),
        						count,
        						resultSet.getInt("productId"),
        						"Nothing",
        						resultSet.getDouble("unitCost"),
        						resultSet.getInt("totalQuantity")
                        );
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
        return stock;
    }
}
