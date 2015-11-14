package com.albury.tictactoe.presenter;

import com.albury.tictactoe.activity.game.GameState;

/**
 * Created by salbury on 5/4/15.
 */
public interface TTTPresenter {
    public void setPlayerHuman(GameState player);

    public void setPlayerComputer(GameState player);

    public void clickNextTurnButton(GameState currentPlayer, int xCoord, int yCoord);

    public void newGame();

    public void onResume();
}
