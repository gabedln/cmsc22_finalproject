package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import user.Buyer;
import user.User;
import user.Seller;

import java.util.ArrayList;
import java.util.List;

public class BuyerScreen {

    private Scene buyerScene;

    // Constructor with sellers list (full functionality)
    public BuyerScreen(Stage stage, Buyer buyer, List<Seller> sellers) {

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

        Image wishlistIcon    = new Image(getClass().getResourceAsStream("/application/images/wishlist_icon.png"));
        ImageView wishlisticon = new ImageView(wishlistIcon);
        wishlisticon.setFitHeight(45);
        wishlisticon.setFitWidth(45);

        Image cartIcon        = new Image(getClass().getResourceAsStream("/application/images/cart_icon.png"));
        ImageView carticon = new ImageView(cartIcon);
        carticon.setFitHeight(45);
        carticon.setFitWidth(45);

        Image transactionIcon = new Image(getClass().getResourceAsStream("/application/images/transaction_icon.png"));
        ImageView transactionicon = new ImageView(transactionIcon);
        transactionicon.setFitHeight(45);
        transactionicon.setFitWidth(45);

        // User Button
        Button userButton = new Button();
        userButton.setGraphic(usericon);
        userButton.setStyle("-fx-background-color: transparent; -fx-padding: 0 0 0 325;" );
        userButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                UserInformation userInfo = new UserInformation(stage, buyerScene, (User)buyer);
                stage.setScene(userInfo.getScene());
            }
        });

        Button homeButton = new Button();
        homeButton.setGraphic(homeicon);
        homeButton.setStyle("-fx-background-color: transparent;");

        Button voucherButton = new Button();
        voucherButton.setGraphic(vouchericon);
        voucherButton.setStyle("-fx-background-color: transparent;");
        voucherButton.setOnMouseClicked(e -> {
            BuyerVoucherClaiming voucherScreen = new BuyerVoucherClaiming(stage, buyerScene, buyer, sellers);
            stage.setScene(voucherScreen.getScene());
        });

        Button wishlistButton = new Button();
        wishlistButton.setGraphic(wishlisticon);
        wishlistButton.setStyle("-fx-background-color: transparent;");
        wishlistButton.setOnMouseClicked(e -> {
            BuyerWishlist wishlistScreen = new BuyerWishlist(stage, buyerScene, buyer);
            stage.setScene(wishlistScreen.getScene());
        });

        Button cartButton = new Button();
        cartButton.setGraphic(carticon);
        cartButton.setStyle("-fx-background-color: transparent;");
        cartButton.setOnMouseClicked(e -> {
            BuyerCart cartScreen = new BuyerCart(stage, buyerScene, buyer);
            stage.setScene(cartScreen.getScene());
        });

        Button transactionButton = new Button();
        transactionButton.setGraphic(transactionicon);
        transactionButton.setStyle("-fx-background-color: transparent;");
        transactionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                TransactionScreen transScreen = new TransactionScreen(stage, buyerScene, buyer, buyer.getTransactions());
                stage.setScene(transScreen.getScene());
            }
        });

        BorderPane root = new BorderPane();

        // set background image
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/application/images/background_seller_storefront.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(bgImage));

        Scene buyerScene = new Scene(root, 1024, 576);
        buyerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.buyerScene = buyerScene;

        HBox icons = new HBox(18, transactionButton, homeButton, voucherButton, wishlistButton, cartButton);
        VBox topIcons = new VBox(10, userButton, icons);
        topIcons.setStyle("-fx-padding: 5 0 0 630;");

        root.getStyleClass().add("buyerscreen_initial");
        root.setTop(topIcons);
    }

    // Overloaded constructor without sellers (for WelcomeScreen compatibility)
    public BuyerScreen(Stage stage, Buyer buyer) {
        this(stage, buyer, new ArrayList<>()); // empty sellers list for now
    }

    public Scene getScene() { return buyerScene; }
}
