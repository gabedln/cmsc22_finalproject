package user;

import java.util.ArrayList;
import java.util.Scanner;
import product.*;

public class Buyer extends User {
    
    private Scanner sc = new Scanner(System.in);

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
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        System.out.println("Your Cart:");
        for (Product product : cart) {
            System.out.println("ID: " + product.getId() +
                               " | Name: " + product.getName() +
                               " | Price: $" + product.getPrice() +
                               " | Stock: " + product.getStock());
        }

        System.out.println("\nEnter Product ID to buy or remove:");
        int idChoice = sc.nextInt();
        sc.nextLine();

        Product chosen = null;
        for (Product product : cart) {
            if (product.getId() == idChoice) {
                chosen = product;
                break;
            }
        }

        if (chosen == null) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.println("[1] Buy now");
        System.out.println("[2] Delete from Cart");
        int actionChoice = sc.nextInt();
        sc.nextLine();

        if (actionChoice == 1) {
            buy(chosen);
        } else if (actionChoice == 2) {
            cart.remove(chosen);
            System.out.println("Product successfully removed from cart");
        }
    }

    public void viewWishlist() {
        if (wishlist.isEmpty()) {
            System.out.println("Wishlist is empty!");
            return;
        }

        System.out.println("Your Wishlist:");
        for (Product product : wishlist) {
            System.out.println("ID: " + product.getId() +
                               " | Name: " + product.getName() +
                               " | Price: $" + product.getPrice() +
                               " | Stock: " + product.getStock());
        }

        System.out.println("\nEnter Product ID to manage:");
        int idChoice = sc.nextInt();
        sc.nextLine();

        Product chosen = null;
        for (Product product : wishlist) {
            if (product.getId() == idChoice) {
                chosen = product;
                break;
            }
        }

        if (chosen == null) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.println("[1] Add to Cart");
        System.out.println("[2] Buy Now");
        System.out.println("[3] Remove from Wishlist");
        int actionChoice = sc.nextInt();
        sc.nextLine();

        if (actionChoice == 1) {
            cart.add(chosen);
            System.out.println("Product successfully added to cart");
        } else if (actionChoice == 2) {
            buy(chosen);
        } else if (actionChoice == 3) {
            wishlist.remove(chosen);
            System.out.println("Product removed from wishlist");
        }
    }

    public void buy(Product product) {
        if(this.getBalance() >= product.getPrice()) {
            if(product.getStock() > 0) {
                setBalance(getBalance() - product.getPrice());
                product.setStock(product.getStock() - 1);

                cart.remove(product);
                bought.add(product);

                System.out.println(product.getName() + " successfully bought!");
                System.out.println("Current balance: $" + getBalance());
            } else {
                System.out.println(product.getName() + " out of stock.");
            }
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void viewTransactionHistory() {
        System.out.println(getUsername() + "'s Transaction History");

        if (bought.isEmpty()) {
            System.out.println("No recorded transactions yet.");
        } else {
            int totalItems = 0;
            float totalPrice = 0;

            for (Product product : bought) {
                System.out.println("ID: " + product.getId() +
                                   " | Name: " + product.getName() +
                                   " | Price: $" + product.getPrice());
                totalItems++;
                totalPrice += product.getPrice();
            }

            System.out.println("-----------------------------------");
            System.out.println("Total items bought: " + totalItems);
            System.out.println("Total amount spent: $" + totalPrice);
        }
    }
}
