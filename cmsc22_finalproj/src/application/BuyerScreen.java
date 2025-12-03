package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Buyer;
import user.User;


public class BuyerScreen {

    private Scene buyerScene;

    public BuyerScreen(Stage stage, Buyer buyer) {
        //load icons
		Image userIcon 		  = new Image(getClass().getResourceAsStream("/application/images/user_icon.png"));
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
		

        //create buttons
		Button userButton = new Button();
		userButton.setGraphic(usericon);
		userButton.setStyle("-fx-background-color: transparent; -fx-padding: 0 0 0 325" );
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
		
		Button wishlistButton = new Button();
		wishlistButton.setGraphic(wishlisticon);
		wishlistButton.setStyle("-fx-background-color: transparent;");
		
		Button cartButton = new Button();
		cartButton.setGraphic(carticon);
		cartButton.setStyle("-fx-background-color: transparent;");
		
		Button transactionButton = new Button();
		transactionButton.setGraphic(transactionicon);
		transactionButton.setStyle("-fx-background-color: transparent;");
		
		BorderPane root = new BorderPane();
		Scene buyerScene = new Scene(root, 1024, 576);
		buyerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.buyerScene = buyerScene;

        // HBox for aligned buttons
		HBox icons = new HBox(18, transactionButton, homeButton, voucherButton, wishlistButton, cartButton);
		VBox topIcons = new VBox(10, userButton, icons);
		topIcons.setStyle("-fx-padding: 5 0 0 630");
        
		root.getStyleClass().add("buyerscreen_initial");
		root.setTop(topIcons);
   
    }

 
    public Scene getScene() { return buyerScene; }
}