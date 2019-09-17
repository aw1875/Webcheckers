// Class Name: TestPiece
// Description: JUnit test for Piece Class
package com.webcheckers.model.Board;

import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@Tag("Model-tier")
/**
 * This class tests the functionality of the Piece class
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
class TestPiece {
    private Piece RedKing = new Piece(Type.KING, Color.RED);
    private Piece WhiteKing = new Piece(Type.KING, Color.WHITE);
    private Piece RedSingle = new Piece(Type.SINGLE, Color.RED);
    private Piece WhiteSingle = new Piece(Type.SINGLE, Color.WHITE);

    /**
     * This test method tests setting color of red king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testRedKingColor() {
        System.out.println("Fetching RedKing piece...");
        assertEquals(RedKing.getColor(), Color.RED);
    }

    /**
     * This test method tests setting color of white king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testWhiteKingColor() {
        System.out.println("Fetching WhiteKing piece...");
        assertEquals(WhiteKing.getColor(), Color.WHITE);

    }

    /**
     * This test method tests setting color of red single
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testRedSingleColor() {
        System.out.println("Fetching RedSingle piece...");
        assertEquals(RedSingle.getColor(), Color.RED);
    }

    /**
     * This test method tests setting color of white king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testWhiteSingleColor() {
            System.out.println("Fetching WhiteSingle piece...");
            assertEquals(WhiteSingle.getColor(), Color.WHITE);
    }

    /**
     * This test method tests setting type of red king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testRedKingType() {
            System.out.println("Fetching RedKing piece...");
            assertEquals(RedKing.getType(), Type.KING);

    }

    /**
     * This test method tests setting type of white king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testWhiteKingType() {
            System.out.println("Fetching WhiteKing piece...");
            assertEquals(WhiteKing.getType(), Type.KING);
    }

    /**
     * This test method tests setting type of red single
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testRedSingleType() {

            System.out.println("Fetching RedSingle piece...");
            assertEquals(RedSingle.getType(), Type.SINGLE);

    }

    /**
     * This test method tests setting type of white single
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testWhiteSingleType() {

            System.out.println("Fetching WhiteSingle piece...");
            assertEquals(WhiteSingle.getType(), Type.SINGLE);

    }

    /**
     * This test method tests setting a red single to red king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testSetRedKing() {
        RedSingle.setKing();

        System.out.println("Fetching RedSingle piece...");
        assertEquals(RedSingle.getType(), Type.KING);
    }

    /**
     * This test method tests setting a white single to white king
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testSetWhiteKing() {
        WhiteSingle.setKing();

        System.out.println("Fetching WhiteSingle piece...");
        assertEquals(WhiteSingle.getType(), Type.KING);

    }
}
