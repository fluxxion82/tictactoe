package com.albury.tictactoe.model.ttt;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.activity.game.GameState;
import com.albury.tictactoe.gamerules.TTTRules;
import com.albury.tictactoe.model.Board;
import com.albury.tictactoe.model.Move;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by salbury on 5/4/15.
 * <p/>
 * Tried to make the implementation of the board object immutable, and one could only derive other board objects via its put(TTTMove m) method
 */
public final class TTTBoard extends Board {
    private static final String TAG = TTTBoard.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private final TTTMark[][] mGameBoard = new TTTMark[3][3];
    private GameState mCurrentGameState = GameState.UNKNOWN;
    private GameState mCurrentPlayer = GameState.PLAYER1;

    private List<TTTMove> mMoveHistory;
    private final TTTMove mCenterPosition;

    public TTTBoard() {
        super();
        mMoveHistory = new LinkedList<TTTMove>();
        mCenterPosition = new TTTMove((int) 3 / 2, (int) 3 / 2, TTTMark.BLANK);
        initializeBoard();
    }

    // Set/Reset the board back to all empty values.
    @Override
    protected void initializeBoard() {
        // Loop through rows
        for (int i = 0; i < 3; i++) {
            // Loop through columns
            for (int j = 0; j < 3; j++) {
                mGameBoard[i][j] = TTTMark.BLANK;
            }
        }
    }

    @Override
    public Board put(Move move) {
        if (TTTRules.isMoveValid(this, move)) {
            mCurrentGameState = makeMove((TTTMove) move);
            mMoveHistory.add((TTTMove) move);
            if (mCurrentPlayer == GameState.PLAYER1) {
                mCurrentPlayer = GameState.PLAYER2;
            } else {
                mCurrentPlayer = GameState.PLAYER1;
            }

            return this;
        }

        return null;//probably not safe
    }

    private GameState makeMove(TTTMove move) {
        int row = move.mXCoordinate;
        int col = move.mYCoordinate;

        if (TTTRules.isMoveValid(this, move)) {

            if ((col >= 0) && (col < 3)) {
                if (mGameBoard[row][col] == TTTMark.BLANK) {
                    mGameBoard[row][col] = move.mCurrentPlayerMark;
                }
            }

            if (TTTRules.checkForWin(this, move)) {
                return GameState.WIN;
            } else if (TTTRules.isBoardFull(this)) {
                return GameState.DRAW;
            }

            return GameState.EMPTY;
        }
        return GameState.EMPTY;
    }

    public TTTMark[][] getCurrentBoard() {
        return mGameBoard;
    }

    /**
     * Get the win/ lose/ draw/ status
     *
     * @return gate status
     */
    public GameState getCurrentGameState() {
        return mCurrentGameState;
    }

    public List<TTTMove> getMoveHistory() {
        return mMoveHistory;
    }

    public int getNumberOfMove() {
        return mMoveHistory.size();
    }

    public TTTMove getCenterSquarePosition() {
        return mCenterPosition;
    }

    public boolean isEmpty() {
        return getNumberOfMove() == 0;
    }

    public TTTMove getLastMove() {
        TTTMove position = null;
        if (!isEmpty()) {
            position = mMoveHistory.get(mMoveHistory.size() - 1);
        }
        return position;
    }
}
