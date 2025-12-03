package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class About {
    private Scene aboutScene;

    public About(Stage stage, Scene previous) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("about-screen"); // background handled in CSS

        this.aboutScene = new Scene(root, 1024, 576);
        this.aboutScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

     // Title
        Text title = new Text("About the Project");
        title.getStyleClass().add("welcome-text"); // keep large header

        // Description
        Text description = new Text(
            "This application is a simple but fully functional online storefront developed as the final project for CMSC 22 by Mari Gabriel De Leon, Natasha Lois Montas, Mark Leo Abucay, and Avigail Ann A. Monsalud of CD-3L.\n\n" +
            "Built using JavaFX, the system showcases the integration of core Object-Oriented Programming concepts through an interactive and user-friendly interface.\n\n" +
            "It features two types of users—Sellers and Buyers—each with their own tools for managing products, vouchers, carts, and transaction histories.\n\n" +
            "Designed to be intuitive, organized, and visually appealing, the application demonstrates essential e-commerce functionalities while highlighting proper class design, code reusability, and dynamic GUI development."
        );
        description.getStyleClass().add("about-description"); // new smaller font style
        description.setWrappingWidth(800);


        // Back button uses existing "back-button-main" style
        Button backButton = new Button("go back");
        backButton.getStyleClass().add("back-button-main");
        backButton.setOnAction(e -> stage.setScene(previous));

        VBox layout = new VBox(20, title, description, backButton);
        layout.getStyleClass().add("about-layout");
        layout.setAlignment(Pos.CENTER);

        root.setCenter(layout);
    }

    public Scene getScene() {
        return this.aboutScene;
    }
}
