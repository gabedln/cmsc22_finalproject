package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.Seller;
import product.Vouchers;

public class ViewVoucher {
    private Scene viewVoucherScene;

    public ViewVoucher(Stage stage, Scene previous, Seller seller) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("add-voucher");

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.viewVoucherScene = scene;

        VBox voucherListBox = new VBox(30);
        voucherListBox.setAlignment(Pos.TOP_CENTER);
        voucherListBox.setStyle("-fx-padding: 40;");

        for (Vouchers voucher : seller.getVoucherList()) {
            Image ticketImage = new Image(getClass().getResourceAsStream("/application/images/voucher_ticket.png"));
            ImageView ticketView = new ImageView(ticketImage);
            ticketView.setFitWidth(600);
            ticketView.setFitHeight(180);

            Text codeText = new Text("Code: " + voucher.getVoucherCode());
            Text discountText = new Text("Discount: " + (int)(voucher.getDiscount() * 100) + "%");
            Text minText = new Text("Minimum: ₱" + voucher.getMinimum());
            Text capText = new Text("Cap: ₱" + voucher.getCap());

            codeText.getStyleClass().add("voucher-text");
            discountText.getStyleClass().add("voucher-text");
            minText.getStyleClass().add("voucher-text");
            capText.getStyleClass().add("voucher-text");

            VBox infoBox = new VBox(4, codeText, discountText, minText, capText);
            infoBox.setAlignment(Pos.TOP_LEFT);
            infoBox.setStyle("-fx-padding: 40 0 0 500;");

            // Remove button
            Button removeButton = new Button("remove");
            removeButton.getStyleClass().add("back-button");
            removeButton.setOnAction(e -> {
                seller.removeVoucher(voucher);
                Main.saveData();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Voucher Removed");
                alert.setHeaderText(null);
                alert.setContentText("Voucher code " + voucher.getVoucherCode() + " has been removed.");
                alert.showAndWait();

                // Refresh screen
                ViewVoucher refreshed = new ViewVoucher(stage, previous, seller);
                stage.setScene(refreshed.getScene());
            });

            VBox fullBox = new VBox(10, new StackPane(ticketView, infoBox), removeButton);
            fullBox.setAlignment(Pos.CENTER);

            voucherListBox.getChildren().add(fullBox);
        }

        // Add Voucher button
        Image addVoucherImage = new Image(getClass().getResourceAsStream("/application/images/add_voucher.png"));
        ImageView addVoucherView = new ImageView(addVoucherImage);
        addVoucherView.setFitWidth(200);
        addVoucherView.setFitHeight(80);

        Button addVoucherButton = new Button();
        addVoucherButton.setGraphic(addVoucherView);
        addVoucherButton.setStyle("-fx-background-color: transparent");
        addVoucherButton.getStyleClass().add("icon-button");
        addVoucherButton.setOnAction(e -> {
            AddVoucher addVoucher = new AddVoucher(stage, scene, seller);
            stage.setScene(addVoucher.getScene());
        });

        voucherListBox.getChildren().add(addVoucherButton);

        ScrollPane scrollPane = new ScrollPane(voucherListBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Button backButton = new Button("go back");
        backButton.getStyleClass().add("back-button-main");
        backButton.setOnAction(e -> stage.setScene(previous));

        VBox layout = new VBox(20, scrollPane, backButton);
        layout.setAlignment(Pos.CENTER);

        root.setCenter(layout);
    }

    public Scene getScene() { return this.viewVoucherScene; }
}
