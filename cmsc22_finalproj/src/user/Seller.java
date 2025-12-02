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
    public static ArrayList<Product> storeProducts = new ArrayList<>();     // global store list

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
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Price: ");
        float price = sc.nextFloat();

        System.out.print("Enter Stock: ");
        int stock = sc.nextInt();
        sc.nextLine(); // clear leftover newline

        Product product = new Product(name, category, price, stock, this);
        productList.add(product);
        storeProducts.add(product);

        System.out.println("Product added successfully! ID: " + product.getId() + " | " + product.getName());
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
        System.out.print("Enter discount (%): ");
        int discount = sc.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        System.out.print("Enter minimum purchase: ");
        float min = sc.nextFloat();

        System.out.print("Enter cap value: ");
        float cap = sc.nextFloat();
        sc.nextLine(); // clear trailing newline

        Vouchers voucher = new Vouchers(this, discount, quantity, cap, min);
        voucherList.add(voucher);

        System.out.println("Voucher added successfully! Code: " + voucher.getVoucherCode());
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
