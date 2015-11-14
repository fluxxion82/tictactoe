package com.albury.tictactoe.model.ttt;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.model.Board;
import com.albury.tictactoe.model.Computer;
import com.albury.tictactoe.model.Mark;
import com.albury.tictactoe.model.Move;
import com.albury.tictactoe.strategy.Strategy;
import com.albury.tictactoe.strategy.TicTacToeStrategy;

/**
 * Created by salbury on 5/5/15.
 */
public class TTTComputer extends Computer {
    private static final String TAG = TTTComputer.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private Strategy mStrategy;
    private TTTMark mComputerMarker;

    public TTTComputer(TTTBoard board) {
        getStrategy(board);
    }

    @Override
    public Move getMove(Board board) {
        return mStrategy.getNextMove(mComputerMarker, (TTTBoard) board);
    }

    @Override
    public Mark getPlayerMark() {
        return mComputerMarker;
    }

    @Override
    public void setPlayerMark(Mark mark) {
        mComputerMarker = (TTTMark) mark;
    }

    private Strategy getStrategy(TTTBoard board) {
        mStrategy = new TicTacToeStrategy(board).getStrategy(mComputerMarker);
        return mStrategy;
    }

}