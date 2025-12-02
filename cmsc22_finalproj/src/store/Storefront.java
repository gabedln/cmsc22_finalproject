package store;

import user.Seller;
import product.Product;
import java.util.ArrayList;
import java.util.Scanner;

public class Storefront {

    private ArrayList<Seller> sellers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void addSeller(Seller seller) {
        sellers.add(seller);
    }

    public void showAllSellers() {
        if (sellers.isEmpty()) {
            System.out.println("No sellers available.");
            return;
        }

        System.out.println("Available Sellers:");
        for (int i = 0; i < sellers.size(); i++) {
            Seller s = sellers.get(i);
            System.out.println("[" + (i + 1) + "] " + s.getUsername() + " | Location: " + s.getLocation());
        }
    }

    
    public void chooseSeller() {
        showAllSellers();

        if (sellers.isEmpty()) return;

        System.out.print("Select a seller by number: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > sellers.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Seller chosenSeller = sellers.get(choice - 1);
        showSellerStorefront(chosenSeller);
    }

    public void showSellerStorefront(Seller seller) {
        ArrayList<Product> products = seller.getProductList();

        if (products.isEmpty()) {
            System.out.println("This seller has no products available.");
            return;
        }

        System.out.println("\n" + seller.getUsername() + "'s Storefront:");
        for (Product p : products) {
            System.out.println("ID: " + p.getId() +
                               " | Name: " + p.getName() +
                               " | Category: " + p.getCategory() +
                               " | Price: $" + p.getPrice() +
                               " | Stock: " + p.getStock());
        }
    }
}




