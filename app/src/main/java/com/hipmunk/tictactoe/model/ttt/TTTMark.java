package com.hipmunk.tictactoe.model.ttt;

import com.hipmunk.tictactoe.model.Mark;

/**
 * Created by salbury on 5/4/15.
 */
public enum TTTMark implements Mark {
    BLANK(0),
    X(1),
    O(2);

    private int mValue;

    private TTTMark(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static TTTMark fromInt(int i) {
        for (TTTMark s : values()) {
            if (s.getValue() == i) {
                return s;
            }
        }
        return BLANK;
    }
}
