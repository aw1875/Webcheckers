// Class Name: Piece
// Description: Object to define a Piece
package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;

/**
 * This class creates an object for Piece of either type Single or King
 * and Color of either Red or White pulled from enum classes
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
public class Piece {
    private Type type;
    private Color color;

    /**
     * constructor for Piece Object that sets its type and color.
     * @param type the type of Piece
     * @param color the color of Piece
     */
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    /**
     * getter function to get the type of Piece
     * @return type (enum)
     */
    public Type getType() {
        return type;
    }

    /**
     * getter function to get the color of Piece
     * @return color (enum)
     */
    public Color getColor() {
        return color;
    }

    /**
     * function to set the type of a Piece as king
     */
    public void setKing(){
        this.type = Type.KING;
    }

    /**
     * function to set the type of a Piece as Single
     */
    public void setSingle() {
        this.type = Type.SINGLE;
    }
}
