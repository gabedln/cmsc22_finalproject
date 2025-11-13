package user;

public abstract class User {
	private String username;
	private String password;
	private String displayName;
	private double balance;
	private String location;

	public String getUsername(){ return username; }
	public String getDisplayName() { return displayName; }
	public double getBalance() { return balance; }
	public String getLocation() { return location; }
	
	
	public User(String username, String password, String displayName, double balance, String location) {
		this.username = username;
		this.password = password;
		this.displayName = displayName;
		this.balance = balance;
		this.location = location;
	}
	
	public abstract void login(String username, String password);
	public abstract void getPassword();
	public abstract void viewDetails();
	public abstract void changeDisplayName(String displayName);
	public abstract void changeLocation(String location);
}
