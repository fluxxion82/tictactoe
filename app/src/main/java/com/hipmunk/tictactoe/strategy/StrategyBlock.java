package com.hipmunk.tictactoe.strategy;

import com.hipmunk.tictactoe.model.ttt.TTTBoard;
import com.hipmunk.tictactoe.model.ttt.TTTMark;
import com.hipmunk.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/6/15.
 */
class StrategyBlock extends Strategy {
    private final static Strategy WIN_STRATEGY = new StrategyWin(null);

    StrategyBlock(Strategy nextStrategy) {
        mNextStrategy = nextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMark opponentValue = playerValue == TTTMark.X ? TTTMark.O : TTTMark.X;
        return WIN_STRATEGY.getNextMoveFromChild(opponentValue, board);
    }
}
