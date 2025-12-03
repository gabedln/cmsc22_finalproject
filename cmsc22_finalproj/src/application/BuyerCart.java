package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import product.Product;
import product.Vouchers;
import user.Buyer;
import user.Seller;
import user.TransactionHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyerCart {

    private Scene scene;
    private VBox productBox;
    private final Buyer buyer;

    public BuyerCart(Stage stage, Scene previous, Buyer buyer) {
        this.buyer = buyer;

        BorderPane root = new BorderPane();
        root.getStyleClass().add("buyerscreen");

        // set background image
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/application/images/background_seller_storefront.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(bgImage));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        productBox = new VBox(25);
        productBox.setAlignment(Pos.TOP_CENTER);
        productBox.setStyle("-fx-padding: 40; -fx-background-color: transparent;");
        scrollPane.setContent(productBox);

        // back button
        Button backBtn = new Button("back");
        backBtn.setMinWidth(275);
        backBtn.getStyleClass().add("back-button");
        backBtn.setOnAction(e -> stage.setScene(previous));

        VBox bottomPadding = new VBox(backBtn);
        bottomPadding.setAlignment(Pos.CENTER);
        bottomPadding.setStyle("-fx-padding: 15; -fx-background-color: transparent;");

        refreshCart(stage);
        root.setCenter(scrollPane);
        root.setBottom(bottomPadding);

        scene = new Scene(root, 1024, 576);
        scene.setFill(Color.TRANSPARENT); // removes default white scene background
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    }

    private void refreshCart(Stage stage) {
        productBox.getChildren().clear();

        HashMap<Product, Integer> cart = buyer.getCart();

        Label title = new Label("Your Cart");
        title.getStyleClass().add("welcome-text");
        productBox.getChildren().add(title);

        if (cart.isEmpty()) {
            Label empty = new Label("Your cart is empty.");
            empty.getStyleClass().add("product-details");
            productBox.getChildren().add(empty);
            return;
        }

        float subtotal = 0f;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            productBox.getChildren().add(createCartCard(stage, p, qty));
            subtotal += p.getPrice() * qty;
        }

        final float finalSubtotal = subtotal;

        ArrayList<Vouchers> vouchers = buyer.getVoucherList();
        VBox voucherBox = new VBox(10);
        voucherBox.setAlignment(Pos.CENTER_LEFT);

        Label voucherLabel = new Label("Apply Vouchers:");
        voucherLabel.getStyleClass().add("product-name");
        voucherBox.getChildren().add(voucherLabel);

        ArrayList<CheckBox> voucherChecks = new ArrayList<>();
        for (Vouchers v : vouchers) {
            String text = "Voucher " + v.getVoucherCode() + " (" + (int)(v.getDiscount() * 100) +
                    "% off, min ₱" + v.getMinimum() + ", cap ₱" + v.getCap() + ")";
            CheckBox cb = new CheckBox(text);
            cb.setSelected(true);
            voucherChecks.add(cb);
            voucherBox.getChildren().add(cb);
        }
        productBox.getChildren().add(voucherBox);

        float[] totalHolder = new float[]{finalSubtotal};
        Label totalLabel = new Label("Subtotal: ₱" + String.format("%.2f", finalSubtotal));
        totalLabel.getStyleClass().add("product-name");
        productBox.getChildren().add(totalLabel);

        Button applyAll = new Button("apply all vouchers");
        applyAll.setMinWidth(250);
        applyAll.getStyleClass().add("back-button");
        applyAll.setOnAction(e -> {
            float total = finalSubtotal;
            for (int i = 0; i < vouchers.size(); i++) {
                Vouchers v = vouchers.get(i);
                if (voucherChecks.get(i).isSelected() && v.getQuantity() > 0 && total >= v.getMinimum()) {
                    float discountAmount = total * v.getDiscount();
                    if (discountAmount > v.getCap()) discountAmount = v.getCap();
                    total -= discountAmount;
                }
            }
            totalHolder[0] = total;
            totalLabel.setText("Total after vouchers: ₱" + String.format("%.2f", total));
        });
        productBox.getChildren().add(applyAll);

        Button checkoutBtn = new Button("checkout");
        checkoutBtn.setMinWidth(250);
        checkoutBtn.getStyleClass().add("add-button");
        checkoutBtn.setOnAction(e -> {
            float total = totalHolder[0];

            for (int i = 0; i < vouchers.size(); i++) {
                Vouchers v = vouchers.get(i);
                if (voucherChecks.get(i).isSelected() && total >= v.getMinimum() && v.getQuantity() > 0) {
                    v.reduceQuantity();
                }
            }

            buyer.setBalance(buyer.getBalance() - total);

            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                Seller s = p.getSeller();
                s.setBalance(s.getBalance() + p.getPrice() * qty);

                TransactionHistory th = new TransactionHistory(buyer, s, p, qty, p.getPrice() * qty);
                buyer.getTransactions().add(th);
                s.logTransaction(th);

                p.setStock(p.getStock() - qty);
            }

            cart.clear();
            Main.saveData();

            Alert confirm = new Alert(Alert.AlertType.INFORMATION);
            confirm.setTitle("Checkout Successful");
            confirm.setHeaderText(null);
            confirm.setContentText("Total paid: ₱" + String.format("%.2f", total));
            confirm.showAndWait();

            refreshCart(stage);
        });
        productBox.getChildren().add(checkoutBtn);
    }

    private HBox createCartCard(Stage stage, Product product, int qty) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(950);
        card.setStyle("-fx-padding: 20; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 20; -fx-background-color: transparent;");

        ImageView img = loadCategoryImage(product.getCategory());
        img.setFitHeight(80);
        img.setFitWidth(80);
        img.setPreserveRatio(true);

        VBox infoBox = new VBox(8);
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("product-name");
        Label detailsLabel = new Label("₱" + String.format("%.2f", product.getPrice()) +
                " | Qty: " + qty + " | Stock: " + product.getStock());
        detailsLabel.getStyleClass().add("product-details");
        infoBox.getChildren().addAll(nameLabel, detailsLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button plusBtn = new Button("+");
        plusBtn.setMinWidth(70);
        plusBtn.getStyleClass().add("add-button");
        plusBtn.setOnAction(e -> {
            if (qty < product.getStock()) {
                buyer.addQuantity(product);
                refreshCart(stage);
            }
        });

        Button minusBtn = new Button("-");
        minusBtn.setMinWidth(70);
        minusBtn.getStyleClass().add("back-button");
        minusBtn.setOnAction(e -> {
            buyer.reduceQuantity(product);
            refreshCart(stage);
        });

        Button deleteBtn = new Button("delete");
        deleteBtn.setMinWidth(120);
        deleteBtn.getStyleClass().add("back-button-main");
        deleteBtn.setOnAction(e -> {
            buyer.removeFromCart(product);
            refreshCart(stage);
        });

        buttonBox.getChildren().addAll(plusBtn, minusBtn, deleteBtn);
        card.getChildren().addAll(img, infoBox, buttonBox);
        return card;
    }

    private ImageView loadCategoryImage(String category) {
        String fname;
        switch (category.toLowerCase()) {
            case "beverages": fname = "beverages.png"; break;
            case "canned goods": fname = "cannedgoods.png"; break;
            case "cleaning": fname = "cleaningtools.png"; break;
            case "condiments": fname = "condiments.png"; break;
            case "health care": fname = "healthcare.png"; break;
            case "snacks": fname = "snacks.png"; break;
            default: fname = "default.png"; break;
        }
        try {
            return new ImageView(new Image(getClass().getResourceAsStream("/application/images/" + fname)));
        } catch (Exception ex) {
            return new ImageView();
        }
    }

    public Scene getScene() { return scene; }
}
