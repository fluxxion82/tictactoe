package com.albury.tictactoe.model.ttt;

import com.albury.tictactoe.model.Board;
import com.albury.tictactoe.model.Human;
import com.albury.tictactoe.model.Mark;
import com.albury.tictactoe.model.Move;

/**
 * Created by salbury on 5/6/15.
 */
public class TTTHuman extends Human {
    private TTTMark mHumanMarker;

    @Override
    public Move getMove(Board board) {
        return null;
    }

    @Override
    public Mark getPlayerMark() {
        return mHumanMarker;
    }

    @Override
    public void setPlayerMark(Mark mark) {
        mHumanMarker = (TTTMark) mark;
    }
}
