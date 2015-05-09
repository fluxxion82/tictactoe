package com.hipmunk.tictactoe.activity.game;

/**
 * Created by salbury on 5/4/15.
 */
public enum GameState {
    UNKNOWN(-3),
    WIN(-2),
    DRAW(-1),
    EMPTY(0),
    PLAYER1(1),
    PLAYER2(2);

    private int mValue;

    private GameState(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static GameState fromInt(int i) {
        for (GameState s : values()) {
            if (s.getValue() == i) {
                return s;
            }
        }
        return EMPTY;
    }
}

