package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidatorRed {
    private MoveValidatorRedKing moveValidatorRedKing;
    private final int MAX_ROWS = 7;


    public MoveValidatorRed() {
        moveValidatorRedKing = new MoveValidatorRedKing();
    }

    void getAllPossibleMovesRedMinus(int i, int j, Row row, Row kingRow, Space redSpace, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        if (row != null) {
            if (row.getSpace(j-1).getPiece() == null) {
                simpleMoves.add(new Move(new Position(i,j), new Position(i-1, j-1)));
            }
            else if ((row.getSpace(j-1).getPiece().getColor() == Color.WHITE) && (i-2 >= 0) && (j-2 >= 0)) {
                Space JumpSpace = rows.get(i-2).getSpace(j-2);
                if (JumpSpace.getPiece() == null) {
                    jumpMoves.add(new Move(new Position(i,j), new Position(i-2, j-2)));
                }
            }
        }
        if (redSpace.getPiece().getType() == Type.KING) {
            if (kingRow != null) {
                moveValidatorRedKing.allPossibleMovesRedKingMinus(i, j, kingRow, rows, simpleMoves, jumpMoves);
            }
        }
    }

    void getAllPossibleMovesRedPlus (int i, int j, Row row, Row kingRow, Space redSpace, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        if (row != null) {
            if (row.getSpace(j+1).getPiece() == null) {
                simpleMoves.add(new Move(new Position(i,j), new Position(i-1, j+1)));
            }
            else if ((row.getSpace(j+1).getPiece().getColor() == Color.WHITE) && (i-2 >= 0) && (j+2 <= MAX_ROWS)) {
                Space JumpSpace = rows.get(i-2).getSpace(j+2);
                if (JumpSpace.getPiece() == null) {
                    jumpMoves.add(new Move(new Position(i,j), new Position(i-2, j+2)));
                }
            }
        }
        if (redSpace.getPiece().getType() == Type.KING && kingRow != null) {
            moveValidatorRedKing.allPossibleMovesRedKingPlus(i, j, kingRow, rows, simpleMoves, jumpMoves);
        }
    }

    public void getPossibleJumpMovesOnPositionRedMinus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, boolean isKing, HashSet<Move> jumpMoves) {
        if (row - 1 >= 0) {
            if (rows.get(row - 1).getSpace(cell - 1).getPiece() != null) {
                if ((row - 2 >= 0) && (cell - 2 >= 0) && (rows.get(row - 1).getSpace(cell - 1).getPiece().getColor() == Color.WHITE)) {
                    if (rows.get(row - 2).getSpace(cell - 2).getPiece() == null) {
                        if (notEmpty != null) {
                            for (Position position: notEmpty) {
                                if (position.getRow() == row-2 && position.getCell() == cell - 2) {
                                    notJumpAdd = true;
                                }
                            }
                        }
                        if (!notJumpAdd) {
                            jumpMoves.add(new Move(pos, new Position(row-2, cell-2)));
                        }
                        notJumpAdd = false;
                    }
                }
            }
        }
        if (isKing) {
            moveValidatorRedKing.getPossibeJumpMovesOnPositionRedKingMinus(row, cell, pos, rows, notEmpty, notJumpAdd, jumpMoves);
        }
    }

    public void getPossibleJumpMovesOnPositionRedPlus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, boolean isKing, HashSet<Move> jumpMoves) {
        if (row - 1 >= 0) {
            if (rows.get(row - 1).getSpace(cell + 1).getPiece() != null) {
                if ((row - 2 >= 0) && (cell + 2 <= MAX_ROWS) && (rows.get(row - 1).getSpace(cell + 1).getPiece().getColor() == Color.WHITE)) {
                    if (rows.get(row - 2).getSpace(cell + 2).getPiece() == null) {
                        if (notEmpty != null) {
                            for (Position position: notEmpty) {
                                if (position.getRow() == row-2 && position.getCell() == cell + 2) {
                                    notJumpAdd = true;
                                }
                            }
                        }
                        if (!notJumpAdd) {
                            jumpMoves.add(new Move(pos, new Position(row-2, cell+2)));
                        }
                        notJumpAdd = false;
                    }
                }
            }
        }
        if (isKing) {
            moveValidatorRedKing.getPossibeJumpMovesOnPositionRedKingPlus(row, cell, pos, rows, notEmpty, notJumpAdd, jumpMoves);
        }
    }
}
