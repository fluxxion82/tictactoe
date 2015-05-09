package com.hipmunk.tictactoe.model.ttt;

import com.hipmunk.tictactoe.model.Board;
import com.hipmunk.tictactoe.model.Human;
import com.hipmunk.tictactoe.model.Mark;
import com.hipmunk.tictactoe.model.Move;

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
