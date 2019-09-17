// Class Name: BoardViewTest
// Description: JUnit test for BoardView Class
package com.webcheckers.model.Board;

import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.Board.enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
/**
 * This class tests the functionality of the BoardView class
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
class BoardViewTest {
    private BoardView boardView = new BoardView();
    private Move move1 = new Move(new Position(2,1), new Position(3,2));
    private Move move2 = new Move(new Position(3,2), new Position(4,1));
    private Move move3 = new Move(new Position(4,1), new Position(5,2));
    private Move move4 = new Move(new Position(5,2), new Position(6,1));
    private Move move5 = new Move(new Position(6,1), new Position(7,2));
    private Move move6 = new Move(new Position(7,2), new Position(6,1));

    /**
     * This test method tests the white player view
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testGetWhitePlayerView() {
        System.out.println("fetching WhitePlayerView...");
        assertTrue(boardView.getWhitePlayerView() instanceof BoardView);

    }

    /**
     * This test method tests the red player view
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testGetRedPlayerView() {

        assertTrue(boardView.getRedPlayerView() instanceof BoardView);

    }

    /**
     * This test method tests the getBoard function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testgetBoard() {
        System.out.println("fetching boards...");
        assertTrue(boardView.getBoard() != null);

    }

    /**
     * This test method tests the Iterator implementable
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testIterator() {
        Iterator iter  = boardView.iterator();
        for (int x=0;x<8;x++) {
            assertTrue(iter.hasNext());
            assertEquals(iter.next(), boardView.getBoard().get(x));
        }
        assertFalse(iter.hasNext());

    }

    /**
     * This test method tests the RemovePiece function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testRemovePiece() {
        boardView.getBoard().get(5).getSpace(2).removePiece();
        boardView.getBoard().get(6).getSpace(1).removePiece();
        boardView.getBoard().get(7).getSpace(2).removePiece();
    }

    /**
     * This test method tests the movePiece function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testmovePiece() {
        boardView.movePiece(move1);
        boardView.movePiece(move2);
        boardView.movePiece(move3);
        boardView.movePiece(move4);
        boardView.movePiece(move5);
        System.out.println("Setting the piece type to KING");
        assertEquals(boardView.getBoard().get(7).getSpace(2).getPiece().getType(), Type.KING);
        boardView.movePiece(move6);
        boardView.movePiece(move5);
        System.out.println("The piece type is still KING");
        assertEquals(boardView.getBoard().get(7).getSpace(2).getPiece().getType(), Type.KING);
    }
}
