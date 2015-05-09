package com.hipmunk.tictactoe.presenter;

import android.util.Log;

import com.hipmunk.tictactoe.BuildConfig;
import com.hipmunk.tictactoe.R;
import com.hipmunk.tictactoe.activity.game.GameState;
import com.hipmunk.tictactoe.activity.game.TicTacToeView;
import com.hipmunk.tictactoe.model.Computer;
import com.hipmunk.tictactoe.model.Human;
import com.hipmunk.tictactoe.model.ttt.TTTBoard;
import com.hipmunk.tictactoe.model.ttt.TTTComputer;
import com.hipmunk.tictactoe.model.ttt.TTTHuman;
import com.hipmunk.tictactoe.model.ttt.TTTMark;
import com.hipmunk.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/4/15.
 */
public class TTTPresenterImpl implements TTTPresenter {
    private static final String TAG = TTTPresenterImpl.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private TTTBoard mGameBoard;
    private Computer mComputer;
    private Human mHuman;

    private GameState mHumanPlayer;
    private GameState mComputerPlayer;
    private GameState mCurrentTurn;

    private TicTacToeView mView;


    public TTTPresenterImpl(TicTacToeView view) {
        mView = view;
        mGameBoard = new TTTBoard();
        mComputer = new TTTComputer(mGameBoard);
        mHuman = new TTTHuman();
    }

    @Override
    public void onResume() {
        GameState player = mView.getCurrentPlayer();
        GameState gameState = mGameBoard.getCurrentGameState();
        if (gameState == GameState.UNKNOWN) {
            newGame();
            if (player == mComputerPlayer) {
                mCurrentTurn = mComputerPlayer;
                makeComputerMove();
            } else {
                mCurrentTurn = mHumanPlayer;
                makePlayerMove();
            }
        } else if (gameState == GameState.WIN || gameState == GameState.DRAW) {
            displayeGameResult(gameState);
        }

    }

    @Override
    public void setPlayerHuman(GameState player) {
        mHumanPlayer = player;
        if (mHumanPlayer == GameState.PLAYER1) {
            mCurrentTurn = mHumanPlayer;
            mComputer.setPlayerMark(TTTMark.X);
        } else {
            mHuman.setPlayerMark(TTTMark.O);
        }
    }

    @Override
    public void setPlayerComputer(GameState player) {
        mComputerPlayer = player;
        if (mComputerPlayer == GameState.PLAYER1) {
            mCurrentTurn = mComputerPlayer;
            mComputer.setPlayerMark(TTTMark.X);
        } else {
            mComputer.setPlayerMark(TTTMark.O);
        }
    }

    @Override
    public void clickNextTurnButton(GameState currentPlayer, int xCoord, int yCoord) {
        onCellSelected(xCoord, yCoord);

        GameState gameState = mGameBoard.getCurrentGameState();

        if (gameState == GameState.WIN || gameState == GameState.DRAW) {
            setGameState(gameState);
            mView.playMove(xCoord, yCoord);
            mView.stopBlink();

            displayeGameResult(gameState);
        } else {
            mView.playMove(xCoord, yCoord);
            mView.stopBlink();
            finishTurn();

        }
    }

    @Override
    public void newGame() {
        mGameBoard = new TTTBoard();
        mComputer = new TTTComputer(mGameBoard);
        mHuman = new TTTHuman();

        setPlayerHuman(mHumanPlayer);
        setPlayerComputer(mComputerPlayer);

        if (mHumanPlayer == GameState.PLAYER1) {
            makePlayerMove();
        } else {
            makeComputerMove();
        }
    }

    private void finishTurn() {
        if (DEBUG) {
            Log.i(TAG, "finishTurn, current player=" + mCurrentTurn.getValue());
        }

        mView.prepareForNextTurn(mCurrentTurn);
        setGameState(mGameBoard.getCurrentGameState());
        if (mCurrentTurn == mComputerPlayer) {
            mCurrentTurn = mHumanPlayer;
            mView.finishTurn(mCurrentTurn);
            makePlayerMove();
        } else {
            mCurrentTurn = mComputerPlayer;
            mView.finishTurn(mCurrentTurn);
            makeComputerMove();
        }
    }

    private void displayeGameResult(GameState gameState) {
        int textResource;
        if (gameState == GameState.WIN) {
            if (mCurrentTurn == mComputerPlayer) {
                textResource = R.string.computer_wins;
            } else {
                textResource = R.string.you_win; // Never should happen!!
            }
        } else {
            textResource = R.string.tie;
        }
        mView.displayGameResult(gameState, textResource);
    }

    private boolean onCellSelected(int xCoord, int yCoord) {
        if (DEBUG) {
            Log.i(TAG, "onCellSelected");
        }

        TTTMove move;
        if (mCurrentTurn == GameState.PLAYER1) {
            move = new TTTMove(xCoord, yCoord, TTTMark.X);
        } else {
            move = new TTTMove(xCoord, yCoord, TTTMark.O);
        }

        mGameBoard.put(move);

        return true;
    }

    private void setGameState(GameState state) {
        mView.setTurnButtonState(state);
    }

    private void makePlayerMove() {
        if (DEBUG) {
            Log.i(TAG, "makePlayerMove, mCurrentTurn=" + mCurrentTurn.getValue());
        }
    }

    private void makeComputerMove() {
        mCurrentTurn = mComputerPlayer;

        TTTMove move = (TTTMove) mComputer.getMove(mGameBoard);
        clickNextTurnButton(mComputerPlayer, move.mXCoordinate, move.mYCoordinate);
    }
}
