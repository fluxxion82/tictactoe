package com.hipmunk.tictactoe.strategy;

import android.util.Log;

import com.hipmunk.tictactoe.BuildConfig;
import com.hipmunk.tictactoe.gamerules.TTTRules;
import com.hipmunk.tictactoe.model.ttt.TTTBoard;
import com.hipmunk.tictactoe.model.ttt.TTTMark;
import com.hipmunk.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/5/15.
 * <p/>
 * Same note on TicTacToeStrategy, but this class did end up the way I first thought. As you can see, I was going for a strategy pattern, but then it morphed into more of a decorator pattern, and this class is
 * basically a strategy factory, but only picking one strategy by wrapping all the strategies into one.
 */
public abstract class Strategy {
    private static final String TAG = Strategy.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    protected Strategy mNextStrategy;

    public TTTMove getNextMove(TTTMark playerValue, TTTBoard board) {
        TTTMove position = getNextMoveFromChild(playerValue, board);
        if (shouldGetNextStrategyMove(position, board)) {
            position = mNextStrategy.getNextMove(playerValue, board);
        }

        if (DEBUG && mNextStrategy != null) {
            Log.i(TAG, "getNextMove, strategy=" + mNextStrategy.getClass().getSimpleName());
        }
        return position;
    }

    private boolean shouldGetNextStrategyMove(TTTMove position, TTTBoard board) {
        if (TTTRules.isMoveValid(board, position)) {
            return false;
        }

        if (mNextStrategy == null) {
            return false;
        }

        return true;
    }

    protected abstract TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board);

}
