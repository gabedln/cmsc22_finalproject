package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import user.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
	private static ArrayList<User> users = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) {
		Image icon = new Image(getClass().getResourceAsStream("/application/images/logo.png"));
		
		try {
			Path loadPath = Paths.get("src/application/save/users.txt");
			if(Files.exists(loadPath)) {
				try {
					ObjectInputStream in = new ObjectInputStream(Files.newInputStream(loadPath));
					users = (ArrayList<User>) in.readObject();
				} catch(IOException e) {System.out.println("Loading failed!");}
			}
			
			stage.setResizable(false);
			
			BorderPane root = new BorderPane(); // initial welcome screen, prompts user to login
			root.getStyleClass().add("root-border-pane");
			Scene scene = new Scene(root,1024,576);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("Girl, Boy, Bakla, Tomboy Store");
			
			TextField usernameField = new TextField();
			usernameField.setPromptText("username");
			usernameField.getStyleClass().add("text-field");
			usernameField.setMinWidth(255);
			
			PasswordField pwField = new PasswordField();
			pwField.setPromptText("password");
			usernameField.getStyleClass().add("text-field");
			// GridPane sets password field and email field to be the same, appearance wise
			
			Button login = new Button("login");
			login.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent arg0) {
					String inputUsername = usernameField.getText();
					String inputPassword = pwField.getText();
					for(User user : users) {
						if(inputUsername.equals(user.getUsername()) && inputPassword.equals(user.getPassword())) {
							WelcomeScreen welcome = new WelcomeScreen(stage, scene, user, getSellers());
							stage.setScene(welcome.getScene());
							return;
						}
					}
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("No Account Found");
					error.setContentText("Incorrect username or password!");
					error.showAndWait();
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
					stage.setScene(signup.getScene());
				}
			});
			signupLink.getStyleClass().add("signup-link");
			HBox signupPrompt = new HBox (4, signupLabel, signupLink);
			
			
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setStyle("-fx-padding: 200 20 20 20;"); // -fx-padding: top right bottom left
			grid.setAlignment(Pos.CENTER);
			
			grid.addRow(0, usernameField);
			grid.addRow(1, pwField);
			grid.addRow(2, login);
			grid.addRow(3, signupPrompt);
			
			root.setCenter(grid);
			stage.setScene(scene);
			stage.getIcons().add(icon);
			stage.show();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void saveData() {
		Path savepath = Paths.get("src/application/save/users.txt");
		try {
			ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(savepath));
			out.writeObject((ArrayList<User>)users);
			out.close();
		} catch(IOException e) {}
	}
	
	public static ArrayList<Seller> getSellers(){
		ArrayList<Seller> sellers = new ArrayList<>();
		for(User u : users) {
			if(u instanceof Seller) {
				sellers.add((Seller)u);
			}
		}
		return sellers;
	}
	

	public static void addUser(User user) { users.add(user); }
	public static ArrayList<User> getUsers() { return users; }
	
	public static void main(String[] args) {
		launch(args);
	}
}

