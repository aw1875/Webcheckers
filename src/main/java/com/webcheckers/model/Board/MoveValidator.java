package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidator {
    private BoardView boardView;
    protected boolean isRedPlayer;
    private HashSet<Move> simpleMoves;
    private HashSet<Move> jumpMoves;
    private MoveValidatorRed moveValidatorRed;
    private MoveValidatorWhite moveValidatorWhite;
    private final int MAX_ROWS = 7;

    /**
     * Constructor for the MoveValidator
     * @param boardView
     * @param isRedPlayer
     */
    public MoveValidator(BoardView boardView, boolean isRedPlayer) {
        this.boardView = boardView;
        moveValidatorRed = new MoveValidatorRed();
        moveValidatorWhite = new MoveValidatorWhite();
        this.isRedPlayer = isRedPlayer;
        simpleMoves = new HashSet<>();
        jumpMoves = new HashSet<>();
    }

    /**
     * If a player has any possible moves it returns true.  Otherwise,
     * returns false because they have no moves.
     * @return boolean value representing if there are any possible moves for the player
     */
    public boolean generateAllPossibleMoves() {
        simpleMoves.clear();
        jumpMoves.clear();
        ArrayList<Row> rows = boardView.getBoardArray();
        if (isRedPlayer) {
            return generateAllPossibleMovesRed(rows);
        }
        else {
            return generateAllPossibleMovesWhite(rows);
        }
    }

    /**
     * If the red player has any possible moves, returns true. Other wise,
     * returns false.
     * @param rows Arraylist type of all the rows
     * @return boolean value that represents if there are possible moves
     * for the red player
     */
    private boolean generateAllPossibleMovesRed(ArrayList<Row> rows) {
        Space redSpace = null;
        for (int i = MAX_ROWS; i >= 0; i--) {
            Row row = rows.get(i);
            Row ckrow = null;
            if (i-1 >= 0) {
                ckrow = rows.get(i-1);
            }
            Row kingRow = null;
            if (i+1 <= MAX_ROWS) {
                kingRow = rows.get(i+1);
            }
            for (int j = 0; j < MAX_ROWS + 1; j++) {
                if (row.getSpace(j).isColored() && row.getSpace(j).getPiece() != null && row.getSpace(j).getPiece().getColor() == Color.RED) {
                    redSpace = row.getSpace(j);
                }
                else {
                    redSpace = null;
                }
                if (redSpace != null) {
                    if (j-1 >= 0) {
                        moveValidatorRed.getAllPossibleMovesRedMinus(i, j, ckrow, kingRow, redSpace, rows, simpleMoves, jumpMoves);
                    }
                    if (j + 1 <= MAX_ROWS) {
                        moveValidatorRed.getAllPossibleMovesRedPlus(i, j, ckrow, kingRow, redSpace, rows, simpleMoves, jumpMoves);
                    }
                }
            }
        }
        return (!simpleMoves.isEmpty()) || (!jumpMoves.isEmpty());
    }

    /**
     * If the white player has any possible moves, returns true. Other wise,
     * returns false.
     * @param rows Arraylist type of all the rows
     * @return boolean value that represents if there are possibles moves
     * for the white player
     */
    private boolean generateAllPossibleMovesWhite(ArrayList<Row> rows) {
        Space whiteSpace = null;
        for (int i = 0; i <= MAX_ROWS; i++) {
            Row row = rows.get(i);
            Row ckrow = null;
            if (i+1 <= MAX_ROWS) {
                ckrow = rows.get(i+1);
            }
            Row kingRow = null;
            if (i-1 >= 0) {
                kingRow = rows.get(i-1);
            }
            for (int j = 0; j < MAX_ROWS + 1; j++) {
                if (row.getSpace(j).isColored() && row.getSpace(j).getPiece() != null && row.getSpace(j).getPiece().getColor() == Color.WHITE) {
                    whiteSpace = row.getSpace(j);
                }
                else {
                    whiteSpace = null;
                }
                if (whiteSpace != null) {
                    if (j-1 >= 0) {
                        moveValidatorWhite.getAllPossibleMovesWhiteMinus(i, j, ckrow, kingRow, whiteSpace, rows, simpleMoves, jumpMoves);
                    }
                    if (j + 1 <= MAX_ROWS) {
                        moveValidatorWhite.getAllPossibleMovesWhitePlus(i, j, ckrow, kingRow, whiteSpace, rows, simpleMoves, jumpMoves);
                    }
                }
            }
        }
        return (!simpleMoves.isEmpty()) || (!jumpMoves.isEmpty());
    }

    /**
     * If the current position on the board has any possible jump moves, returns true.  Otherwise,
     * returns false.
     * @param pos Position type of the placement on the board
     * @param isKing boolean type if the piece is a King
     * @param notEmpty Arraylist of positions that have pieces in that space
     * @return boolean representing if there are any possibilities of jump moves on the board
     */
    public boolean getPossibleJumpMovesOnPosition(Position pos, boolean isKing, ArrayList<Position> notEmpty) {
        simpleMoves.clear();
        jumpMoves.clear();
        ArrayList<Row> rows = boardView.getBoardArray();
        if (isRedPlayer) {
            return getPossibleJumpMovesOnPositionRed(rows, pos, isKing, notEmpty);
        }
        else {
            return getPossibleJumpMovesOnPositionWhite(rows, pos, isKing, notEmpty);
        }
    }

    /**
     * If the current Red player's position on the board has any possible jump moves,
     * returns true.  Otherwise, returns false.
     * @param rows Arraylist of Row representing all the rows on the bard
     * @param pos Position type of the placement on the board
     * @param isKing boolean type if the piece is a King
     * @param notEmpty Arraylist of positions that have pieces in that space
     * @return boolean representing if there are any possibilities of jump moves on the board for the red player
     */
    public boolean getPossibleJumpMovesOnPositionRed(ArrayList<Row> rows, Position pos, boolean isKing, ArrayList<Position> notEmpty) {
        boolean notJumpAdd = false;
        int row = pos.getRow();
        int cell = pos.getCell();
        if (cell - 1 >= 0) {
            moveValidatorRed.getPossibleJumpMovesOnPositionRedMinus(row, cell, pos, rows, notEmpty, notJumpAdd, isKing, jumpMoves);
            notJumpAdd = false;
        }
        if (cell + 1 <= MAX_ROWS) {
            moveValidatorRed.getPossibleJumpMovesOnPositionRedPlus(row, cell, pos, rows, notEmpty, notJumpAdd, isKing, jumpMoves);
        }
        return !jumpMoves.isEmpty();
    }

    /**
     * If the current White player's position on the board has any possible jump moves,
     * returns true.  Otherwise, returns false.
     * @param rows Arraylist of Row representing all the rows on the bard
     * @param pos Position type of the placement on the board
     * @param isKing boolean type if the piece is a King
     * @param notEmpty Arraylist of positions that have pieces in that space
     * @return boolean representing if there are any possibilities of jump moves on the board for the white player
     */
    public boolean getPossibleJumpMovesOnPositionWhite(ArrayList<Row> rows, Position pos, boolean isKing, ArrayList<Position> notEmpty) {
        boolean notJumpAdd = false;
        int row = pos.getRow();
        int cell = pos.getCell();
        if (cell - 1 >= 0) {
            moveValidatorWhite.getPossibleJumpMovesOnPositionWhiteMinus(row, cell, pos, rows, notEmpty, notJumpAdd, isKing, jumpMoves);
            notJumpAdd = false;
        }
        if (cell + 1 <= MAX_ROWS) {
            moveValidatorWhite.getPossibleJumpMovesOnPositionWhitePlus(row, cell, pos, rows, notEmpty, notJumpAdd, isKing, jumpMoves);
        }
        return !jumpMoves.isEmpty();
    }

    /**
     * Prints the status of the possible moves
     */
    public void printSetStatus() {
        if (simpleMoves.isEmpty()) {
            System.out.println("No simple moves");
        }
        else {
            System.out.println("Simple Moves: ");
            for (Move move : simpleMoves) {
                System.out.println(move);
            }
        }
        if (jumpMoves.isEmpty()) {
            System.out.println("No jump moves");
        }
        else {
            System.out.println("Jump Moves: ");
            for (Move move : jumpMoves) {
                System.out.println(move);
            }
        }
    }

    /**
     * Returns all the possible jump moves
     * @return HashSet of Moves that represent jump moves
     */
    public HashSet<Move> getJumpMoves() {
        return jumpMoves;
    }

    /**
     * If the player has any simple moves or jump moves, returns true.
     * Otherwise, returns false.
     * @return boolean value that represents if there are any possible moves
     */
    public boolean hasMoves() {
        return (!simpleMoves.isEmpty() || !jumpMoves.isEmpty());
    }

    /**
     * If there are no jump moves, gets all the simple moves. Otherwise, gets the jump moves.
     * @return HashSet of moves that are either the jump moves, or the simple moves
     */
    public HashSet<Move> getPossibleMoves() {
        if (jumpMoves.isEmpty()) {
            return simpleMoves;
        }
        else {
            return jumpMoves;
        }
    }

    /**
     * If the jump moves HashSet is not empty returns true because there is a jump move.
     * Otherwise, returns false.
     * @return boolean value representing if there are any possible jump moves.
     */
    public boolean jumpMoveAvailable() {
        return !jumpMoves.isEmpty();
    }

    /**
     * Switches the players
     */
    public void switchActivePlayer() {
        simpleMoves.clear();
        jumpMoves.clear();
        if (isRedPlayer) {
            isRedPlayer = false;
        }
        else {
            isRedPlayer = true;
        }
    }
}
