package application.models.entities;

public class Accounts {
	
	private String userName, fullName, phone, password;
	private boolean isAdmin;
	
	public Accounts()
	{
		userName = fullName = phone = password = "";
		isAdmin = false;
	}
	
	public Accounts(String userName, String fullName, String phone, String password, boolean isAdmin)
	{
		this.userName = userName;
		this.fullName = fullName;
		this.phone = phone;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getFullName()
	{
		return fullName;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean getIsAdmin()
	{
		return isAdmin;
	}

}