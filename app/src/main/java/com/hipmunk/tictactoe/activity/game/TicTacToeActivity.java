package com.hipmunk.tictactoe.activity.game;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hipmunk.tictactoe.BuildConfig;
import com.hipmunk.tictactoe.R;
import com.hipmunk.tictactoe.presenter.TTTPresenter;
import com.hipmunk.tictactoe.presenter.TTTPresenterImpl;
import com.hipmunk.tictactoe.view.TicTacToeBoardLayout;

/**
 * Created by salbury on 5/4/15.
 */
public class TicTacToeActivity extends BoardActivity implements TicTacToeView {
    private static final String TAG = TicTacToeActivity.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private TTTPresenter mPresenter;
    private TicTacToeBoardLayout mGameView;
    private TextView mInfoView;
    private Button mButtonNext;

    private GameState mHumanPlayer;
    private GameState mComputerPlayer;
    private GameState mCurrentPlayer;

    private class CellListener implements TicTacToeBoardLayout.ICellListener {

        public void onCellSelected(int xCoord, int yCoord) {
            int[] cell = mGameView.getSelection();
            mButtonNext.setEnabled(cell[0] >= 0);
        }
    }

    private class ButtonListener implements View.OnClickListener {

        public void onClick(View v) {
            if (getTurnButtonState() == GameState.WIN || getTurnButtonState() == GameState.DRAW) {
                resetGame();
            } else {
                int[] cell = mGameView.getSelection();
                if (cell.length > 1) {
                    mPresenter.clickNextTurnButton(mCurrentPlayer, cell[0], cell[1]);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DEBUG) {
            Log.i(TAG, "onCreate");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        mGameView = (TicTacToeBoardLayout) findViewById(R.id.game_view);
        mInfoView = (TextView) findViewById(R.id.info_turn);
        mButtonNext = (Button) findViewById(R.id.next_turn);

        mPresenter = new TTTPresenterImpl(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            int first = (int) b.get("first");
            if (first == GameState.PLAYER1.getValue()) {
                mCurrentPlayer = mHumanPlayer = GameState.PLAYER1;
                mComputerPlayer = GameState.PLAYER2;

                prepareForNextTurn(mComputerPlayer);
            } else {
                mCurrentPlayer = mComputerPlayer = GameState.PLAYER1;
                mHumanPlayer = GameState.PLAYER2;

                prepareForNextTurn(mHumanPlayer);
            }

        } else {
            //go back to start activity?
            finish();
        }

        mPresenter.setPlayerHuman(mHumanPlayer);
        mPresenter.setPlayerComputer(mComputerPlayer);

        resetGame();

        mButtonNext.setOnClickListener(new ButtonListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void displayGameResult(GameState winner, int textResource) {
        if (DEBUG) {
            Log.i(TAG, "displayGameResult");
        }

        mButtonNext.setEnabled(true);
        mButtonNext.setText(R.string.new_game);

        mInfoView.setText(textResource);

    }

    @Override
    public GameState getCurrentPlayer() {
        return mGameView.getCurrentPlayer();
    }

    /**
     * Prepares for the next player
     *
     * @param currentPlayer The player of current turn
     * @return
     */
    @Override
    public void prepareForNextTurn(GameState currentPlayer) {
        if (DEBUG) {
            Log.i(TAG, "prepareForNextTurn, player=" + currentPlayer.getValue());
        }

        mButtonNext.setEnabled(false);
        if (currentPlayer == mHumanPlayer) {
            mInfoView.setText(R.string.player_computer_thinking);
            mGameView.setEnabled(false);
        } else if (currentPlayer == mComputerPlayer) {
            mInfoView.setText(R.string.player_one_thinking);
            mGameView.setEnabled(true);
        }
    }

    @Override
    public void finishTurn(GameState currentPlayer) {
        if (DEBUG) {
            Log.i(TAG, "finishTurn, player=" + currentPlayer.getValue());
        }

        mCurrentPlayer = currentPlayer;

        mGameView.setCurrentPlayer(currentPlayer);
    }

    @Override
    public void playMove(int xCoord, int yCoord) {
        if (mCurrentPlayer == mComputerPlayer) {
            playComputerMove(xCoord, yCoord);
        } else {
            playHumanMove(xCoord, yCoord);
        }
    }

    @Override
    public void stopBlink() {
        mGameView.stopBlink();
    }

    @Override
    public void setTurnButtonState(GameState state) {
        mButtonNext.setTag(mButtonNext.getId(), state);

    }

    public void playHumanMove(int xCoord, int yCoord) {
        if (DEBUG) {
            Log.i(TAG, "play human move");
        }
        setCell(xCoord, yCoord, mHumanPlayer);
    }

    public void playComputerMove(int xCoord, int yCoord) {
        if (DEBUG) {
            Log.i(TAG, "play computer move");
        }
        setCell(xCoord, yCoord, mComputerPlayer);
    }

    private void setCell(int cellXIndex, int cellYIndex, GameState value) {
        if (DEBUG) {
            Log.i(TAG, "setCell");
        }
        mGameView.setCell(cellXIndex, cellYIndex, value);
    }

    private GameState getTurnButtonState() {
        GameState state = (GameState) mButtonNext.getTag(mButtonNext.getId());
        if (state != null) {
            return state;
        }
        return null;
    }

    private void resetGame() {

        mCurrentPlayer = mHumanPlayer == GameState.PLAYER1 ? mHumanPlayer : mComputerPlayer;
        setTurnButtonState(GameState.EMPTY);
        prepareForNextTurn(mCurrentPlayer == GameState.PLAYER1 ? GameState.PLAYER2 : GameState.PLAYER1);
        mButtonNext.setEnabled(true);
        mButtonNext.setText(R.string.im_done);

        mGameView.initializeBoard();
        mGameView.setCurrentPlayer(mCurrentPlayer);

        mGameView.setFocusable(true);
        mGameView.setFocusableInTouchMode(true);
        mGameView.setCellListener(new CellListener());

        mPresenter.newGame();
    }
}
