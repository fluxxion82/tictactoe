package com.hipmunk.tictactoe.strategy;

import com.hipmunk.tictactoe.BuildConfig;
import com.hipmunk.tictactoe.gamerules.TTTRules;
import com.hipmunk.tictactoe.model.ttt.TTTBoard;
import com.hipmunk.tictactoe.model.ttt.TTTMark;
import com.hipmunk.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/7/15.
 */
class StrategySecondTurnAsSecondPlayer extends Strategy {
    private static final String TAG = StrategySecondTurnAsSecondPlayer.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    StrategySecondTurnAsSecondPlayer(Strategy nextStrategy) {
        mNextStrategy = nextStrategy;
    }

    @Override
    protected TTTMove getNextMoveFromChild(TTTMark playerValue, TTTBoard board) {
        TTTMove position = null;
        int numberOfMoves = board.getNumberOfMove();
        TTTMove opponentPosition = board.getLastMove();

        if (numberOfMoves == 3) {
            position = getPositionAsSecondPlayer(board, position, opponentPosition);
        }

        return position;
    }

    private TTTMove getPositionAsSecondPlayer(TTTBoard board, TTTMove position, TTTMove opponentLastPos) {
        if (TTTRules.isCornerPosition(opponentLastPos)) {
            position = getPositionWhenOpponentChoseCornerMoves(board, opponentLastPos);
        } else {
            position = getPositionWhenOpponenChoseMiddleMoves(board, opponentLastPos);
        }
        return position;
    }

    private TTTMove getPositionWhenOpponentChoseCornerMoves(TTTBoard board, TTTMove opponentLastPos) {
        TTTMove position = null;
        TTTMove oppositeOpponentPosition = TTTRules.getOppositeCorner(opponentLastPos);
        if (board.getMoveHistory().get(0).mXCoordinate == oppositeOpponentPosition.mXCoordinate
                && board.getMoveHistory().get(0).mYCoordinate == oppositeOpponentPosition.mYCoordinate) {
            //we want to get a middle square to threaten a win
            position = middleFromCorner(board, opponentLastPos.mXCoordinate, opponentLastPos.mYCoordinate);
        } else {
            position = getFreeCorner(opponentLastPos);
        }
        return position;
    }

    private TTTMove getPositionWhenOpponenChoseMiddleMoves(TTTBoard board, TTTMove opponentLastPos) {
        TTTMove opponentPos1 = board.getMoveHistory().get(0);
        TTTMove higher = null;
        TTTMove lower = null;
        if (opponentPos1.mXCoordinate < opponentLastPos.mXCoordinate) {
            higher = opponentPos1;
            lower = opponentLastPos;
        } else {
            higher = opponentLastPos;
            lower = opponentPos1;
        }

        int row = getCornerFromMiddle(lower.mXCoordinate, higher.mXCoordinate);
        int col = getCornerFromMiddle(lower.mYCoordinate, higher.mYCoordinate);

        return new TTTMove(row, col, TTTMark.BLANK);
    }

    private TTTMove getFreeCorner(TTTMove opponentPosition) {
        int row = opponentPosition.mXCoordinate;
        int col = TTTRules.getOppositeCorner(opponentPosition.mYCoordinate);

        TTTMove position = new TTTMove(row, col, TTTMark.BLANK);
        return position;
    }

    private int getCornerFromMiddle(int lowerPos, int higherPos) {
        int corner = 0;
        if (TTTRules.isAtCorner(lowerPos)) {
            corner = lowerPos;
        } else {
            corner = higherPos;
        }
        return corner;
    }

    private TTTMove middleFromCorner(TTTBoard board, int x, int y) {
        TTTMove position = null;
        final int boardSize = board.getCurrentBoard().length;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                TTTMove pos = new TTTMove(row, col, TTTMark.O);
                if (board.getCurrentBoard()[row][col] == TTTMark.BLANK && !TTTRules.isCornerPosition(pos)) {
                    position = pos;
                    break;
                }
            }
        }
        return position;
    }
}
