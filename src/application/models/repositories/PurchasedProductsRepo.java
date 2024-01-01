package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.PurchasedProducts;
import application.screens.purchases.PurchaseCartItem;
import application.utils.backendUtils.DatabaseConnection;

public class PurchasedProductsRepo {
	
	public void addPurchasedProduct(ArrayList<PurchaseCartItem> products, String purchaseId) 
	{
        Connection connection = DatabaseConnection.connect();

        try 
        {
            for(PurchaseCartItem product : products)
            {
            	PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO purchasedProducts " +
                        "(productId, purchaseId, quantity, bonus, discount, salesTax, netTotal, batchNum) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setString(1, String.valueOf(product.getProductId()));
                preparedStatement.setString(2, purchaseId);
                preparedStatement.setString(3, String.valueOf(product.getQuantity()));
                preparedStatement.setString(4, product.getBonusValue());
                preparedStatement.setString(5, product.getDiscount());
                preparedStatement.setString(6, product.getSalesTaxVal());
                preparedStatement.setString(7, product.getNetTotalValue());
                preparedStatement.setString(8, product.getBatchNumVal());

                preparedStatement.executeUpdate();
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
    }
	
	public ArrayList<PurchasedProducts> getPurchasedProductFromPurchaseId(int purchaseId)
	{
		Connection connection = DatabaseConnection.connect();
		ArrayList<PurchasedProducts> products = new ArrayList<>();
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT pp.*, p.name, p.purchasePrice " +
	                "FROM purchasedProducts pp " +
	                "INNER JOIN products p ON pp.productId = p.id " +
	                "WHERE pp.purchaseId = ?");
			preparedStatement.setString(1, String.valueOf(purchaseId));
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				PurchasedProducts purchasedProduct = new PurchasedProducts(
	                    resultSet.getInt("productId"),
	                    resultSet.getString("name"),
	                    purchaseId,
	                    resultSet.getInt("quantity"),
	                    resultSet.getInt("bonus"),
	                    resultSet.getDouble("purchasePrice"),
	                    resultSet.getString("discount"),
	                    resultSet.getString("salesTax"),
	                    resultSet.getDouble("netTotal"),
	                    resultSet.getString("batchNum")
	            );
				products.add(purchasedProduct);
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
		return products;
	}
}