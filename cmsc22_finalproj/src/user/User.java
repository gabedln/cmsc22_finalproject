/*
 * This package is the superclass of Buyer and Seller objects
 * This shows the screen after logging in.
 * The methods here are overridden by its subclass depending on the type of user they are
 * For example, in the viewDashboard, a buyer will have a view cart option
 * while a seller will have a view products option
 */

package user;

import java.io.Serializable;

public abstract class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String displayName;
	private String username;
	private String password;
	private float balance;
	private String location;
	
	public User(String displayName, String username, String password, float balance, String location) {
		this.displayName = displayName;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.location = location;
	}
	
	public abstract void displayDashboard();
	
	
	public void changeDisplayName() {

	}
	
	public void changeLocation() {
	}
	
	
	public String getDisplayName() { return this.displayName; }
	public String getName() { return this.username; }
	public String getPassword() { return this.password; }
	public String getUsername() { return this.username; }
	public String getLocation() { return this.location; }
	public float getBalance() { return this.balance; }
	public void setBalance(float balance) { this.balance = balance; }

}
