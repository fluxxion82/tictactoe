package com.hipmunk.tictactoe.model.ttt;

import com.hipmunk.tictactoe.BuildConfig;
import com.hipmunk.tictactoe.model.Board;
import com.hipmunk.tictactoe.model.Computer;
import com.hipmunk.tictactoe.model.Mark;
import com.hipmunk.tictactoe.model.Move;
import com.hipmunk.tictactoe.strategy.Strategy;
import com.hipmunk.tictactoe.strategy.TicTacToeStrategy;

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