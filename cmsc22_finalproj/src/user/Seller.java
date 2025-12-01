package user;

import java.util.ArrayList;
import product.*;

public class Seller extends User {
	
	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<Vouchers> vouchers = new ArrayList<>();
	
	public Seller(String displayName, String username, String password, float balance, String location){
		super(displayName, username, password, balance, location);
	}
	
	public void add(Product product) {
		this.products.add(product);
	}
	
	/*
	 * Sellers are users who can sell products in their own storefront. 
	 * Sellers also have a product list, a voucher list and a transaction log. 
	 * Sellers can add, sell, arrange and hide products, add or remove vouchers, 
	 * and log transactions. 
	 */

	@Override
	public void displayDashboard() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Vouchers> getVouchers() { return this.vouchers; }
	
	

}
