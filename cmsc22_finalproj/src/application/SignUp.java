package application;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.*;

public class SignUp {
	private User newUser;
	private Scene signup;
	
	public SignUp(Stage stage, Scene previous) {
		BorderPane root = new BorderPane();
		root.getStyleClass().add("signup-border-pane");
		Scene signup = new Scene(root, 1024, 576);
		signup.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.signup = signup;
		
		TextField displaynameField = new TextField();
		displaynameField.setPromptText("display name");
		displaynameField.getStyleClass().add("text-field");
		displaynameField.setMinWidth(255);
		
		TextField usernameField = new TextField();
		usernameField.setPromptText("username");
		usernameField.getStyleClass().add("text-field");
		usernameField.setMinWidth(255);
		
		TextField locationField = new TextField();
		locationField.setPromptText("location");
		locationField.getStyleClass().add("text-field");
		locationField.setMinWidth(255);
		
		PasswordField pwField = new PasswordField();
		pwField.setPromptText("password");
		locationField.getStyleClass().add("text-field");
		// GridPane sets password field and email field to be the same, appearance wise
		
		ComboBox<String> userField = new ComboBox<>();
		userField.setValue("buyer"); // sets initial value of our combo box to professor
		// adds different items / options to our combo box
		userField.getItems().add("buyer");
		userField.getItems().add("seller");
		userField.setMinWidth(255);
		userField.getStyleClass().add("combo-box");
		
		
		Button signupButton = new Button("sign-up");
		signupButton.setDisable(true);
		signupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(userField.getValue().equals("buyer")) {
					Main.addUser(new Buyer(displaynameField.getText(), usernameField.getText(), pwField.getText(), 0, locationField.getText()));
				} else {
					Main.addUser(new Seller(displaynameField.getText(), usernameField.getText(), pwField.getText(), 0, locationField.getText()));
				}
				Main.saveData();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setContentText("New user created!");
				alert.showAndWait();
				
				stage.setScene(previous);
			}
		});
		
		
		Button back = new Button("go back");
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(previous);
			}
		});
		
		ChangeListener<String> textfieldListener = (obs, oldVal, newVal) -> {
			boolean allFilled = !displaynameField.getText().isEmpty()
					&& !usernameField.getText().isEmpty()
					&& !locationField.getText().isEmpty()
					&& !pwField.getText().isEmpty();
			signupButton.setDisable(!allFilled);
		};
		
		displaynameField.textProperty().addListener(textfieldListener);
		usernameField.textProperty().addListener(textfieldListener);
		locationField.textProperty().addListener(textfieldListener);
		pwField.textProperty().addListener(textfieldListener);
		
		signupButton.setMinWidth(255);
		signupButton.getStyleClass().add("login-button");	
		back.setMinWidth(255);
		back.getStyleClass().add("back-button");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setStyle("-fx-padding: 170 40 20 65;"); // -fx-padding: top right bottom left
		grid.setAlignment(Pos.CENTER);
		
		grid.addRow(0, displaynameField);
		grid.addRow(1, usernameField);
		grid.addRow(2, locationField);
		grid.addRow(3, pwField);
		grid.addRow(4, userField);
		grid.addRow(5, signupButton);
		grid.addRow(6, back);
		
		root.setCenter(grid);
	}
	
	public User getNewUser() { return this.newUser; }
	public Scene getScene() { return this.signup; }
}
