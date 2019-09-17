package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidatorRedKing {
    private final int MAX_ROWS = 7;

    public void allPossibleMovesRedKingMinus(int i, int j, Row row, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        //if the king piece can move into space cell - 1 position
        if (row.getSpace(j - 1).getPiece() == null) {
            simpleMoves.add(new Move(new Position(i, j), new Position(i + 1, j - 1)));
        } else if (row.getSpace(j - 1).getPiece().getColor() == Color.WHITE) {
            if (i + 2 <= MAX_ROWS) {
                if (j - 2 >= 0) {
                    Space JumpSpace = rows.get(i + 2).getSpace(j - 2);
                    if (JumpSpace.getPiece() == null) {
                        jumpMoves.add(new Move(new Position(i, j), new Position(i + 2, j - 2)));
                    }
                }
            }
        }
    }

    public void allPossibleMovesRedKingPlus(int i, int j, Row row, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        //if the king piece can move into space cell + 1 position
        if (row.getSpace(j + 1).getPiece() == null) {
            simpleMoves.add(new Move(new Position(i, j), new Position(i + 1, j + 1)));
        } else if (row.getSpace(j + 1).getPiece().getColor() == Color.WHITE && (i + 2 <= MAX_ROWS) && (j + 2 <= MAX_ROWS)) {
            Space JumpSpace = rows.get(i + 2).getSpace(j + 2);
            if (JumpSpace.getPiece() == null) {
                jumpMoves.add(new Move(new Position(i, j), new Position(i + 2, j + 2)));
            }
        }
    }

    public void getPossibeJumpMovesOnPositionRedKingMinus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, HashSet<Move> jumpMoves) {
        if (row + 1 <= MAX_ROWS) {
            if ((row + 2 <= MAX_ROWS) && (cell - 2 >= 0) && (rows.get(row + 1).getSpace(cell - 1).getPiece() != null)) {
                if ((rows.get(row + 1).getSpace(cell - 1).getPiece().getColor() == Color.WHITE) && (rows.get(row + 2).getSpace(cell - 2).getPiece() == null)) {
                    if (notEmpty != null) {
                        for (Position position : notEmpty) {
                            if (position.getRow() == row + 2 && position.getCell() == cell - 2) {
                                notJumpAdd = true;
                            }
                        }
                    }
                    if (!notJumpAdd) {
                        jumpMoves.add(new Move(pos, new Position(row + 2, cell - 2)));
                    }
                }
            }
        }
    }

    public void getPossibeJumpMovesOnPositionRedKingPlus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, HashSet<Move> jumpMoves) {
        if (row + 1 <= MAX_ROWS) {
            if ((row + 2 <= MAX_ROWS) && (cell + 2 <= MAX_ROWS) && (rows.get(row + 1).getSpace(cell + 1).getPiece() != null)) {
                if ((rows.get(row + 1).getSpace(cell + 1).getPiece().getColor() == Color.WHITE) && (rows.get(row + 2).getSpace(cell + 2).getPiece() == null)) {
                    if (notEmpty != null) {
                        for (Position position : notEmpty) {
                            if (position.getRow() == row + 2 && position.getCell() == cell + 2) {
                                notJumpAdd = true;
                            }
                        }
                    }
                    if (!notJumpAdd) {
                        jumpMoves.add(new Move(pos, new Position(row + 2, cell + 2)));
                    }
                }
            }
        }
    }
}
