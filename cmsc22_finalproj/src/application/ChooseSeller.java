package application;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Buyer;
import user.Seller;

import java.util.List;

public class ChooseSeller {
    private Scene chooseSellerScene;

    public ChooseSeller(Stage stage, Scene previous, Buyer buyer, List<Seller> sellers) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("choose-seller"); // background style

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.chooseSellerScene = scene;

        // ---------------- Title ----------------
        Label title = new Label("CHOOSE SELLER");
        title.getStyleClass().add("welcome-text"); // reuse big font style

        // ---------------- Seller ComboBox ----------------
        ComboBox<Seller> sellerComboBox = new ComboBox<>();
        sellerComboBox.setPromptText("Select a seller");
        sellerComboBox.setMinWidth(275);
        sellerComboBox.getStyleClass().add("product-combo-box"); // same font as category dropdown
        sellerComboBox.getItems().addAll(sellers);

        sellerComboBox.setCellFactory(cb -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Seller seller, boolean empty) {
                super.updateItem(seller, empty);
                setText(empty || seller == null ? null : seller.getDisplayName() + " (@" + seller.getUsername() + ")");
            }
        });
        sellerComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Seller seller, boolean empty) {
                super.updateItem(seller, empty);
                setText(empty || seller == null ? null : seller.getDisplayName() + " (@" + seller.getUsername() + ")");
            }
        });
        
        /*

        // Redirect to storefront when a seller is chosen
        sellerComboBox.setOnAction(e -> {
            Seller selectedSeller = sellerComboBox.getValue();
            if (selectedSeller != null) {
                SellerStorefront storefront = new SellerStorefront(stage, scene, buyer, selectedSeller);
                stage.setScene(storefront.getScene());
            }
        });
        
        */

        // ---------------- Layout ----------------
        VBox layout = new VBox(20, title, sellerComboBox);
        layout.setStyle("-fx-padding: 150 40 20 40;"); // top spacing for title
        layout.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        root.setCenter(layout);
    }

    public Scene getScene() {
        return this.chooseSellerScene;
    }
}
