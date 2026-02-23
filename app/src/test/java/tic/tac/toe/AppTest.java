package tic.tac.toe;

import org.junit.jupiter.api.Test;
import tic.tac.toe.GamingView.GameLogic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    @Test
    void detectsWinnerOnRows() {
        GameLogic game = new GameLogic();

        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X wins

        assertTrue(game.isGameOver());
        assertEquals('X', game.getWinner());
    }

    @Test
    void detectsDraw() {
        GameLogic game = new GameLogic();
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(0, 2); // X
        game.makeMove(1, 2); // O
        game.makeMove(1, 1); // X
        game.makeMove(2, 0); // O
        game.makeMove(1, 0); // X
        game.makeMove(2, 2); // O
        game.makeMove(2, 1); // X

        assertTrue(game.isGameOver());
        assertTrue(game.isDraw());
        assertNull(game.getWinner());
    }

    @Test
    void aiPicksWinningMoveFirst() {
        GameLogic game = new GameLogic();
        game.makeMove(2, 2); // X
        game.makeMove(0, 0); // O
        game.makeMove(1, 1); // X
        game.makeMove(0, 1); // O
        game.makeMove(2, 1); // X

        int[] bestMove = game.bestMoveFor('O', 'X');
        assertArrayEquals(new int[]{0, 2}, bestMove);
    }
}
