// Class Name: Move
// Description: This class holds the code for a Move made/to be made by a player
package com.webcheckers.model;

import com.webcheckers.util.Message;

import java.util.HashSet;

/**
 * This class represents a move that can be made by a player. A move
 * has a starting and a final position.
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
public class Move {
    private Position start, end;
    private final static int MAX_ROWS = 7;

    /**
     * This constructor sets the starting position and the final position
     * contained by a move
     * @param start the starting position of the move
     * @param end the final position of the move
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * this getter method returns the starting point of a move
     * @return Position object for start
     */
    public Position getStart() {
        return this.start;
    }

    /**
     * this getter method returns the final point of a move
     * @return Position object for end
     */
    public Position getEnd() {
        return this.end;
    }

    public Message validateMove(HashSet<Move> possibleMoves, boolean isJump) {
        System.out.println("This move is" + this);
        if (possibleMoves.contains(this)) {
            return new Message("Valid Move", Message.Type.INFO);
        }
        else if (isJump) {
            return new Message("Invalid, you must make a jump move", Message.Type.ERROR);
        }
        else {
            return new Message("Invalid Move", Message.Type.ERROR);
        }
    }

    /**
     * When one player makes a move, flips the move to match the move but
     * on the opposite side for the other board.
     */
    public void flipMove() {
        System.out.println(this);
        this.start = new Position(MAX_ROWS-getStart().getRow(), MAX_ROWS-getStart().getCell());
        this.end = new Position(MAX_ROWS-getEnd().getRow(), MAX_ROWS-getEnd().getCell());
        System.out.println(this);
    }

    /**
     * If the difference between the starting row and ending row is equivalent
     * to two then returns true. Otherwise returns false.
     * @return Boolean type representing if the move qualifies to be a jump
     */
    public boolean isJump() {
        int rowsJumped = Math.abs(this.start.getRow() - this.end.getRow());
        return rowsJumped == 2;
    }

    /**
     * Gets the position of the opponents piece
     * @return Position type representing the coordinates of the opponents piece
     */
    public Position getOpponentPiecePosition() {
        Position takenPiecePosition;
        int takenPieceRow = (start.getRow() + end.getRow()) / 2;
        int takenPieceCell = (start.getCell() + end.getCell()) / 2;
        takenPiecePosition = new Position(takenPieceRow, takenPieceCell);
        return takenPiecePosition;
    }

    /**
     * If the move are the same move then returns true.
     * Otherwise returns false.
     * @param obj Object type item to be checked if it is type move
     * @return boolean type representing equivalence of the moves
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Move other = (Move) obj;
        if (!other.getStart().equals(this.getStart())) {
            return false;
        }
        if (!other.getEnd().equals(this.getEnd())) {
            return false;
        }
        return true;
    }

    /**
     * Calculates the hash code of the start and end coordinates
     * @return int value for the hash code
     */
    @Override
    public int hashCode() {
        final int code = 31;
        int result = 1;
        result = code * result + (start.getRow() + start.getCell());
        result = code * result + (end.getRow() + end.getCell());
        return result;
    }

    /**
     * Creates a string that identifies the start and end of the move
     * @return String type representing the move
     *          "Move: Start[ {start row & col} ], End: {end row & col} ]"
     */
    @Override
    public String toString() {
        return ("Move: Start[" + start.getRow() + ", " + start.getCell() + "], End[" + end.getRow() + ", " + end.getCell() + "]");
    }
}
