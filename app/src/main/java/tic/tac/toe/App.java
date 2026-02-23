package tic.tac.toe;

import javafx.application.Application;
import javafx.stage.Stage;
import tic.tac.toe.GamingView.ViewManager;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewManager.init(primaryStage);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(ViewManager.getInitialScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
