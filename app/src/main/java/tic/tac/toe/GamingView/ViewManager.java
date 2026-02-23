package tic.tac.toe.GamingView;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tic.tac.toe.GamingView.InitialScene;

public class ViewManager {

    private static Stage primaryStage;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static Scene getInitialScene() {
        return new InitialScene().getScene();
    }

    public static void switchToScene(Scene scene) {
        primaryStage.setScene(scene);
    }
}

