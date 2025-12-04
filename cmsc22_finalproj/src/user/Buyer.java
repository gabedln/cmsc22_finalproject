package user;

import product.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Buyer extends User {

    private static final long serialVersionUID = 3L;

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

    public boolean processBuy(ArrayList<Product> products) {
    if (!checkStock(products)) {
        System.out.println("Insufficient stock for one or more products.");
        return false;
    }

    HashMap<Seller, Float> discountedTotals = calculateDiscountedTotals(products);

    float grandTotal = 0f;
    for (Float sellerTotal : discountedTotals.values()) {
        grandTotal += sellerTotal;
    }

    if (!finalizeCheckout(grandTotal, discountedTotals, products)) {
        System.out.println("Insufficient balance. Needed: ₱" + grandTotal);
        return false;
    }

    System.out.println("Checkout successful! Total paid: ₱" + grandTotal);
    return true;
}

private boolean checkStock(ArrayList<Product> products) {
    for (Product product : products) {
        int quantity = cart.getOrDefault(product, 0);
        if (quantity > product.getStock()) {
            return false;
        }
    }
    return true;
}

private HashMap<Seller, Float> calculateDiscountedTotals(ArrayList<Product> products) {
    HashMap<Seller, Float> sellerTotals = new HashMap<>();

    for (Product product : products) {
        int quantity = cart.getOrDefault(product, 0);
        Seller seller = product.getSeller();
        float subtotal = product.getPrice() * quantity;
        sellerTotals.put(seller, sellerTotals.getOrDefault(seller, 0f) + subtotal);
    }

    HashMap<Seller, Float> discountedTotals = new HashMap<>();

    for (Map.Entry<Seller, Float> sellerEntry : sellerTotals.entrySet()) {
        Seller seller = sellerEntry.getKey();
        float sellerSubtotal = sellerEntry.getValue();

        float bestDiscount = 0f;
        Vouchers bestVoucher = null;

        for (Vouchers voucher : voucherList) {
            if (sellerSubtotal >= voucher.getMinimum() && voucher.getQuantity() > 0 && voucher.getSeller() == seller) {
                float discountAmount = sellerSubtotal * (voucher.getDiscount() / 100f);
                if (discountAmount > voucher.getCap()) discountAmount = voucher.getCap();

                if (discountAmount > bestDiscount) {
                    bestDiscount = discountAmount;
                    bestVoucher = voucher;
                }
            }
        }

        if (bestVoucher != null) {
            bestVoucher.reduceQuantity();
        }

        discountedTotals.put(seller, sellerSubtotal - bestDiscount);
    }

    return discountedTotals;
}

private boolean finalizeCheckout(float grandTotal, HashMap<Seller, Float> discountedTotals, ArrayList<Product> products) {
    if (getBalance() < grandTotal) {
        return false;
    }

    setBalance(getBalance() - grandTotal);

    for (Product product : products) {
        int quantity = cart.getOrDefault(product, 0);
        Seller seller = product.getSeller();

        float sellerTotal = discountedTotals.getOrDefault(seller, 0f);
        seller.setBalance(seller.getBalance() + sellerTotal);

        product.setStock(product.getStock() - quantity);

        TransactionHistory transactionHistory = new TransactionHistory(this, seller, product, quantity, sellerTotal);
        transactions.add(transactionHistory);
        seller.logTransaction(transactionHistory);
    }

    cart.clear();
    return true;
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
