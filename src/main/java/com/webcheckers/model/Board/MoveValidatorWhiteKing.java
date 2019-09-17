package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidatorWhiteKing {
    private final int MAX_ROWS = 7;

    public void allPossibleMovesWhiteKingMinus(int i, int j, Row kingRow, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        if (kingRow.getSpace(j-1).getPiece() == null) {
            simpleMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i+1, MAX_ROWS-j+1)));
        }
        else if ((kingRow.getSpace(j-1).getPiece().getColor() == Color.RED) && (i-2 >= 0) && (j-2 >= 0)) {
            Space kingJumpSpace = rows.get(i-2).getSpace(j-2);
            if (kingJumpSpace.getPiece() == null) {
                jumpMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i+2, MAX_ROWS-j+2)));
            }
        }
    }

    public void allPossibleMovesWhiteKingPlus(int i, int j, Row kingRow, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        if (kingRow.getSpace(j+1).getPiece() == null) {
            simpleMoves.add(new Move(new Position(MAX_ROWS - i, MAX_ROWS - j), new Position(MAX_ROWS-i+1, MAX_ROWS-j-1)));
        }
        else if((kingRow.getSpace(j+1).getPiece().getColor() == Color.RED) && (i-2 >= 0) && (j+2 <= MAX_ROWS)) {
            Space kingJumpSpace = rows.get(i-2).getSpace(j+2);
            if (kingJumpSpace.getPiece() == null) {
                jumpMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i+2, MAX_ROWS-j-2)));
            }
        }
    }

    public void getAllPosibleJumpMovesOnPositionWhiteKingMinus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, HashSet<Move> jumpMoves) {
        if (row - 1 >= 0) {
            if ((row - 2 >= 0) && (cell - 2 >= 0) && (rows.get(row - 1).getSpace(cell - 1).getPiece() != null)) {
                if ((rows.get(row - 1).getSpace(cell - 1).getPiece().getColor() == Color.RED) && rows.get(row - 2).getSpace(cell - 2).getPiece() == null) {
                    if (notEmpty != null) {
                        for (Position position : notEmpty) {
                            if ((position.getRow() == row - 2) && (position.getCell() == cell - 2)) {
                                notJumpAdd = true;
                            }
                        }
                    }
                    if (!notJumpAdd) {
                        jumpMoves.add(new Move(new Position(MAX_ROWS-row, MAX_ROWS-cell), new Position(MAX_ROWS-row+2, MAX_ROWS-cell+2)));
                    }
                }
            }
        }
    }

    public void getAllPossibleJumpMovesOnPositionWhiteKingPlus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, HashSet<Move> jumpMoves) {
        if (row - 1 >= 0) {
            if ((row - 2 >= 0) && (cell + 2 <= MAX_ROWS) && (rows.get(row - 1).getSpace(cell + 1).getPiece() != null)) {
                if ((rows.get(row - 1).getSpace(cell + 1).getPiece().getColor() == Color.RED) && rows.get(row - 2).getSpace(cell + 2).getPiece() == null) {
                    if (notEmpty != null) {
                        for (Position position : notEmpty) {
                            if ((position.getRow() == row - 2) && (position.getCell() == cell + 2)) {
                                notJumpAdd = true;
                            }
                        }
                    }
                    if (!notJumpAdd) {
                        jumpMoves.add(new Move(new Position(MAX_ROWS-row, MAX_ROWS-cell), new Position(MAX_ROWS-row+2, MAX_ROWS-cell-2)));
                    }
                }
            }
        }
    }
}
