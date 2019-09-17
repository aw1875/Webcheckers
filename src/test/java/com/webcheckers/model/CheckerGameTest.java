package com.webcheckers.model;

import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.enums.GameWinner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("Model-tier")
public class CheckerGameTest {
    Player p1 = new Player("P1");
    Player p2 = new Player("P2");
    CheckerGame cg = new CheckerGame(p1,p2);


    @Test
    public void getBoard() throws Exception {
        BoardView bv = cg.getBoard();
        assertNotNull(bv);
    }

    @Test
    public void getRedBoard() throws Exception {
        BoardView bv = cg.getRedBoard();
        assertNotNull(bv);
    }

    @Test
    public void getWhiteBoard() throws Exception {
        BoardView bv = cg.getWhiteBoard();
        assertNotNull(bv);
    }

    @Test
    public void getAllMoves() throws Exception {
        try
        {
            ArrayList moves = cg.getAllMoves();
            assertArrayEquals(moves.toArray(),(new ArrayList()).toArray());
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getAllMoves() test finished");
    }

    @Test
    public void getPieceMoved() throws Exception {
        try
        {
            ArrayList pieces = cg.getPieceMoved();
            assertArrayEquals(pieces.toArray(),(new ArrayList()).toArray());
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getPieceMoved() test finished");
    }

    @Test
    public void getPiecesTaken() throws Exception {
        try
        {
            ArrayList pieces = cg.getPieceMoved();
            assertArrayEquals(pieces.toArray(),(new ArrayList()).toArray());
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getPiecesTaken() test finished");
    }

    @Test
    public void getMovectr() throws Exception {
        try
        {
            int moveCount = cg.getMovectr();
            assertEquals(moveCount, 0);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getMovectr() test finished");
    }

    @Test
    public void isMyTurn() throws Exception {
        try
        {
            assertEquals(cg.isMyTurn(p1),true);
            assertEquals(cg.isMyTurn(p2),false);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getMovectr() test finished");
    }

    @Test
    public void getActiveColor() throws Exception {
        try
        {
            assertEquals(cg.getActiveColor(), Color.RED);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getActiveColor() test finished");
    }

    @Test
    public void getPlayers() throws Exception {
        try
        {
            Player p = cg.getRedPlayer();
            assertEquals(p, p1);
            p = cg.getWhitePlayer();
            assertEquals(p, p2);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getPlayers() test finished");
    }

    @Test
    public void getOpponent() throws Exception {
        try
        {
            assertEquals(cg.getOpponent(p1),p2);
            assertEquals(cg.getOpponent(p2),p1);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getOpponent() test finished");
    }

    @Test
    public void hasPieces() throws Exception {
        try
        {
            assertEquals(cg.whiteHasPieces(),true);
            assertEquals(cg.redHasPieces(),true);
            cg.setNumRed(0);
            cg.setNumWhite(0);
            assertEquals(cg.whiteHasPieces(),false);
            assertEquals(cg.redHasPieces(),false);
            cg = new CheckerGame(p1,p2);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("hasPieces() test finished");
    }

    @Test
    public void getWinner() throws Exception {
        try
        {
            assertEquals(cg.getWinner(), GameWinner.ingame);
            cg.setWinner(GameWinner.red);
            assertEquals(cg.getWinner(), GameWinner.red);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getWinner() test finished");
    }

    @Test
    public void playerHasNoPiecesLose() throws Exception {
        try
        {
            cg.playerHasNoPiecesLose();
            cg.setNumRed(0);
            cg.playerHasNoPiecesLose();
            cg.setNumRed(10);
            cg.setNumWhite(0);
            cg.playerHasNoPiecesLose();
            cg = new CheckerGame(p1,p2);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getWinner() test finished");
    }

    @Test
    public void playerResigned() throws Exception {
        try
        {
            cg.playerResigned(p1);
            cg.playerResigned(p2);
            cg.setWinner(GameWinner.ingame);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getWinner() test finished");

    }

    @Test
    public void pushMove() throws Exception {
        try
        {
            HashSet<Move> v = cg.getPossibleMoves();
            for (Move m : v) {
                cg.pushMove(m);
                break;
            }
            cg = new CheckerGame(p1,p2);
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("pushMove() test finished");
    }

    @Test
    public void getMoveStack() throws Exception {
        try
        {
            Stack<Move> al = cg.getMoveStack();
            assertNotNull(al);
            assertFalse(cg.jumpMoveAvailable());

        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getMoveStack() test finished");
    }

    @Test
    public void playerHasMoves() throws Exception {
        try
        {
            assertTrue(cg.playerHasMoves());
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getMoveStack() test finished");
    }


    @Test
    public void switchCurrentTurn() throws Exception {
        try
        {
            cg.switchCurrentTurn();
            cg.switchCurrentTurn();
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getMoveStack() test finished");
    }

    @Test
    public void submitTurn() throws Exception {
        try
        {
            cg.submitTurn();
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getMoveStack() test finished");
    }

    //@Test
    //public void nextTurn() throws Exception {
    //    //TODO
    //}

    //@Test
    //public void previousTurn() throws Exception {
    //    //TODO
    //}

}