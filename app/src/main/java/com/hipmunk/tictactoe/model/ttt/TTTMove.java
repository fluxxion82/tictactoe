package com.hipmunk.tictactoe.model.ttt;

import com.hipmunk.tictactoe.model.Move;

/**
 * Created by salbury on 5/4/15.
 */
public class TTTMove extends Move {
    public TTTMark mCurrentPlayerMark;

    public TTTMove(int xCoordinate, int yCoordinate, TTTMark currentPlayerMark) {
        mXCoordinate = xCoordinate;
        mYCoordinate = yCoordinate;
        mCurrentPlayerMark = currentPlayerMark;
    }
}
