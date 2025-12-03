package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import product.Product;
import user.Buyer;

import java.util.ArrayList;

public class BuyerWishlist {

    private Scene scene;
    private VBox productBox;
    private final Buyer buyer;

    public BuyerWishlist(Stage stage, Scene previous, Buyer buyer) {
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

        // scroll content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
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

        refreshWishlist(stage); // populate wishlist
        root.setCenter(scrollPane);
        root.setBottom(bottomPadding);

        // scene + css
        scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    }

    private void refreshWishlist(Stage stage) {
        productBox.getChildren().clear();

        ArrayList<Product> wishlist = buyer.getWishlist();

        Label title = new Label("Your Wishlist");
        title.getStyleClass().add("welcome-text");
        productBox.getChildren().add(title);

        if (wishlist.isEmpty()) {
            Label empty = new Label("Your wishlist is empty.");
            empty.getStyleClass().add("product-details");
            productBox.getChildren().add(empty);
            return;
        }

        for (Product p : wishlist) {
            productBox.getChildren().add(createWishlistCard(stage, p));
        }
    }

    private HBox createWishlistCard(Stage stage, Product product) {
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
                " | Stock: " + product.getStock() +
                " | " + product.getCategory());
        detailsLabel.getStyleClass().add("product-details");
        infoBox.getChildren().addAll(nameLabel, detailsLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

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
