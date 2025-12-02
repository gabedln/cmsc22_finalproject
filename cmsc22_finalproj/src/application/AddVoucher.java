package application;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.Seller;
import Product.Voucher;

public class AddVoucher {
    private Scene addVoucher;

    public AddVoucher(Stage stage, Scene previous, Seller seller) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("add-voucher"); // background style
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
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                stage.setScene(previous);
            }
        });

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

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                try {
                    int discount = Integer.parseInt(discountField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    float min = Float.parseFloat(minimumField.getText());
                    float cap = Float.parseFloat(capField.getText());

                    Voucher voucher = new Voucher(seller, discount, quantity, cap, min);
                    seller.getVoucherList().add(voucher);

                    System.out.println("Voucher added successfully! Code: " + voucher.getVoucherCode());

                    // Clear fields after adding
                    discountField.clear();
                    quantityField.clear();
                    minimumField.clear();
                    capField.clear();
                    addButton.setDisable(true);

                    // Optionally return to previous scene
                    stage.setScene(previous);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric values for discount, quantity, minimum, and cap.");
                }
            }
        });

        // ---------------- Layout ----------------
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 90 40 20 65;"); // top right bottom left
        grid.setAlignment(Pos.CENTER);

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
