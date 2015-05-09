package com.hipmunk.tictactoe.model;

/**
 * Created by salbury on 5/4/15.
 */
public abstract class Board {

    protected abstract void initializeBoard();

    public abstract Board put(Move move);
}
