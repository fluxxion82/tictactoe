package com.albury.tictactoe.strategy;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.gamerules.TTTRules;
import com.albury.tictactoe.model.ttt.TTTBoard;
import com.albury.tictactoe.model.ttt.TTTMark;
import com.albury.tictactoe.model.ttt.TTTMove;

import java.util.List;

/**
 * Created by salbury on 5/6/15.
 */
class StrategyThirdTurn extends Strategy {
    private static final String TAG = StrategyThirdTurn.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    StrategyThirdTurn(Strategy nextStrategy) {
        mNextStrategy = nextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;
        if (isFirstPlayerThirdTurn(board)) {
            List<TTTMove> moveHistory = board.getMoveHistory();
            TTTMove opponentSecondMove = board.getLastMove();
            TTTMove opponentFirstMove = moveHistory.get(1);

            int row = 0;
            int col = 0;

            int diff = getAbsoluteDifference(opponentFirstMove.mXCoordinate, opponentSecondMove.mYCoordinate);
            if (diff == TTTRules.getBottomLeftCorner()) {
                row = opponentFirstMove.mXCoordinate;
                col = TTTRules.getOppositeCorner(opponentFirstMove.mYCoordinate);
            } else {
                row = TTTRules.getOppositeCorner(opponentFirstMove.mXCoordinate);
                col = opponentFirstMove.mYCoordinate;
            }

            position = new TTTMove(row, col, playerValue);
        }

        return position;
    }

    private int getAbsoluteDifference(int num1, int num2) {
        return Math.abs(num1 - num2);
    }

    private boolean isFirstPlayerThirdTurn(TTTBoard board) {
        return board.getNumberOfMove() == 4;
    }
}
