package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignUp {
	Scene signup;
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
		
		TextField emailField = new TextField();
		emailField.setPromptText("email");
		emailField.getStyleClass().add("text-field");
		emailField.setMinWidth(255);
		
		PasswordField pwField = new PasswordField();
		pwField.setPromptText("password");
		emailField.getStyleClass().add("text-field");
		// GridPane sets password field and email field to be the same, appearance wise
		
		ComboBox<String> userField = new ComboBox<>();
		userField.setValue("Buyer"); // sets initial value of our combo box to professor
		// adds different items / options to our combo box
		userField.getItems().add("Buyer");
		userField.getItems().add("Seller");
		userField.setMinWidth(255);
		userField.getStyleClass().add("combo-box");
		
		Button signupButton = new Button("sign-up");
		signupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Sign-Up Button Clicked!");
			}
		});
		
		Button back = new Button("go back");
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Go Back Button Clicked!");
				stage.setScene(previous);
			}
		});
		
		signupButton.setMinWidth(255);
		signupButton.getStyleClass().add("login-button");	
		back.setMinWidth(255);
		back.getStyleClass().add("back-button");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setStyle("-fx-padding: 60 40 20 20;"); // -fx-padding: top right bottom left
		grid.setAlignment(Pos.CENTER);
		
		grid.addRow(0, displaynameField);
		grid.addRow(1, usernameField);
		grid.addRow(2, emailField);
		grid.addRow(3, pwField);
		grid.addRow(4, userField);
		grid.addRow(5, signupButton);
		grid.addRow(6, back);
		
		root.setCenter(grid);
	}
	
	public Scene getScene() { return this.signup; }
}
