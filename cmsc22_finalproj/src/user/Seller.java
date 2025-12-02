package user;

import product.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Seller extends User {

    private static final long serialVersionUID = 1L; // FIXED WARNING
    private static final Scanner sc = new Scanner(System.in); // GLOBAL SCANNER (NO LEAK)

    private ArrayList<Product> productList = new ArrayList<>();             // visible products
    private ArrayList<Product> hiddenList = new ArrayList<>();              // hidden products
    private ArrayList<Vouchers> voucherList = new ArrayList<>();            // vouchers offered
    private ArrayList<TransactionHistory> transactions = new ArrayList<>(); // sales logs
    private float balance;

    public Seller(String displayName, String username, String password, float balance, String location) {
        super(displayName, username, password, balance, location);
        this.balance = balance;
    }

    // Add an existing product to seller list + store list
    public void sell(Product product) {
        if (product == null) return;
        productList.add(product);
        storeProducts.add(product);
    }

    // Add new product through input
    public void addProduct() {
        Product product = new Product(name, category, price, stock, this);
        productList.add(product);
    }

    // Move visible product → hidden list
    public void hideProduct(Product product) {
        if (productList.remove(product)) {
            hiddenList.add(product);
        }
    }

    // Move hidden product → visible list
    public void unhideProduct(Product product) {
        if (hiddenList.remove(product)) {
            productList.add(product);
        }
    }

    // Create a new voucher
    public void addVoucher() {
        Vouchers voucher = new Vouchers(this, discount, quantity, cap, min);
        voucherList.add(voucher);
    }

    // Remove an existing voucher
    public void removeVoucher(Vouchers voucher) {
        voucherList.remove(voucher);
    }

    // Log a transaction (used when a customer buys)
    public void logTransaction(TransactionHistory transaction) {
        if (transaction != null) {
            transactions.add(transaction);
        }
    }

    public ArrayList<TransactionHistory> getTransactions() {
        return transactions;
    }

    // Balance controls
    public float getBalance() { 
        return balance; 
    }

    public void addBalance(float amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public void changeDisplayName(String name){
        this.displayName = name;
    }

    public void changeLocation (String location){
        this.location = location;
    }

    // Lists
    public ArrayList<Product> getProductList() { return productList; }
    public ArrayList<Product> getHiddenList() { return hiddenList; }
    public ArrayList<Vouchers> getVoucherList() { return voucherList; }

    @Override
    public void displayDashboard() {
        System.out.println("=== Seller Dashboard ===");
        System.out.println("Name: " + getDisplayName());
        System.out.println("Balance: ₱" + balance);
        System.out.println("Products Listed: " + productList.size());
        System.out.println("Hidden Products: " + hiddenList.size());
        System.out.println("Vouchers Offered: " + voucherList.size());
        System.out.println("========================");
    }
}
