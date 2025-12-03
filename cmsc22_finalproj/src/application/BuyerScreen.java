package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Buyer;

import java.util.ArrayList;

public class BuyerScreen {

    private Scene buyerScene;

    public BuyerScreen(Stage stage, Buyer buyer) {

        //load icons
        Image homeIcon        = loadImage("/application/images/home_icon.png");
        Image voucherIcon     = loadImage("/application/images/voucher_icon.png");
        Image wishlistIcon    = loadImage("/application/images/wishlist_icon.png");
        Image cartIcon        = loadImage("/application/images/cart_icon.png");
        Image transactionIcon = loadImage("/application/images/transaction_icon.png");

        //create buttons
        Button homeButton        = createIconButton(homeIcon, 45, 45);
        Button voucherButton     = createIconButton(voucherIcon, 45, 45);
        Button wishlistButton    = createIconButton(wishlistIcon, 45, 45);
        Button cartButton        = createIconButton(cartIcon, 45, 45);
        Button transactionButton = createIconButton(transactionIcon, 45, 45);

        
        //buttons
        homeButton.setOnAction(e -> System.out.println("Home clicked"));
        voucherButton.setOnAction(e -> System.out.println("Voucher clicked"));
        wishlistButton.setOnAction(e -> System.out.println("Wishlist clicked"));
        cartButton.setOnAction(e -> System.out.println("Cart clicked"));
        transactionButton.setOnAction(e -> System.out.println("Transaction clicked"));
        
        
        //navigation bar
        HBox topButtons = new HBox(20,
                homeButton,
                voucherButton,
                wishlistButton,
                cartButton,
                transactionButton
        );
        topButtons.setAlignment(Pos.CENTER_RIGHT);
        topButtons.setStyle("-fx-padding: 20;");

        VBox topSection = new VBox(topButtons);
        topSection.setAlignment(Pos.TOP_RIGHT);

        //root layout
        BorderPane root = new BorderPane();
        root.setTop(topSection);

        //product check
        ArrayList<String> products = new ArrayList<>();
        if (products.isEmpty()) {
            root.getStyleClass().add("buyerscreen");
        } else {
            root.getStyleClass().add("buyerscreen_with_products");
        }

        //bottom placeholder
        VBox placeholder = new VBox();
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setStyle("-fx-padding: 0 0 120 15;");
        root.setBottom(placeholder);


        //scene setup
        Scene scene = new Scene(root, 1024, 576);

        // Prevent CSS loading crash
        if (getClass().getResource("/application/application.css") != null) {
            scene.getStylesheets().add(
                    getClass().getResource("/application/application.css").toExternalForm()
            );
        } else {
            System.out.println("ERROR: Missing CSS → /application/application.css");
        }

        this.buyerScene = scene;
    }

    //buttons
    private Button createIconButton(Image img, double w, double h) {
        Button btn = new Button();
        // Add padding: top right bottom left
        btn.setStyle("-fx-background-color: transparent;" + "-fx-padding: 45 6 6 9;");  // 8px top, 12px right, 8px bottom, 12px left

        
        // "-fx-padding: 8px 12px 8px 12px;"
        if (img != null) {
            ImageView view = new ImageView(img);
            view.setFitWidth(w);
            view.setFitHeight(h);
            btn.setGraphic(view);
        }

        return btn;
    }

    //image loader
    private Image loadImage(String path) {
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            System.out.println("ERROR: Image not found → " + path);
            return null; //avoid crash
        }
        return new Image(stream);
    }

    // ---------------------------
    // GET SCENE
    // ---------------------------
    public Scene getScene() {
        return buyerScene;
    }
}