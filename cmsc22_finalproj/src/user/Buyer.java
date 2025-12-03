package user;

import product.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Buyer extends User {

    private static final long serialVersionUID = 1L;

    private ArrayList<Product> wishlist = new ArrayList<>();
    private ArrayList<Vouchers> voucherList = new ArrayList<>();
    private HashMap<Product, Integer> cart = new HashMap<>();
    private ArrayList<TransactionHistory> transactions = new ArrayList<>();

    public Buyer(String displayName, String username, String password, float balance, String location) {
        super(displayName, username, password, balance, location);
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n===== BUYER DASHBOARD =====");
        System.out.println("Username: " + getUsername());
        System.out.println("Display Name: " + getDisplayName());
        System.out.println("Location: " + getLocation());
        System.out.println("Balance: ₱" + String.format("%.2f", getBalance()));
        System.out.println("Cart Items: " + cart.size());
        System.out.println("Wishlist Items: " + wishlist.size());
        System.out.println("Total Transactions: " + transactions.size());
        System.out.println("===========================\n");
    }

    // Wishlist
    public void addToWishlist(Product product) {
        if (!wishlist.contains(product)) wishlist.add(product);
    }

    public void removeWishlist(Product product) {
        wishlist.remove(product);
    }

    public void wishlistToCart(Product product) {
        if (wishlist.contains(product)) cart.put(product, 1);
    }

    // Cart
    public void addToCart(Product product) {
        cart.put(product, cart.getOrDefault(product, 0) + 1);
    }

    public void addQuantity(Product product) {
        if (cart.containsKey(product)) cart.put(product, cart.get(product) + 1);
    }

    public void reduceQuantity(Product product) {
        if (cart.containsKey(product)) {
            int qty = cart.get(product);
            if (qty <= 1) cart.remove(product);
            else cart.put(product, qty - 1);
        }
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        float overallTotal = 0f;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            float total = p.getPrice() * qty;

            System.out.println("ID: " + p.getId()
                    + " | Name: " + p.getName()
                    + " | Qty: " + qty
                    + " | Price each: ₱" + p.getPrice()
                    + " | Total: ₱" + total);

            overallTotal += total;
        }
        System.out.println("-----------------------------------");
        System.out.println("Overall total: ₱" + overallTotal);
    }

    // Transaction History
    public void viewTransactionHistory() {
        System.out.println(getUsername() + "'s Transaction History:");
        if (transactions.isEmpty()) {
            System.out.println("No recorded transactions yet.");
            return;
        }

        int totalItems = 0;
        float totalSpent = 0f;
        for (TransactionHistory th : transactions) {
            System.out.println("ID: " + th.getProduct().getId()
                    + " | Name: " + th.getProduct().getName()
                    + " | Price: ₱" + th.getProduct().getPrice());
            totalItems += th.getQuantity();
            totalSpent += th.getTotalCost();
        }
        System.out.println("-----------------------------------");
        System.out.println("Total items bought: " + totalItems);
        System.out.println("Total amount spent: ₱" + totalSpent);
    }

    // Getters for collections
    public ArrayList<Product> getWishlist() { return wishlist; }
    public ArrayList<Vouchers> getVoucherList() { return voucherList; }
    public HashMap<Product, Integer> getCart() { return cart; }
    public ArrayList<TransactionHistory> getTransactions() { return transactions; }
}
