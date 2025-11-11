package product;

import user.*;

public class Vouchers { // required attributes and methods
	private String name;
	private Seller seller;
	private double discount;
	private double maxDiscount;
	
	public Vouchers (String name, Seller seller, double discount, double maxDiscount) {
		this.name = name;
		this.seller = seller;
		this.discount = discount;
		this.maxDiscount = maxDiscount;
	}
}