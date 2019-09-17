// Class Name: SpaceTest
// Description: JUnit test for Space Object
package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
/**
 * This class tests the functionality of the Space object
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
class SpaceTest {
    private Space white = new Space(0, false);
    private Space black = new Space(1, true);
    private Piece piece = new Piece(Type.SINGLE, Color.RED);
    private Piece NULL_PIECE = new Piece(null, null);

    /**
     * This test method tests the CreatePiece function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testCreatePiece() {
        black.createPiece(Type.SINGLE, Color.RED);
        try {
            System.out.println("Retrieving red piece on black space...");
            System.out.println("Testing color...");
            assertEquals(black.getPiece().getColor(), piece.getColor());
            System.out.println("Color: " + black.getPiece().getColor());
            System.out.println("Testing type...");
            assertEquals(black.getPiece().getType(), piece.getType());
            System.out.println("Type: " + black.getPiece().getType());
            try {
                black.createPiece(Type.SINGLE, Color.WHITE);
                System.out.println("Retrieving white piece on black space...");
                System.out.println("Testing color...");
                assertEquals(black.getPiece().getColor(), piece.getColor());
                System.out.println("Color: WHITE");
                System.out.println("Testing type...");
                assertEquals(black.getPiece().getType(), piece.getType());
                System.out.println("Type: " + black.getPiece().getType());
                fail("Wrong piece on space");
            }
            catch (Throwable ex) {
                assertEquals(AssertionFailedError.class, ex.getClass());
            }
            finally {
                System.out.println("Successfully fetched white piece on black space");
            }
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Successfully fetched red piece on black space");
        }
        System.out.println("Successfully tested CreatePiece");
    }

    /**
     * This test method tests the isValid function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testisValid() {
        try {
            System.out.println("Testing validity of white space");
            assertFalse(white.isValid());
            System.out.println("White space validity: " + white.isValid());
            fail("White should be invalid");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("isValid successfully tested");
        }
    }

    /**
     * This test method tests the setPiece function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testsetPiece () {
        try {
            assertTrue(black.isValid());
            System.out.println("Setting a piece on black space...");
            black.setPiece(piece);
            System.out.println("Retrieving piece type...");
            assertEquals(black.getPiece().getType(), piece.getType());
            System.out.println("Type: " + piece.getType());
            System.out.println("Retrieving piece color...");
            assertEquals(black.getPiece().getColor(), piece.getColor());
            System.out.println("Color: " + piece.getColor());
            System.out.println("Testing availability of this occupied space");
            assertFalse(black.isValid());
            System.out.println("Space unavailable");
            fail();
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Successfully placed a piece on black space and changed availability status to occupied");
            try {
                System.out.println("Setting piece at this space to null and changing availability status to valid");
                black.setPiece(NULL_PIECE);
                System.out.println("Type: " + black.getPiece().getType());
                assertNull(black.getPiece().getType());
                System.out.println("Color: " + black.getPiece().getColor());
                assertNull(black.getPiece().getColor());
                fail("This space should be null and valid");
            }
            catch (Throwable ex) {
                assertEquals(AssertionFailedError.class, ex.getClass());
            }
            finally {
                System.out.println("Successfully tested isValid");
            }
        }
    }

    /**
     * This test method tests the toString function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testtoString() {
        try {
            System.out.println("Testing toString...");
            black.setPiece(piece);
            System.out.println(black.toString());
            assertEquals(black.toString(), "Space: 1, Piece: SINGLE");
            System.out.println(white.toString());
            assertEquals(white.toString(), "Space: 0, Piece: NONE");
            fail("String error");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Successfully tested toString");
        }
    }
}
