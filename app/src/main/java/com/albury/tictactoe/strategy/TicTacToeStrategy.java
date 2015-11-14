package com.albury.tictactoe.strategy;

import android.util.Log;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.model.ttt.TTTBoard;
import com.albury.tictactoe.model.ttt.TTTMark;

/**
 * Created by salbury on 5/4/15.
 * <p/>
 * Same note on Strategy.class, but this class did end up the way I first thought. As you can see, I was going for a strategy pattern, but then it morphed into more of a decorator pattern, and this class is
 * basically a strategy factory, but only picking one strategy by wrapping all the strategies into one.
 * <p/>
 * I could probably get this back to more of the Strategy pattern I wanted. Since this is a interview demo, I'll just note it.
 */
public class TicTacToeStrategy {
    private static final String TAG = TicTacToeStrategy.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private TTTBoard mCurrentBoard;
    protected TTTMark mPosition[][];


    public TicTacToeStrategy(TTTBoard currentBoard) {
        mCurrentBoard = currentBoard;
        mPosition = mCurrentBoard.getCurrentBoard();
    }

    public Strategy getStrategy(TTTMark currentPlayerMarker) {
        if (DEBUG) {
            Log.i(TAG, "getStrategy");
        }

        Strategy nextAvailableSquare = new StrategyNextAvailableSquare(null);
        Strategy thirdTurn = new StrategyThirdTurn(nextAvailableSquare);
        Strategy secondTurnAsSecondPlayer = new StrategySecondTurnAsSecondPlayer(thirdTurn);
        Strategy block = new StrategyBlock(secondTurnAsSecondPlayer);
        Strategy win = new StrategyWin(block);
        Strategy secondTurnAsFirstPlayer = new StrategySecondTurnAsFirstPlayer(win);
        Strategy firstTurn = new StrategyFirstTurn(secondTurnAsFirstPlayer);
        return firstTurn;
    }

}
