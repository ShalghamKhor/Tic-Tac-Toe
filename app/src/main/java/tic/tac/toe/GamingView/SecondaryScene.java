package tic.tac.toe.GamingView;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class SecondaryScene {

    private Scene scene;

    public SecondaryScene(String title) {
        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> View.switchToScene(View.getInitialScene()));

        StackPane layout = new StackPane();
        layout.getChildren().addAll(new Label(title), backButton);

        scene = new Scene(layout, 300, 200);
    }

    public Scene getScene() {
        return scene;
    }
}
