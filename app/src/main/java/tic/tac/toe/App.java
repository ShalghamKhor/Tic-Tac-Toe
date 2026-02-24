package tic.tac.toe;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tic.tac.toe.GamingView.ViewManager;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewManager.init(primaryStage);
        primaryStage.setTitle("Tic-Tac-Toe v" + AppInfo.getVersion());
        Image icon = new Image(getClass().getResourceAsStream("/images/icon-t.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(ViewManager.getInitialScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
