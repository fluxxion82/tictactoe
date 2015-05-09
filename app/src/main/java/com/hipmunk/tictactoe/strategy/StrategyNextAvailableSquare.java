package com.hipmunk.tictactoe.strategy;

/**
 * Created by salbury on 5/6/15.
 */

import com.hipmunk.tictactoe.BuildConfig;
import com.hipmunk.tictactoe.model.ttt.TTTBoard;
import com.hipmunk.tictactoe.model.ttt.TTTMark;
import com.hipmunk.tictactoe.model.ttt.TTTMove;

class StrategyNextAvailableSquare extends Strategy {
    private static final String TAG = StrategyNextAvailableSquare.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    StrategyNextAvailableSquare(Strategy NextStrategy) {
        mNextStrategy = NextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;

        final int boardSize = board.getCurrentBoard().length;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                TTTMove pos = new TTTMove(row, col, playerValue);
                if (board.getCurrentBoard()[row][col] == TTTMark.BLANK) {
                    position = pos;
                    break;
                }
            }
        }
        return position;
    }

}