package application;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.Seller;
import product.Vouchers;

public class AddVoucher {
    private Scene addVoucher;

    public AddVoucher(Stage stage, Scene previous, Seller seller) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("add-voucher");
        Scene addVoucher = new Scene(root, 1024, 576);
        addVoucher.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.addVoucher = addVoucher;

        // ---------------- Fields ----------------
        TextField discountField = new TextField();
        discountField.setPromptText("discount (%)");
        discountField.getStyleClass().add("text-field");
        discountField.setMinWidth(275);

        TextField quantityField = new TextField();
        quantityField.setPromptText("quantity");
        quantityField.getStyleClass().add("text-field");
        quantityField.setMinWidth(275);

        TextField minimumField = new TextField();
        minimumField.setPromptText("minimum purchase");
        minimumField.getStyleClass().add("text-field");
        minimumField.setMinWidth(275);

        TextField capField = new TextField();
        capField.setPromptText("discount capped at");
        capField.getStyleClass().add("text-field");
        capField.setMinWidth(275);

        // ---------------- Buttons ----------------
        Button addButton = new Button("add voucher");
        addButton.setDisable(true);
        addButton.setMinWidth(275);
        addButton.getStyleClass().add("add-button");

        Button back = new Button("back");
        back.setMinWidth(275);
        back.getStyleClass().add("back-button");
        back.setOnAction(e -> stage.setScene(previous));

        // ---------------- Listener ----------------
        ChangeListener<String> textFieldListener = (obs, oldVal, newVal) -> {
            boolean allFilled = !discountField.getText().isEmpty()
                    && !quantityField.getText().isEmpty()
                    && !minimumField.getText().isEmpty()
                    && !capField.getText().isEmpty();
            addButton.setDisable(!allFilled);
        };

        discountField.textProperty().addListener(textFieldListener);
        quantityField.textProperty().addListener(textFieldListener);
        minimumField.textProperty().addListener(textFieldListener);
        capField.textProperty().addListener(textFieldListener);

        // ---------------- Add Voucher Action ----------------
        addButton.setOnAction(e -> {
            try {
                int discount = Integer.parseInt(discountField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                float min = Float.parseFloat(minimumField.getText());
                float cap = Float.parseFloat(capField.getText());

                Vouchers voucher = new Vouchers(seller, discount, quantity, cap, min);
                seller.getVoucherList().add(voucher);

                // Save data
                Main.saveData();

                // Confirmation popup
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Voucher created successfully!\nCode: " + voucher.getVoucherCode());
                alert.showAndWait();

                // Clear fields
                discountField.clear();
                quantityField.clear();
                minimumField.clear();
                capField.clear();
                addButton.setDisable(true);

                // Return to ViewVoucher
                ViewVoucher viewVoucher = new ViewVoucher(stage, previous, seller);
                stage.setScene(viewVoucher.getScene());

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter numeric values for discount, quantity, minimum, and cap.");
                alert.showAndWait();
            }
        });

        // ---------------- Layout ----------------
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 90 40 20 65;");
        grid.setAlignment(javafx.geometry.Pos.CENTER);

        grid.addRow(0, discountField);
        grid.addRow(1, quantityField);
        grid.addRow(2, minimumField);
        grid.addRow(3, capField);
        grid.addRow(4, addButton);
        grid.addRow(5, back);

        root.setCenter(grid);
    }

    public Scene getScene() {
        return this.addVoucher;
    }
}
