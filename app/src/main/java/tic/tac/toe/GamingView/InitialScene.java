package tic.tac.toe.GamingView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import tic.tac.toe.GamingView.SecondaryScene;
import tic.tac.toe.GamingView.View;

public class InitialScene {

    private final Scene scene;

    public InitialScene() {
        Button button1 = createButton("Play against Computer", () -> View.switchToScene(new SecondaryScene("Scene 2").getScene()));
        Button button2 = createButton("Play against another player", () -> View.switchToScene(new SecondaryScene("Scene 3").getScene()));

        VBox vbox = new VBox(20, button1, button2);
        vbox.setAlignment(Pos.CENTER);

        scene = new Scene(vbox, 500, 300);
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
