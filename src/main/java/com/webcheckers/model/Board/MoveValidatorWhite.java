package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidatorWhite {
    private final int MAX_ROWS = 7;
    private MoveValidatorWhiteKing moveValidatorWhiteKing;

    public MoveValidatorWhite() {
        moveValidatorWhiteKing = new MoveValidatorWhiteKing();
    }

    public void getAllPossibleMovesWhiteMinus(int i, int j, Row row, Row kingRow, Space whiteSpace, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        if (row != null) {
            if (row.getSpace(j-1).getPiece() == null) {
                simpleMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i-1, MAX_ROWS-j+1)));
            }
            else if((row.getSpace(j-1).getPiece().getColor() == Color.RED) && (i+2 <= MAX_ROWS) && (j-2 >= 0)) {
                Space jumpSpace = rows.get(i+2).getSpace(j-2);
                if (jumpSpace.getPiece() == null) {
                    jumpMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i-2, MAX_ROWS-j+2)));
                }
            }
        }
        if (whiteSpace.getPiece().getType() == Type.KING && kingRow != null) {
            moveValidatorWhiteKing.allPossibleMovesWhiteKingMinus(i, j, kingRow, rows, simpleMoves, jumpMoves);
        }
    }

    public void getAllPossibleMovesWhitePlus(int i, int j, Row row, Row kingRow, Space whiteSpace, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves) {
        if (row != null) {
            if (row.getSpace(j+1).getPiece() == null) {
                simpleMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i-1, MAX_ROWS-j-1)));
            }
            else if((row.getSpace(j+1).getPiece().getColor() == Color.RED) && (i+2 <= MAX_ROWS) && (j+2 <= MAX_ROWS)) {
                Space jumpSpace = rows.get(i+2).getSpace(j+2);
                if (jumpSpace.getPiece() == null) {
                    jumpMoves.add(new Move(new Position(MAX_ROWS-i, MAX_ROWS-j), new Position(MAX_ROWS-i-2, MAX_ROWS-j-2)));
                }
            }
        }
        if (whiteSpace.getPiece().getType() == Type.KING && kingRow != null) {
            moveValidatorWhiteKing.allPossibleMovesWhiteKingPlus(i, j, kingRow, rows, simpleMoves, jumpMoves);
        }
    }

    public void getPossibleJumpMovesOnPositionWhiteMinus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, boolean isKing, HashSet<Move> jumpMoves) {
        if (row + 1 <= MAX_ROWS) {
            if (rows.get(row+1).getSpace(cell - 1).getPiece() != null) {
                if ((row + 2 <= MAX_ROWS) && (cell - 2 >= 0) && rows.get(row + 1).getSpace(cell - 1).getPiece().getColor() == Color.RED) {
                    if (rows.get(row + 2).getSpace(cell - 2).getPiece() == null) {
                        if (notEmpty != null) {
                            for (Position position : notEmpty) {
                                if ((position.getRow() == row + 2) && (position.getCell() == cell - 2)) {
                                    notJumpAdd = true;
                                }
                            }
                        }
                        if (!notJumpAdd) {
                            jumpMoves.add(new Move(new Position(MAX_ROWS-row, MAX_ROWS-cell), new Position(MAX_ROWS - row - 2, MAX_ROWS - cell + 2)));
                        }
                        notJumpAdd = false;
                    }
                }
            }
        }
        if(isKing) {
            moveValidatorWhiteKing.getAllPosibleJumpMovesOnPositionWhiteKingMinus(row, cell, pos, rows, notEmpty, notJumpAdd, jumpMoves);
        }
    }

    public void getPossibleJumpMovesOnPositionWhitePlus(int row, int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> notEmpty, boolean notJumpAdd, boolean isKing, HashSet<Move> jumpMoves) {
        if (row + 1 <= MAX_ROWS) {
            if (rows.get(row+1).getSpace(cell + 1).getPiece() != null) {
                if ((row + 2 <= MAX_ROWS) && (cell + 2 <= MAX_ROWS) && rows.get(row + 1).getSpace(cell + 1).getPiece().getColor() == Color.RED) {
                    if (rows.get(row + 2).getSpace(cell + 2).getPiece() == null) {
                        if (notEmpty != null) {
                            for (Position position : notEmpty) {
                                if ((position.getRow() == row + 2) && (position.getCell() == cell + 2)) {
                                    notJumpAdd = true;
                                }
                            }
                        }
                        if (!notJumpAdd) {
                            jumpMoves.add(new Move(new Position(MAX_ROWS-row, MAX_ROWS-cell), new Position(MAX_ROWS - row - 2, MAX_ROWS - cell - 2)));
                        }
                        notJumpAdd = false;
                    }
                }
            }
        }
        if(isKing) {
            moveValidatorWhiteKing.getAllPossibleJumpMovesOnPositionWhiteKingPlus(row, cell, pos, rows, notEmpty, notJumpAdd, jumpMoves);
        }
    }
}
