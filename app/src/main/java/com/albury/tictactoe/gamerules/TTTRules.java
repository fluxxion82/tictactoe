package com.albury.tictactoe.gamerules;

import com.albury.tictactoe.activity.game.GameState;
import com.albury.tictactoe.model.Move;
import com.albury.tictactoe.model.ttt.TTTBoard;
import com.albury.tictactoe.model.ttt.TTTMark;
import com.albury.tictactoe.model.ttt.TTTMove;

/**
 * Created by salbury on 5/4/15.
 */
public class TTTRules {

    public static boolean isMoveValid(TTTBoard board, Move move) {
        if (move == null) {
            return false;
        }
        if (board.getCurrentBoard()[move.mXCoordinate][move.mYCoordinate] != TTTMark.BLANK) {
            return false;
        }
        return true;
    }

    public static boolean isMoveValid(GameState[][] boardData, int xCoord, int yCoord) {
        if (boardData[xCoord][yCoord] != GameState.EMPTY) {
            return false;
        }

        return true;
    }

    // Returns true if there is a win, false otherwise.
    // This calls our other win check functions to check the entire board.
    public static boolean checkForWin(TTTBoard board, Move move) {
        return (checkRowsForWin(board, move) || checkColumnsForWin(board, move) || checkDiagonalsForWin(board, move));
    }

    // Loop through rows and see if any are winners.
    private static boolean checkRowsForWin(TTTBoard board, Move move) {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board.getCurrentBoard()[i][0], board.getCurrentBoard()[i][1], board.getCurrentBoard()[i][2]) == true) {
                return true;
            }
        }
        return false;
    }

    // Loop through columns and see if any are winners.
    private static boolean checkColumnsForWin(TTTBoard board, Move move) {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board.getCurrentBoard()[0][i], board.getCurrentBoard()[1][i], board.getCurrentBoard()[2][i]) == true) {
                return true;
            }
        }
        return false;
    }

    // Check the two diagonals to see if either is a win. Return true if either wins.
    private static boolean checkDiagonalsForWin(TTTBoard board, Move move) {
        return ((checkRowCol(board.getCurrentBoard()[0][0], board.getCurrentBoard()[1][1], board.getCurrentBoard()[2][2]) == true) || (checkRowCol(board.getCurrentBoard()[0][2], board.getCurrentBoard()[1][1], board.getCurrentBoard()[2][0]) == true));
    }

    // Check to see if all three values are the same (and not empty) indicating a win.
    private static boolean checkRowCol(TTTMark c1, TTTMark c2, TTTMark c3) {
        return ((c1 != TTTMark.BLANK) && (c1 == c2) && (c2 == c3));

    }

    // Loop through all cells of the board and if one is found to be blank then return false.
    // Otherwise the board is full.
    public static boolean isBoardFull(TTTBoard board) {
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getCurrentBoard()[i][j] == TTTMark.BLANK) {
                    isFull = false;
                }
            }
        }
        return isFull;
    }

    public static TTTMark[] getRowValues(TTTBoard board, int rowNum) {
        TTTMark[] row = board.getCurrentBoard()[rowNum];
        return getSquareValues(row);
    }

    public static TTTMark[] getColumnValues(TTTBoard board, int colNum) {
        final int boardSize = 3;
        TTTMark[] col = new TTTMark[boardSize];
        for (int i = 0; i < boardSize; i++) {
            col[i] = board.getCurrentBoard()[i][colNum];
        }
        return getSquareValues(col);
    }

    public static TTTMark[] getLeftDiagonalValues(TTTBoard board) {
        final int boardSize = 3;
        TTTMark[] leftDiagonalSquares = new TTTMark[boardSize];
        for (int i = 0; i < boardSize; i++) {
            leftDiagonalSquares[i] = board.getCurrentBoard()[i][i];
        }
        return getSquareValues(leftDiagonalSquares);
    }

    public static TTTMark[] getRightDiagonalValues(TTTBoard board) {
        final int boardSize = 3;
        TTTMark[] rightDiagonalSquares = new TTTMark[boardSize];
        for (int i = 0; i < boardSize; i++) {
            rightDiagonalSquares[i] = board.getCurrentBoard()[i][getBottomRightCorner() - i];
        }
        return getSquareValues(rightDiagonalSquares);
    }

    private static TTTMark[] getSquareValues(TTTMark[] row) {
        TTTMark[] values = new TTTMark[row.length];
        for (int i = 0; i < row.length; i++) {
            TTTMark squareText = row[i];
            values[i] = squareText;
        }
        return values;
    }

    public static boolean isCornerPosition(TTTMove position) {
        int[] opposite = getOppositeCorner(position.mXCoordinate, position.mYCoordinate);
        if(opposite[0] >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isAtCorner(int number) {
        return number == 0 || number == getBottomRightCorner()
                || number == getTopRightCorner() || number == getTopLeftCorner();
    }

    public static TTTMove getOppositeCorner(TTTMove position) {
        int row = position.mXCoordinate;
        int col = position.mYCoordinate;

        int[] move = getOppositeCorner(row, col);
        return new TTTMove(move[0], move[1], TTTMark.BLANK);
    }

    public static int[] getOppositeCorner(int x, int y) {
        int[] pos = new int[2];
        if (x == 0 && y == 0) {
            pos[0] = 2;
            pos[1] = 2;
        } else if (x == 0 && y == 2) {
            pos[0] = 2;
            pos[1] = 0;
        } else if (x == 2 && y == 0) {
            pos[0] = 0;
            pos[1] = 2;
        } else if (x == 2 && y == 2) {
            pos[0] = 0;
            pos[1] = 0;
        } else {
            pos[0] = -1;
            pos[0] = -1;
        }

        return pos;
    }

    public static int getOppositeCorner(int pos) {
        if (pos == 0) {
            pos = getBottomLeftCorner();
        } else {
            pos = 0;
        }
        return pos;
    }

    public static int getBottomLeftCorner() {
        return 0;
    }

    public static int getBottomRightCorner() {
        return 2;
    }

    private static int getTopRightCorner() {
        return 8;
    }

    private static int getTopLeftCorner() {
        return 6;
    }

    public static int[] getCoordFromLinearCell(int cell) {
        int x = 0;
        int y = 0;
        switch (cell) {
            case 0:
                x = 0;
                y = 0;
                break;
            case 1:
                x = 1;
                y = 0;
                break;
            case 2:
                x = 2;
                y = 0;
                break;
            case 3:
                x = 0;
                y = 1;
                break;
            case 4:
                x = 1;
                y = 1;
                break;
            case 5:
                x = 2;
                y = 1;
                break;
            case 6:
                x = 0;
                y = 2;
                break;
            case 7:
                x = 1;
                y = 2;
                break;
            case 8:
                x = 2;
                y = 2;
                break;
        }

        int[] result = new int[3];
        result[0] = x;
        result[1] = y;
        result[2] = cell;
        return result;
    }
}
