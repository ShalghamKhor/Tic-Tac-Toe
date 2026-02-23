package tic.tac.toe.GamingView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class InitialScene {

    private final Scene scene;

    public InitialScene() {
        Button button1 = createButton(
            "Play against Computer",
            () -> ViewManager.switchToScene(new SecondaryScene(true).getScene())
        );
        Button button2 = createButton(
            "Play against another player",
            () -> ViewManager.switchToScene(new SecondaryScene(false).getScene())
        );

        VBox vbox = new VBox(20, button1, button2);

        vbox.setAlignment(Pos.CENTER);

        scene = new Scene(vbox, 500, 300);
        scene.getStylesheets().add(getClass().getResource("/Style/Style.css").toExternalForm());
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefSize(200, 100);
        button.setOnAction(e -> action.run());
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}
