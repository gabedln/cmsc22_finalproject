package application;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import product.Product;
import user.Seller;

public class AddProduct {
    private Scene addProduct;

    public AddProduct(Stage stage, Scene previous, Seller seller) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("add-product");
        Scene addProduct = new Scene(root, 1024, 576);
        addProduct.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.addProduct = addProduct;

        TextField productnameField = new TextField();
        productnameField.setPromptText("product name");
        productnameField.getStyleClass().add("products-field");
        productnameField.setMinWidth(275);

        TextField priceField = new TextField();
        priceField.setPromptText("price");
        priceField.getStyleClass().add("products-field");
        priceField.setMinWidth(275);

        TextField stockField = new TextField();
        stockField.setPromptText("stock");
        stockField.getStyleClass().add("products-field");
        stockField.setMinWidth(275);

        ComboBox<String> categoryField = new ComboBox<>();
        categoryField.setValue("canned goods");
        categoryField.getItems().addAll(
            "canned goods",
            "condiments",
            "snacks",
            "beverages",
            "health care",
            "cleaning"
        );
        categoryField.getStyleClass().add("product-combo-box");
        categoryField.setMinWidth(275);

        Button addButton = new Button("add product");
        addButton.setDisable(true);
        addButton.setMinWidth(275);
        addButton.getStyleClass().add("add-button");

        Button back = new Button("back");
        back.setMinWidth(275);
        back.getStyleClass().add("product-back-button");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                stage.setScene(previous);
            }
        });

        // Enable add button only when all fields are filled
        ChangeListener<String> textFieldListener = (obs, oldVal, newVal) -> {
            boolean allFilled = !productnameField.getText().trim().isEmpty()
                    && !priceField.getText().trim().isEmpty()
                    && !stockField.getText().trim().isEmpty();
            addButton.setDisable(!allFilled);
        };

        productnameField.textProperty().addListener(textFieldListener);
        priceField.textProperty().addListener(textFieldListener);
        stockField.textProperty().addListener(textFieldListener);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 90 40 20 65;");
        grid.setAlignment(Pos.CENTER);

        grid.addRow(0, productnameField);
        grid.addRow(1, priceField);
        grid.addRow(2, stockField);
        grid.addRow(3, categoryField);
        grid.addRow(4, addButton);
        grid.addRow(5, back);

        root.setCenter(grid);

        // Add product logic
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
                try {
                    String name = productnameField.getText().trim();
                    String category = categoryField.getValue();
                    float price = Float.parseFloat(priceField.getText().trim());
                    int stock = Integer.parseInt(stockField.getText().trim());

                    // Validate inputs
                    if (price <= 0 || stock < 0) {
                        System.out.println("Price must be positive and stock cannot be negative.");
                        return;
                    }

                    // Create product and add to seller's product list
                    Product newProduct = new Product(name, category, price, stock, seller);
                    seller.addProduct(newProduct);

                    System.out.println("Product added successfully: " + name);

                    // Go to SellerStorefront after adding product
                    SellerStorefront storefront = new SellerStorefront(stage, previous, seller);
                    stage.setScene(storefront.getScene());
                    
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input: Price must be a number and stock must be an integer.");
                    ex.printStackTrace();
                }
            }
        });
    }

    public Scene getScene() { 
        return this.addProduct; 
    }
}