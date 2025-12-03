package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Seller;
import user.User;

public class SellerScreen {

    private Scene sellerScene;

    public SellerScreen(Stage stage, Scene main, Seller seller) {
        // adds icons of buttons
        Image userIcon        = new Image(getClass().getResourceAsStream("/application/images/user_icon.png"));
        ImageView usericon = new ImageView(userIcon);
        usericon.setFitHeight(45);
        usericon.setFitWidth(45);
        
        Image homeIcon        = new Image(getClass().getResourceAsStream("/application/images/home_icon.png"));
        ImageView homeicon = new ImageView(homeIcon);
        homeicon.setFitHeight(45);
        homeicon.setFitWidth(45);
        
        Image voucherIcon     = new Image(getClass().getResourceAsStream("/application/images/voucher_icon.png"));
        ImageView vouchericon = new ImageView(voucherIcon);
        vouchericon.setFitHeight(45);
        vouchericon.setFitWidth(45);
        
        Image transactionIcon = new Image(getClass().getResourceAsStream("/application/images/transaction_icon.png"));
        ImageView transactionicon = new ImageView(transactionIcon);
        transactionicon.setFitHeight(45);
        transactionicon.setFitWidth(45);

        // adds buttons
        Button userButton = new Button();
        userButton.setGraphic(usericon);
        userButton.setStyle("-fx-background-color: transparent; -fx-padding: 5 0 0 175;");
        userButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                UserInformation userInfo = new UserInformation(stage, sellerScene, (User)seller);
                stage.setScene(userInfo.getScene());
            }
        });
        
        Button homeButton = new Button();
        homeButton.setGraphic(homeicon);
        homeButton.setStyle("-fx-background-color: transparent;");
        homeButton.setOnMouseClicked(e -> stage.setScene(main));
        
        Button voucherButton = new Button();
        voucherButton.setGraphic(vouchericon);
        voucherButton.setStyle("-fx-background-color: transparent;");
        voucherButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                ViewVoucher sellerVouchers = new ViewVoucher(stage, sellerScene, seller);
                stage.setScene(sellerVouchers.getScene());
            }
        });
        
        Button transactionButton = new Button();
        transactionButton.setGraphic(transactionicon);
        transactionButton.setStyle("-fx-background-color: transparent;");
      
        transactionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                // Pass null for now, or pass seller.getSalesHistory() if you have that list later
                TransactionScreen transScreen = new TransactionScreen(stage, sellerScene, seller, null);
                stage.setScene(transScreen.getScene());
            }
        });
        
        BorderPane root = new BorderPane();
        Scene sellerScene = new Scene(root, 1024, 576);
        sellerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.sellerScene = sellerScene;
        
        //HBox and VBox for buttons
        HBox icons = new HBox(18, homeButton, voucherButton, transactionButton);
        VBox topIcons = new VBox(10, userButton, icons);
        topIcons.setStyle("-fx-padding: 5 0 0 790");
        

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
                    AddProduct addProduct = new AddProduct(stage, sellerScene, seller);
                    stage.setScene(addProduct.getScene());
                }
            });

            root.getStyleClass().add("sellerscreen_initial");
            root.setBottom(button);
            root.setTop(topIcons);
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
