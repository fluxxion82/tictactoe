package com.hipmunk.tictactoe.model;

/**
 * Created by salbury on 5/4/15.
 */
public interface Player {
    public Move getMove(Board board);

    public Mark getPlayerMark();

    public void setPlayerMark(Mark mark);
}