package tic.tac.toe.GamingView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SecondaryScene {
    private static final char HUMAN_PLAYER = 'X';
    private static final char COMPUTER_PLAYER = 'O';

    private final boolean playAgainstComputer;
    private final GameLogic gameLogic;
    private final Button[][] boardButtons;
    private final Label statusLabel;
    private final Scene scene;

    public SecondaryScene(boolean playAgainstComputer) {
        this.playAgainstComputer = playAgainstComputer;
        this.gameLogic = new GameLogic();
        this.boardButtons = new Button[3][3];
        this.statusLabel = new Label();
        this.statusLabel.getStyleClass().add("status-label");

        GridPane board = buildBoard();
        HBox controls = buildControls();

        VBox layout = new VBox(20, statusLabel, board, controls);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("game-layout");

        scene = new Scene(layout, 600, 650);
        scene.getStylesheets().add(getClass().getResource("/Style/Style.css").toExternalForm());
        updateStatusLabel();
    }

    private GridPane buildBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().add("board");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cellButton = new Button("");
                cellButton.setPrefSize(140, 140);
                cellButton.getStyleClass().add("cell-button");

                int currentRow = row;
                int currentCol = col;
                cellButton.setOnAction(e -> onCellClicked(currentRow, currentCol));
                boardButtons[row][col] = cellButton;
                gridPane.add(cellButton, col, row);
            }
        }
        return gridPane;
    }

    private HBox buildControls() {
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            gameLogic.reset();
            refreshBoard();
            updateStatusLabel();
            maybePlayComputerTurn();
        });

        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> ViewManager.switchToScene(ViewManager.getInitialScene()));

        HBox controls = new HBox(12, resetButton, backButton);
        controls.setAlignment(Pos.CENTER);
        return controls;
    }

    private void onCellClicked(int row, int col) {
        if (gameLogic.isGameOver()) {
            return;
        }
        if (playAgainstComputer && gameLogic.getCurrentPlayer() == COMPUTER_PLAYER) {
            return;
        }
        if (!gameLogic.makeMove(row, col)) {
            return;
        }
        refreshBoard();
        updateStatusLabel();
        maybePlayComputerTurn();
    }

    private void maybePlayComputerTurn() {
        if (!playAgainstComputer || gameLogic.isGameOver() || gameLogic.getCurrentPlayer() != COMPUTER_PLAYER) {
            return;
        }
        int[] move = gameLogic.bestMoveFor(COMPUTER_PLAYER, HUMAN_PLAYER);
        if (move != null) {
            gameLogic.makeMove(move[0], move[1]);
            refreshBoard();
            updateStatusLabel();
        }
    }

    private void refreshBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char value = gameLogic.getCell(row, col);
                boardButtons[row][col].setText(value == ' ' ? "" : String.valueOf(value));
                boardButtons[row][col].setDisable(value != ' ' || gameLogic.isGameOver());
            }
        }
    }

    private void updateStatusLabel() {
        if (gameLogic.getWinner() != null) {
            statusLabel.setText("Player " + gameLogic.getWinner() + " wins!");
            return;
        }
        if (gameLogic.isDraw()) {
            statusLabel.setText("It's a draw!");
            return;
        }
        if (playAgainstComputer && gameLogic.getCurrentPlayer() == COMPUTER_PLAYER) {
            statusLabel.setText("Computer's turn");
            return;
        }
        statusLabel.setText("Player " + gameLogic.getCurrentPlayer() + "'s turn");
    }

    public Scene getScene() {
        return scene;
    }
}
