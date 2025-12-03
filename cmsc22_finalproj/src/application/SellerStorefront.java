package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Seller;
import user.User;

public class SellerStorefront {

    private Scene sellerScene;

    public SellerStorefront(Stage stage, Scene main, Seller seller) {
        // User icon button
        Image userIcon = new Image(getClass().getResourceAsStream("/application/images/user_icon.png"));
        ImageView usericon = new ImageView(userIcon);
        usericon.setFitHeight(45);
        usericon.setFitWidth(45);

        Button userButton = new Button();
        userButton.setGraphic(usericon);
        userButton.setStyle("-fx-background-color: transparent; -fx-padding: 10 0 0 960;");

        BorderPane root = new BorderPane();
        Scene sellerScene = new Scene(root, 1024, 576);
        sellerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.sellerScene = sellerScene;

        userButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                UserInformation userInfo = new UserInformation(stage, sellerScene, (User)seller);
                stage.setScene(userInfo.getScene());
            }
        });

        // Check if seller has products
        if (seller.getProductList().isEmpty()) {
            // Show "Start Selling" button
            Button startSelling = new Button("start selling");
            startSelling.setMinWidth(450);
            startSelling.getStyleClass().add("startSelling-button");

            VBox button = new VBox(startSelling);
            button.setStyle("-fx-padding: 0 0 120 15;");
            button.setAlignment(Pos.CENTER);

            startSelling.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent arg0) {
                    // Go to AddProduct
                    AddProduct addProduct = new AddProduct(stage, sellerScene, seller);
                    stage.setScene(addProduct.getScene());
                }
            });

            root.getStyleClass().add("sellerscreen_initial");
            root.setBottom(button);
            root.setTop(userButton);
        } else {
            // Products exist - go directly to storefront
            SellerStorefront storefront = new SellerStorefront(stage, main, seller);
            stage.setScene(storefront.getScene());
            return;
        }
    }

    public Scene getScene() { 
        return this.sellerScene; 
    }
}
