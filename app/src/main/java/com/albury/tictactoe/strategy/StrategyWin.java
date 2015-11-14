package com.albury.tictactoe.strategy;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.gamerules.TTTRules;
import com.albury.tictactoe.model.ttt.TTTBoard;
import com.albury.tictactoe.model.ttt.TTTMark;
import com.albury.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/6/15.
 */
class StrategyWin extends Strategy {
    private static final String TAG = StrategyWin.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    protected static final int INVALID_POSITION = -1;

    StrategyWin(Strategy nextStrategy) {
        mNextStrategy = nextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMove position = getDiagonalWinPosition(playerValue, board);
        if (position == null) {
            position = getVerticalWinPosition(playerValue, board);
            if (position == null) {
                position = getHorizontalWinPosition(playerValue, board);
            }
        }
        return position;
    }

    protected TTTMove getHorizontalWinPosition(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;
        for (int i = 0; i < board.getCurrentBoard().length; i++) {
            TTTMark[] values = TTTRules.getRowValues(board, i);
            int col = getWinningPosition(playerValue, values);
            if (col != INVALID_POSITION) {
                position = new TTTMove(i, col, playerValue);
                break;
            }
        }
        return position;
    }

    protected TTTMove getVerticalWinPosition(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;
        for (int i = 0; i < board.getCurrentBoard().length; i++) {
            TTTMark[] values = TTTRules.getColumnValues(board, i);
            int row = getWinningPosition(playerValue, values);
            if (row != INVALID_POSITION) {
                position = new TTTMove(row, i, playerValue);
                break;
            }
        }
        return position;
    }

    private TTTMove getDiagonalWinPosition(TTTMark playerValue, TTTBoard board) {
        TTTMove position = getLeftDiagonalWinPosition(playerValue, board);
        if (position == null) {
            position = getRightDiagonalWinPosition(playerValue, board);
        }
        return position;
    }

    protected TTTMove getRightDiagonalWinPosition(TTTMark playerValue, TTTBoard board) {
        TTTMark[] values = TTTRules.getRightDiagonalValues(board);
        int position = getWinningPosition(playerValue, values);
        TTTMove pos = null;
        if (position != INVALID_POSITION) {
            pos = new TTTMove(position, board.getCurrentBoard().length - 1 - position, playerValue);
        }
        return pos;
    }

    protected TTTMove getLeftDiagonalWinPosition(TTTMark playerValue, TTTBoard board) {
        TTTMark[] values = TTTRules.getLeftDiagonalValues(board);
        int position = getWinningPosition(playerValue, values);
        TTTMove pos = null;
        if (position != INVALID_POSITION) {
            pos = new TTTMove(position, position, playerValue);
        }
        return pos;
    }

    protected int getWinningPosition(TTTMark playerValue, TTTMark[] values) {
        int position = INVALID_POSITION;
        int playerValueCount = 0;
        for (int i = 0; i < values.length; i++) {
            TTTMark value = values[i];
            if (value == TTTMark.BLANK) {
                position = i;
            } else if (value.equals(playerValue)) {
                playerValueCount++;
            } else {
                break;
            }
        }

        if (!hasOneEmptyValue(playerValueCount, values.length)) {
            position = INVALID_POSITION;
        }

        return position;
    }

    private boolean hasOneEmptyValue(int playerValueCount, int numOfValues) {
        return playerValueCount == (numOfValues - 1);
    }

}
