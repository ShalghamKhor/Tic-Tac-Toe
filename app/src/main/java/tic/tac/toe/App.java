package tic.tac.toe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tic.tac.toe.GamingView.View;
public class App extends Application{

    @Override
    public void start(Stage primaryStage) {
        View.init(primaryStage);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(View.getInitialScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
