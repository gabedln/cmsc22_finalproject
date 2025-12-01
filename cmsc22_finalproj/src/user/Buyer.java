package user;


import java.io.Serializable;
import java.util.ArrayList;
import product.*;

public class Buyer extends User implements Serializable {
	
	private static final long serialVersionUID = 3L;
    private ArrayList<Product> cart = new ArrayList<>();
    private ArrayList<Product> wishlist = new ArrayList<>();
    private ArrayList<Product> bought = new ArrayList<>();
    private ArrayList<Vouchers> vouchers = new ArrayList<>();

    public Buyer(String displayName, String username, String password, float balance, String location){
        super(displayName, username, password, balance, location);
    }

    @Override
    public void displayDashboard() {

    }
    public ArrayList<Vouchers> getVouchers() { return this.vouchers; }

}
