package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			stage.setResizable(false);
			BorderPane root = new BorderPane(); // initial welcome screen, prompts user to login
			root.getStyleClass().add("root-border-pane");
			Scene scene = new Scene(root,1024,576);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("Girl, Boy, Bakla, Tomboy Store");
			
			TextField emailField = new TextField();
			emailField.setPromptText("email");
			emailField.getStyleClass().add("text-field");
			emailField.setMinWidth(255);
			
			PasswordField pwField = new PasswordField();
			pwField.setPromptText("password");
			emailField.getStyleClass().add("text-field");
			// GridPane sets password field and email field to be the same, appearance wise
			
			Button login = new Button("login");
			login.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent arg0) {
					System.out.println("Login Button Clicked!");
				}
			});
			
			login.setMinWidth(255);
			login.getStyleClass().add("login-button");		
			
			Label signupLabel = new Label("don't have an account?");
			signupLabel.getStyleClass().add("signup-label");
			
			Hyperlink signupLink = new Hyperlink ("Sign up here.");
			signupLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent arg0) {
					SignUp signup = new SignUp(stage, scene);
					Scene login = signup.getScene();
					stage.setScene(login);
					System.out.println("Sign-up Button Clicked!");
				}
			});
			signupLink.getStyleClass().add("signup-link");
			HBox signupPrompt = new HBox (4, signupLabel, signupLink);
			
			
			
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setStyle("-fx-padding: 200 20 20 20;"); // -fx-padding: top right bottom left
			grid.setAlignment(Pos.CENTER);
			
			grid.addRow(0, emailField);
			grid.addRow(1, pwField);
			grid.addRow(2, login);
			grid.addRow(3, signupPrompt);
			
			root.setCenter(grid);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
