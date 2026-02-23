package tic.tac.toe.GamingView;

public class GameLogic {
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private Character winner;
    private boolean gameOver;

    public GameLogic() {
        reset();
    }

    public void reset() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
            }
        }
        currentPlayer = 'X';
        winner = null;
        gameOver = false;
    }

    public boolean makeMove(int row, int col) {
        if (gameOver || board[row][col] != ' ') {
            return false;
        }
        board[row][col] = currentPlayer;
        updateGameState();
        if (!gameOver) {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        return true;
    }

    public int[] bestMoveFor(char aiPlayer, char humanPlayer) {
        int[] winningMove = findImmediateMove(aiPlayer);
        if (winningMove != null) {
            return winningMove;
        }

        int[] blockMove = findImmediateMove(humanPlayer);
        if (blockMove != null) {
            return blockMove;
        }

        if (board[1][1] == ' ') {
            return new int[]{1, 1};
        }

        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] corner : corners) {
            if (board[corner[0]][corner[1]] == ' ') {
                return corner;
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private int[] findImmediateMove(char player) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] != ' ') {
                    continue;
                }
                board[row][col] = player;
                boolean wins = hasWinner(player);
                board[row][col] = ' ';
                if (wins) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private void updateGameState() {
        if (hasWinner('X')) {
            winner = 'X';
            gameOver = true;
            return;
        }
        if (hasWinner('O')) {
            winner = 'O';
            gameOver = true;
            return;
        }
        if (isBoardFull()) {
            gameOver = true;
        }
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasWinner(char player) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            || (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Character getWinner() {
        return winner;
    }

    public boolean isDraw() {
        return gameOver && winner == null;
    }
}
