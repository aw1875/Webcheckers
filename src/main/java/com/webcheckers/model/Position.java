// Class Name: Position
// Description: This class represents a Position on the game board.
package com.webcheckers.model;

/**
 * This class holds the location of a cell on board
 * @author couchcoder
 * @version 1.0
 * @since 1.0
 */
public class Position {

    private int row, cell;

    /**
     * This constructor holds the coordinates of the cell
     * i.e. the row and column of the Position
     * @param row the row at which the position is set
     * @param cell the cell at which the position is set
     */
    public Position (int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * this getter method returns the row associated with
     * the Position
     * @return the corresponding row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * this getter method returns the cell associated with
     * the Position
     * @return the corresponding cell
     */
    public int getCell() {
        return this.cell;
    }

    /**
     * This method returns if two positions are the same
     * @param obj the Position object
     * @return bool, true if two positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if(other.getRow() != this.getRow())
            return false;
        if(other.getCell() != this.getCell())
            return false;
        return true;
    }

    /**
     * This method pretty prints the coordinates of a cell
     * @return the formatted coordinates of the associated cell
     */
    @Override
    public String toString() {
        return ("[" + this.getRow() + "," + this.getCell() + "]");
    }
}
