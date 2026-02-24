package tic.tac.toe.GamingView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SecondaryScene {
    private static final char HUMAN_PLAYER = 'X';
    private static final char COMPUTER_PLAYER = 'O';

    private final boolean playAgainstComputer;
    private final GameLogic gameLogic;
    private final Button[][] boardButtons;
    private final Label statusLabel;
    private final Label playerXScoreLabel;
    private final Label playerOScoreLabel;
    private final Label drawsScoreLabel;
    private final Scene scene;
    private int playerXScore;
    private int playerOScore;
    private int drawsScore;
    private boolean roundScoreApplied;
    private boolean roundEndAnimationRunning;

    public SecondaryScene(boolean playAgainstComputer) {
        this.playAgainstComputer = playAgainstComputer;
        this.gameLogic = new GameLogic();
        this.boardButtons = new Button[3][3];
        this.statusLabel = new Label();
        this.playerXScoreLabel = new Label();
        this.playerOScoreLabel = new Label();
        this.drawsScoreLabel = new Label();
        this.statusLabel.getStyleClass().add("status-label");
        this.playerXScoreLabel.getStyleClass().add("score-label");
        this.playerOScoreLabel.getStyleClass().add("score-label");
        this.drawsScoreLabel.getStyleClass().add("score-label");

        HBox scoreboard = buildScoreboard();
        GridPane board = buildBoard();
        HBox controls = buildControls();

        VBox layout = new VBox(20, statusLabel, scoreboard, board, controls);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("game-layout");

        scene = new Scene(layout, 600, 650);
        scene.getStylesheets().add(getClass().getResource("/Style/Style.css").toExternalForm());
        updateScoreboard();
        updateStatusLabel();
    }

    private HBox buildScoreboard() {
        HBox scoreboard = new HBox(16, playerXScoreLabel, playerOScoreLabel, drawsScoreLabel);
        scoreboard.setAlignment(Pos.CENTER);
        scoreboard.getStyleClass().add("scoreboard");
        return scoreboard;
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
            roundScoreApplied = false;
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
        if (roundEndAnimationRunning) {
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
        if (!playAgainstComputer
                || gameLogic.isGameOver()
                || gameLogic.getCurrentPlayer() != COMPUTER_PLAYER
                || roundEndAnimationRunning) {
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
            applyRoundScoreIfNeeded();
            statusLabel.setText("Player " + gameLogic.getWinner() + " wins!");
            startRoundEndAnimationAndReset();
            return;
        }
        if (gameLogic.isDraw()) {
            applyRoundScoreIfNeeded();
            statusLabel.setText("It's a draw!");
            startRoundEndAnimationAndReset();
            return;
        }
        if (playAgainstComputer && gameLogic.getCurrentPlayer() == COMPUTER_PLAYER) {
            statusLabel.setText("Computer's turn");
            return;
        }
        statusLabel.setText("Player " + gameLogic.getCurrentPlayer() + "'s turn");
    }

    private void applyRoundScoreIfNeeded() {
        if (roundScoreApplied) {
            return;
        }
        Character winner = gameLogic.getWinner();
        if (winner != null) {
            if (winner == 'X') {
                playerXScore++;
            } else {
                playerOScore++;
            }
        } else if (gameLogic.isDraw()) {
            drawsScore++;
        }
        roundScoreApplied = true;
        updateScoreboard();
    }

    private void updateScoreboard() {
        String secondPlayerLabel = playAgainstComputer ? "Computer (O)" : "O";
        playerXScoreLabel.setText("X: " + playerXScore);
        playerOScoreLabel.setText(secondPlayerLabel + ": " + playerOScore);
        drawsScoreLabel.setText("Draws: " + drawsScore);
    }

    private void startRoundEndAnimationAndReset() {
        if (roundEndAnimationRunning) {
            return;
        }

        roundEndAnimationRunning = true;
        setBoardInteractionDisabled(true);

        Timeline flashTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> statusLabel.setVisible(true)),
                new KeyFrame(Duration.millis(180), e -> statusLabel.setVisible(false)),
                new KeyFrame(Duration.millis(360), e -> statusLabel.setVisible(true)),
                new KeyFrame(Duration.millis(540), e -> statusLabel.setVisible(false)),
                new KeyFrame(Duration.millis(720), e -> statusLabel.setVisible(true)),
                new KeyFrame(Duration.millis(900), e -> {
                    gameLogic.reset();
                    roundScoreApplied = false;
                    roundEndAnimationRunning = false;
                    statusLabel.setVisible(true);
                    refreshBoard();
                    updateStatusLabel();
                    maybePlayComputerTurn();
                }));
        flashTimeline.play();
    }

    private void setBoardInteractionDisabled(boolean disabled) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardButtons[row][col]
                        .setDisable(disabled || gameLogic.getCell(row, col) != ' ' || gameLogic.isGameOver());
            }
        }
    }

    public Scene getScene() {
        return scene;
    }
}
