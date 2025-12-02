package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import user.User;

public class UserInformation {
	private Scene userInfoScene;
	
	public UserInformation(Stage stage, Scene previous, User user) {
		BorderPane root = new BorderPane();
		Scene userInfoScene = new Scene(root, 1024, 576);
		userInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.userInfoScene = userInfoScene;
		root.getStyleClass().add("user-info");
		
		
	}
	
	public Scene getScene() { return this.userInfoScene; }
}
