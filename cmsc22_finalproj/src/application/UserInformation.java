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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import user.*;

public class UserInformation {
    private Scene userInfoScene;

    public UserInformation(Stage stage, Scene previous, User user) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("user-info"); // uses user_information.png

        Scene userInfoScene = new Scene(root, 1024, 576);
        userInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.userInfoScene = userInfoScene;

        Label displayNameValue = new Label(user.getDisplayName());
        Label usernameValue = new Label("@" + user.getUsername());
        Label locationValue = new Label(user.getLocation());
        Label accountTypeValue;
        if(user instanceof Buyer) {
        	accountTypeValue = new Label("BUYER");
        } else {
        	accountTypeValue = new Label("SELLER");
        }
        Label balanceValue = new Label("₱" + user.getBalance());

        displayNameValue.getStyleClass().add("user-info-text");
        usernameValue.getStyleClass().add("user-info-text");
        locationValue.getStyleClass().add("user-info-text");
        accountTypeValue.getStyleClass().add("user-info-text");
        balanceValue.getStyleClass().add("user-info-text");

        Image editIcon = new Image(getClass().getResourceAsStream("/application/images/edit_icon.png"));
        ImageView editNameIcon = new ImageView(editIcon);
        editNameIcon.setFitHeight(45);
        editNameIcon.setFitWidth(70);

        ImageView editLocationIcon = new ImageView(editIcon);
        editLocationIcon.setFitHeight(45);
        editLocationIcon.setFitWidth(70);

        Button editNameButton = new Button();
        editNameButton.setGraphic(editNameIcon);
        editNameButton.getStyleClass().add("edit-icon-button");

        Button editLocationButton = new Button();
        editLocationButton.setGraphic(editLocationIcon);
        editLocationButton.getStyleClass().add("edit-icon-button");
        
        Button cashin = new Button("cash-in");
        cashin.getStyleClass().add("cash-in");
        cashin.setMinWidth(190);
        
        Button logout = new Button("log out");
        logout.getStyleClass().add("log-out");
        logout.setMinWidth(190);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setVgap(10);
        grid.setHgap(20);
        grid.setStyle("-fx-padding: 205 0 0 250;"); 

        grid.add(displayNameValue, 0, 0);
        grid.add(editNameButton, 1, 0);

        grid.add(usernameValue, 0, 1);

        grid.add(locationValue, 0, 2);
        grid.add(editLocationButton, 1, 2);

        grid.add(accountTypeValue, 0, 3);
        grid.add(balanceValue, 0, 4);

        Button back = new Button("back");
        back.getStyleClass().add("user-info-back-button");
        HBox backButton = new HBox(10, back, cashin, logout);
        backButton.setStyle("-fx-padding: 0 0 30 215");
        back.setOnAction(e -> stage.setScene(previous));
        back.setMinWidth(190);

        editNameButton.setOnAction(e -> {
            TextField nameField = new TextField();
            nameField.setPromptText("Enter new display name");
            nameField.getStyleClass().add("user-text-field");
            nameField.setMinWidth(350);

            Button confirm = new Button("confirm");
            confirm.getStyleClass().add("confirm-button");
            confirm.setOnAction(ev -> {
                user.setDisplayName(nameField.getText());
                Main.saveData();
                stage.setScene(new UserInformation(stage, previous, user).getScene());
            });

            HBox editPane = new HBox(5, nameField, confirm);
            editPane.setAlignment(Pos.CENTER_LEFT);
            editPane.setStyle("-fx-padding: 14 0 0 220;");
            root.setCenter(editPane);
        });
        
        cashin.setOnAction(e-> {
        	AddBalance balance = new AddBalance(stage, userInfoScene, (Buyer)user);
        	stage.setScene(balance.getScene());
        });

        editLocationButton.setOnAction(e -> {
            TextField locationField = new TextField();
            locationField.setPromptText("Enter new location");
            locationField.getStyleClass().add("user-text-field");
            locationField.setMinWidth(350);

            Button confirm = new Button("confirm");
            confirm.getStyleClass().add("confirm-button");
            confirm.setOnAction(ev -> {
                user.setLocation(locationField.getText());
                Main.saveData();
                stage.setScene(new UserInformation(stage, previous, user).getScene());
            });

            HBox editPane = new HBox(5, locationField, confirm);
            editPane.setAlignment(Pos.CENTER_LEFT);
            editPane.setStyle("-fx-padding: 210 0 0 220;");
            root.setCenter(editPane);
        });
        
        logout.setOnAction(e-> {
        	Main.saveData();
        	stage.close();
        });

        root.setCenter(grid);
        root.setBottom(backButton);
    }

    public Scene getScene() {
        return this.userInfoScene;
    }
}
