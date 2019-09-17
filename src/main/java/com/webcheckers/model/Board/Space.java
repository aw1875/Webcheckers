// Class Name: Space
// Description: Class to implement a Space on the game board
package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;

/**
 * This class implements the Space object to represent a space on
 * the board.
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
public class Space {
    private int cellIdx;
    private Piece piece;
    private boolean isColored;

    /**
     * This constructor sets the properties of a Space on board.
     * @param cellIdx index of the cell its representing
     * @param isColored informs if the space is colored or not
     */
    public Space (int cellIdx, boolean isColored) {
        this.cellIdx = cellIdx;
        this.isColored = isColored;
    }

    /**
     * this constructor sets the properties of Original Space
     * @param originalSpace original Space object
     */
    public Space (Space originalSpace) {
        final int MAX_ROWS = 7;
        cellIdx = MAX_ROWS - originalSpace.getCellIdx();
        isColored = originalSpace.isColored;
        this.piece = originalSpace.getPiece();
    }

    /**
     * getter function to get the cell index
     * @return cell index
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * getter function to get a piece
     * @return a Piece object
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * boolean function to state if the space is a colored space
     * @return bool if the space is colored
     */
    public boolean isColored() {
        return isColored;
    }

    /**
     * boolean function to state is the Space is valid for holding a
     * Piece
     * @return bool if the space is valid
     */
    public boolean isValid() {
        return (isColored && piece == null);
    }

    /**
     * This method creates a Piece given type and color
     * @param type the type of piece its creating
     * @param color the color of piece its creating
     */
    public void createPiece(Type type, Color color) {
        if (this.piece == null)
            this.piece = new Piece(type, color);
    }

    /**
     * This method places a piece on the Space
     * @param newPiece the new Piece object to place on the Space
     */
    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

    /**
     * This method removes a Piece from the Space
     * @return the removed Piece
     */
    public Piece removePiece() {
        Piece remP = this.piece;
        this.piece = null;
        return remP;
    }

    /**
     * Pretty Print critical Piece data (for debugging/future use)
     * @return the formatted information about the piece
     */
    @Override
    public String toString() {
        if (this.piece != null) {
            return ("Space: " + cellIdx + ", Piece: " + this.piece.getType());
        }
        else {
            return ("Space: " + cellIdx + ", Piece: NONE");
        }
    }
}
