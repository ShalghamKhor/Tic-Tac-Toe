package tic.tac.toe.GamingView;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class View {

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
