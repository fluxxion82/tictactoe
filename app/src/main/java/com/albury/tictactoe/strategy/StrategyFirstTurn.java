package com.albury.tictactoe.strategy;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.model.ttt.TTTBoard;
import com.albury.tictactoe.model.ttt.TTTMark;
import com.albury.tictactoe.model.ttt.TTTMove;

import java.util.List;

/**
 * Created by salbury on 5/6/15.
 */
class StrategyFirstTurn extends Strategy {
    private static final String TAG = StrategyFirstTurn.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final int NUMBER_OF_CORNER_ON_BOARD = 2;

    StrategyFirstTurn(Strategy nextStrategy) {
        mNextStrategy = nextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;
        if (board.getNumberOfMove() < 2) {
            List<TTTMove> moveHistory = board.getMoveHistory();
            TTTMove centerSquarePosition = board.getCenterSquarePosition();
            if (moveHistory.contains(centerSquarePosition)) {
                position = getRandomSquareCorner(board.getCurrentBoard().length, playerValue);
            } else {
                position = board.getCenterSquarePosition();
            }
        }

        return position;
    }

    protected TTTMove getRandomSquareCorner(int boardSize, TTTMark playerValue) {
        int row = getRandomCornerNumber(boardSize);
        int col = getRandomCornerNumber(boardSize);
        return new TTTMove(row, col, playerValue);
    }

    private int getRandomCornerNumber(int boardSize) {
        int randomNumber = getRandomNumber();
        int number = randomNumber % NUMBER_OF_CORNER_ON_BOARD;
        if (number > 0) {
            number = boardSize - 1;
        }
        return number;
    }

    protected int getRandomNumber() {
        int randomNumber = (int) (Math.random() * 10);
        return randomNumber;
    }
}
