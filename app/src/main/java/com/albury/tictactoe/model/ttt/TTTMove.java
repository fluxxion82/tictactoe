package com.albury.tictactoe.model.ttt;

import com.albury.tictactoe.model.Move;

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
