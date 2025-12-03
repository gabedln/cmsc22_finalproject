package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.User;

public class UserInformation {
    private Scene userInfoScene;

    public UserInformation(Stage stage, Scene previous, User user) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("user-info"); // uses user_information.png

        Scene userInfoScene = new Scene(root, 1024, 576);
        userInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.userInfoScene = userInfoScene;

        // ---------------- Values Only ----------------
        Label displayNameValue = new Label(user.getDisplayName());
        Label usernameValue = new Label("@" + user.getUsername());
        Label locationValue = new Label(user.getLocation());
        Label accountTypeValue = new Label(user.getAccountType());
        Label balanceValue = new Label("₱" + user.getBalance());

        displayNameValue.getStyleClass().add("info-label");
        usernameValue.getStyleClass().add("info-label");
        locationValue.getStyleClass().add("info-label");
        accountTypeValue.getStyleClass().add("info-label");
        balanceValue.getStyleClass().add("info-label");

        // ---------------- Edit Icons ----------------
        Image editIcon = new Image(getClass().getResourceAsStream("/application/images/edit_icon.png"));
        ImageView editNameIcon = new ImageView(editIcon);
        editNameIcon.setFitHeight(20);
        editNameIcon.setFitWidth(20);

        ImageView editLocationIcon = new ImageView(editIcon);
        editLocationIcon.setFitHeight(20);
        editLocationIcon.setFitWidth(20);

        Button editNameButton = new Button();
        editNameButton.setGraphic(editNameIcon);
        editNameButton.getStyleClass().add("edit-icon-button");

        Button editLocationButton = new Button();
        editLocationButton.setGraphic(editLocationIcon);
        editLocationButton.getStyleClass().add("edit-icon-button");

        // ---------------- Edit Actions ----------------
        editNameButton.setOnAction(e -> {
            TextField nameField = new TextField();
            nameField.setPromptText("Enter new display name");
            nameField.getStyleClass().add("text-field");

            Button confirm = new Button("confirm");
            confirm.getStyleClass().add("add-button");
            confirm.setOnAction(ev -> {
                user.setDisplayName(nameField.getText());
                stage.setScene(new UserInformation(stage, previous, user).getScene());
            });

            GridPane editPane = new GridPane();
            editPane.setAlignment(Pos.CENTER_LEFT);
            editPane.setVgap(10);
            editPane.setStyle("-fx-padding: 100 0 0 100;");
            editPane.add(nameField, 0, 0);
            editPane.add(confirm, 0, 1);
            root.setCenter(editPane);
        });

        editLocationButton.setOnAction(e -> {
            TextField locationField = new TextField();
            locationField.setPromptText("Enter new location");
            locationField.getStyleClass().add("text-field");

            Button confirm = new Button("confirm");
            confirm.getStyleClass().add("add-button");
            confirm.setOnAction(ev -> {
                user.setLocation(locationField.getText());
                stage.setScene(new UserInformation(stage, previous, user).getScene());
            });

            GridPane editPane = new GridPane();
            editPane.setAlignment(Pos.CENTER_LEFT);
            editPane.setVgap(10);
            editPane.setStyle("-fx-padding: 100 0 0 100;");
            editPane.add(locationField, 0, 0);
            editPane.add(confirm, 0, 1);
            root.setCenter(editPane);
        });

        // ---------------- Layout ----------------
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setVgap(15);
        grid.setHgap(20);
        grid.setStyle("-fx-padding: 100 0 0 400;"); // aligns values with background labels

        grid.add(displayNameValue, 0, 0);
        grid.add(editNameButton, 1, 0);

        grid.add(usernameValue, 0, 1);

        grid.add(locationValue, 0, 2);
        grid.add(editLocationButton, 1, 2);

        grid.add(accountTypeValue, 0, 3);
        grid.add(balanceValue, 0, 4);

        Button back = new Button("back");
        back.getStyleClass().add("back-button");
        back.setOnAction(e -> stage.setScene(previous));
        grid.add(back, 0, 5);

        root.setCenter(grid);
    }

    public Scene getScene() {
        return this.userInfoScene;
    }
}
