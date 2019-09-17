// PlayerTest.java
// Used to test the functions used in the Player object
// Author: couchcoders
package com.webcheckers.model;

import com.webcheckers.model.enums.PlayerState;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
public class PlayerTest {
    private String name = "Test";
    private Player tp = new Player(name);

    @Test
    void testgetName()
    {
        try
        {
            System.out.println("Reading current player name...");
            assertEquals(tp.getName(), "Test");
            System.out.println("Player name: " + tp.getName());
            fail("[!] Player name does not match expected value");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("setName() test finished");
    }

    @Test
    void testsetStateWaiting()
    {
        try
        {
            System.out.println("Setting player state to 'waiting'...");
            tp.setStateWaiting();
            assertEquals(tp.isWaiting(), true);
            fail("[!] Player state does not match expected value");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("setStateWaiting() test finished");
    }

    @Test
    void testsetStateInGame()
    {
        try
        {
            System.out.println("Setting player state to 'in game'...");
            tp.setStateInGame();
            assertEquals(tp.isInGame(), true);
            fail("[!] Player state does not match expected value");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("setStateInGame() test finished");
    }

    @Test
    void testsetStateChallenged()
    {
        try
        {
            System.out.println("Setting player state to 'challenged'...");
            tp.setStateChallenged();
            assertEquals(tp.isChallenged(), true);
            fail("[!] Player state does not match expected value");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("setStateChallenged() test finished");
    }

    @Test
    void testtoString()
    {
        try
        {
            System.out.println("Testing initial player state...");
            assertNull(tp.toString());
            fail("[!] Player state must be 'waiting'");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        try
        {
            System.out.println("Setting player state to 'ingame'...");
            tp.setStateInGame();
            assertEquals(tp.toString(), PlayerState.INGAME.name() + "  ");
            fail("[!] Player state must be 'ingame'");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("toString() test finished");
    }

}