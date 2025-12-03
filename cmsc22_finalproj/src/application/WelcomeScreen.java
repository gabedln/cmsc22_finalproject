package application;

import user.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class WelcomeScreen {
    private Scene welcome;

    // Unified constructor
    public WelcomeScreen(Stage stage, Scene previous, User user, List<Seller> allSellers) {
        // Back button
        Button back = new Button("go back");
        back.setOnAction(e -> stage.setScene(previous));

        BorderPane root = new BorderPane();
        root.getStyleClass().add("welcome-border-pane");
        this.welcome = new Scene(root, 1024, 576);
        this.welcome.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        Text welcomeLabel;
        Button continueButton;

        if (user instanceof Buyer) {
            welcomeLabel = new Text("WELCOME, BUYER " + user.getDisplayName() + "!");
            continueButton = new Button("start shopping");
            continueButton.setOnAction(e -> {
                ChooseSeller chooseSeller = new ChooseSeller(stage, welcome, (Buyer) user, allSellers);
                stage.setScene(chooseSeller.getScene());
            });
        } else {
            welcomeLabel = new Text("WELCOME, SELLER " + user.getDisplayName() + "!");
            continueButton = new Button("start selling");
            continueButton.setOnAction(e -> {
                SellerScreen sellerScreen = new SellerScreen(stage, previous, (Seller) user);
                stage.setScene(sellerScreen.getScene());
            });
        }

        // Styling
        welcomeLabel.getStyleClass().add("welcome-text");
        continueButton.setMinWidth(350);
        continueButton.getStyleClass().add("continue-button");
        back.setMinWidth(350);
        back.getStyleClass().add("back-button-main");

        HBox buttons = new HBox(10, continueButton, back);
        buttons.setStyle("-fx-padding: 0 0 10 80");

        VBox show = new VBox(10, welcomeLabel, buttons);
        show.setStyle("-fx-padding: 400 40 20 65;");
        show.setAlignment(Pos.CENTER);

        Image aboutIcon = new Image(getClass().getResourceAsStream("/application/images/about_icon.png"));
        ImageView aboutIconView = new ImageView(aboutIcon);
        aboutIconView.setFitWidth(40);
        aboutIconView.setFitHeight(40);

        Button aboutButton = new Button();
        aboutButton.setGraphic(aboutIconView);
        aboutButton.getStyleClass().add("about-icon-button");
        aboutButton.setOnAction(e -> {
            About about = new About(stage, welcome);
            stage.setScene(about.getScene());
        });

        Image creditsIcon = new Image(getClass().getResourceAsStream("/application/images/credits_icon.png"));
        ImageView creditsIconView = new ImageView(creditsIcon);
        creditsIconView.setFitWidth(40);
        creditsIconView.setFitHeight(40);

        Button creditsButton = new Button();
        creditsButton.setGraphic(creditsIconView);
        creditsButton.getStyleClass().add("about-icon-button");
        creditsButton.setOnAction(e -> {
            ViewCredits credits = new ViewCredits(stage, welcome);
            stage.setScene(credits.getScene());
        });

        HBox topBar = new HBox(10, aboutButton, creditsButton);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setStyle("-fx-padding: 10;");

        root.setTop(topBar);
        root.setCenter(show);
    }

    public Scene getScene() {
        return this.welcome;
    }
}
