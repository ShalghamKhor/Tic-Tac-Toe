package tic.tac.toe.GamingView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tic.tac.toe.AppInfo;

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

        VBox centerBox = new VBox(20, button1, button2);
        centerBox.setAlignment(Pos.CENTER);

        Label versionLabel = new Label("Version " + AppInfo.getVersion());
        versionLabel.getStyleClass().add("version-label");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox footer = new HBox(spacer, versionLabel);
        footer.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(centerBox);
        root.setBottom(footer);

        scene = new Scene(root, 500, 300);
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
