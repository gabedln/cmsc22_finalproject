package user;

import store.Storefront;
import product.*;
import java.util.ArrayList;

public class Seller extends User{
	Storefront storefront;
	ArrayList<Product> products;
	ArrayList<Vouchers> vouchers;
	String transactionLog;

	public Seller(String username, String password, String displayName, double balance, String location) {
		super(username, password, displayName, balance, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewDetails() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeDisplayName(String displayName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeLocation(String location) {
		// TODO Auto-generated method stub
		
	}
	
	public void getPassword() {}

}


