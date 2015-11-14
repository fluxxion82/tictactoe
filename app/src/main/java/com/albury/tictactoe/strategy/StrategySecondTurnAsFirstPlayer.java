package com.albury.tictactoe.strategy;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.gamerules.TTTRules;
import com.albury.tictactoe.model.ttt.TTTBoard;
import com.albury.tictactoe.model.ttt.TTTMark;
import com.albury.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/7/15.
 */
class StrategySecondTurnAsFirstPlayer extends Strategy {
    private static final String TAG = StrategySecondTurnAsFirstPlayer.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    StrategySecondTurnAsFirstPlayer(Strategy nextStrategy) {
        mNextStrategy = nextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;
        int numberOfMoves = board.getNumberOfMove();
        TTTMove opponentPosition = board.getLastMove();

        if (numberOfMoves == 2) {
            position = getPositionAsFirstPlayer(board, opponentPosition);
        }

        return position;
    }

    private TTTMove getPositionAsFirstPlayer(TTTBoard board, TTTMove opponentPosition) {
        TTTMove position = null;
        if (TTTRules.isCornerPosition(opponentPosition)) {
            position = TTTRules.getOppositeCorner(opponentPosition);
        } else {
            position = getOpposingCornerForOuterMiddlePosition(opponentPosition, board);
        }
        return position;
    }

    private TTTMove getOpposingCornerForOuterMiddlePosition(TTTMove position, TTTBoard board) {
        int row = getCornerForOuterMiddlePosition(board, position.mXCoordinate, position.mYCoordinate);
        int col = getCornerForOuterMiddlePosition(board, position.mXCoordinate, position.mYCoordinate);
        return new TTTMove(row, col, TTTMark.BLANK);
    }

    private int getCornerForOuterMiddlePosition(TTTBoard board, int x, int y) {

        if (x == 3 / 2) {
            x = 0;
        } else {
            x = TTTRules.getOppositeCorner(x);
        }
        return x;
    }
}
