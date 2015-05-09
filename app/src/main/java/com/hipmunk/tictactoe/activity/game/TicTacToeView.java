package com.hipmunk.tictactoe.activity.game;

/**
 * Created by salbury on 5/4/15.
 */
public interface TicTacToeView {
    public void displayGameResult(GameState gameState, int textResource);

    public GameState getCurrentPlayer();

    public void setTurnButtonState(GameState state);

    public void prepareForNextTurn(GameState player);

    public void playMove(int xCoord, int yCoord);

    public void stopBlink();

    public void finishTurn(GameState moveEndingPlayer);
}

