package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import product.Vouchers;
import user.Buyer;
import user.Seller;
import javafx.scene.control.Label;
import java.util.List;

public class BuyerVoucherClaiming {
    private Scene buyerVoucherClaimingScene;

    public BuyerVoucherClaiming(Stage stage, Scene previous, Buyer buyer, List<Seller> sellers) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("sellerstorefront-background"); 
        // in application.css:
        // .sellerstorefront-background {
        //   -fx-background-image: url("/application/images/background_seller_storefront.png");
        //   -fx-background-size: cover;
        // }

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.buyerVoucherClaimingScene = scene;

        // Left column: available vouchers
        VBox availableBox = new VBox(30);
        availableBox.setAlignment(Pos.TOP_CENTER);
        availableBox.setStyle("-fx-padding: 40; -fx-background-color: transparent;");

        // Right column: claimed vouchers
        VBox claimedBox = new VBox(30);
        claimedBox.setAlignment(Pos.TOP_CENTER);
        claimedBox.setStyle("-fx-padding: 40; -fx-background-color: transparent;");

        Label availableTitle = new Label("Available Vouchers");
        availableTitle.getStyleClass().add("welcome-text");
        availableBox.getChildren().add(availableTitle);

        Label claimedTitle = new Label("Claimed Vouchers");
        claimedTitle.getStyleClass().add("welcome-text");
        claimedBox.getChildren().add(claimedTitle);

        // loop through all sellers’ vouchers
        for (Seller seller : sellers) {
            for (Vouchers voucher : seller.getVoucherList()) {
                VBox card = createVoucherCard(voucher, true);
                Button claimButton = new Button("claim");
                claimButton.getStyleClass().add("add-button");
                claimButton.setOnAction(e -> {
                    if (voucher.getQuantity() > 0) {
                        buyer.getVoucherList().add(voucher);
                        voucher.reduceQuantity();
                        Main.saveData();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Voucher Claimed");
                        alert.setHeaderText(null);
                        alert.setContentText("You claimed voucher code " + voucher.getVoucherCode());
                        alert.showAndWait();

                        // move card to claimedBox
                        claimedBox.getChildren().add(createVoucherCard(voucher, false));
                        availableBox.getChildren().remove(card);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Unavailable");
                        alert.setHeaderText(null);
                        alert.setContentText("This voucher is no longer available.");
                        alert.showAndWait();
                    }
                });
                card.getChildren().add(claimButton);
                availableBox.getChildren().add(card);
            }
        }

        // scrollable columns side by side
        ScrollPane availableScroll = new ScrollPane(availableBox);
        availableScroll.setFitToWidth(true);
        availableScroll.setStyle("-fx-background-color: transparent;");

        ScrollPane claimedScroll = new ScrollPane(claimedBox);
        claimedScroll.setFitToWidth(true);
        claimedScroll.setStyle("-fx-background-color: transparent;");

        HBox columns = new HBox(40, availableScroll, claimedScroll);
        columns.setAlignment(Pos.TOP_CENTER);

        // back button
        Button backButton = new Button("go back");
        backButton.getStyleClass().add("back-button-main");
        backButton.setOnAction(e -> stage.setScene(previous));

        VBox layout = new VBox(20, columns, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: transparent;");

        root.setCenter(layout);
    }

    private VBox createVoucherCard(Vouchers voucher, boolean showQuantity) {
        Image ticketImage = new Image(getClass().getResourceAsStream("/application/images/voucher_ticket.png"));
        ImageView ticketView = new ImageView(ticketImage);
        ticketView.setFitWidth(600);
        ticketView.setFitHeight(180);

        Text codeText = new Text("Code: " + voucher.getVoucherCode());
        Text discountText = new Text("Discount: " + voucher.getDiscount() + "%");
        Text minText = new Text("Minimum: ₱" + voucher.getMinimum());
        Text capText = new Text("Cap: ₱" + voucher.getCap());
        Text qtyText = new Text(showQuantity ? "Available: " + voucher.getQuantity() : "Remaining: " + voucher.getQuantity());

        codeText.getStyleClass().add("voucher-text");
        discountText.getStyleClass().add("voucher-text");
        minText.getStyleClass().add("voucher-text");
        capText.getStyleClass().add("voucher-text");
        qtyText.getStyleClass().add("voucher-text");

        VBox infoBox = new VBox(4, codeText, discountText, minText, capText, qtyText);
        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setStyle("-fx-padding: 40 0 0 500; -fx-background-color: transparent;");

        VBox card = new VBox(new StackPane(ticketView, infoBox));
        card.setAlignment(Pos.CENTER);
        card.setSpacing(10);
        card.setStyle("-fx-background-color: transparent;");

        return card;
    }

    public Scene getScene() { return this.buyerVoucherClaimingScene; }
}
