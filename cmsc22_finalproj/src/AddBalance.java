package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import user.Buyer;

public class AddBalance {
    private Scene addBalanceScene;

    public AddBalance(Stage stage, Scene previous, Buyer buyer) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("buyerscreen_initial");

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.addBalanceScene = scene;

        TextField amountField = new TextField();
        amountField.setPromptText("amount");
        amountField.getStyleClass().add("text-field");
        amountField.setMinWidth(275);

        ComboBox<String> methodBox = new ComboBox<>();
        methodBox.getItems().addAll("GCash", "PayMaya", "BDO", "CIMB");
        methodBox.setPromptText("Online Banking");
        methodBox.getStyleClass().add("combo-box");
        methodBox.setMinWidth(275);

        Button applyButton = new Button("apply changes");
        applyButton.setMinWidth(275);
        applyButton.getStyleClass().add("add-button");

        Button backButton = new Button("back");
        backButton.setMinWidth(275);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> stage.setScene(previous));

        //sets balance of buyer
        applyButton.setOnAction(e -> {
            try {
                float amount = Float.parseFloat(amountField.getText());
                buyer.setBalance(buyer.getBalance() + amount);
                Main.saveData();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Balance Updated");
                alert.setHeaderText(null);
                alert.setContentText("₱" + amount + " has been added to your balance.");
                alert.showAndWait();

                amountField.clear();
                methodBox.getSelectionModel().clearSelection();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid numeric amount.");
                alert.showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 90 40 20 65;");
        grid.setAlignment(Pos.CENTER);

        grid.addRow(0, amountField);
        grid.addRow(1, methodBox);
        grid.addRow(2, applyButton);
        grid.addRow(3, backButton);

        root.setCenter(grid);
    }

    public Scene getScene() {
        return this.addBalanceScene;
    }
}







ViewCredits

package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewCredits {
    private Scene creditsScene;

    public ViewCredits(Stage stage, Scene previous) {
        BorderPane root = new BorderPane();

        // Fullscreen background image
        Image creditsImage = new Image(getClass().getResourceAsStream("/application/images/credits.png"));
        ImageView creditsView = new ImageView(creditsImage);
        creditsView.setFitWidth(1024);
        creditsView.setFitHeight(576);
        creditsView.setPreserveRatio(false); // stretch to cover entire screen

        root.setCenter(creditsView);

        // Back button (small, top-left corner)
        Button backButton = new Button("back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> stage.setScene(previous));

        HBox topBar = new HBox(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-padding: 10;");

        root.setTop(topBar);

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.creditsScene = scene;
    }

    public Scene getScene() {
        return this.creditsScene;
    }
}




