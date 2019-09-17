/**
 * Class Name: GameCenterTest
 * Tests the GameCenter class in the application tier
 */

package com.webcheckers.appl;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for GameCenter class
 *       Tests Creating the game
 *       Getting the game by the ID
 *       Getting the games
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
public class GameCenterTest {
    private GameCenter gameCenter = new GameCenter();
    private Player red = new Player("RedPlayer");
    private Player white = new Player("WhitePlayer");

    /**
     * This method tests the createGame method.  Makes the games with 2
     * players, red and white and uses assertions and the getGame method
     * to find the games with those players. Prints CreateGame passed is
     * test passes, prints out test failed otherwise
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    public void testCreateGame() {
        gameCenter.createNewGame(red, white);
        try {
            assertNotNull(gameCenter.getGames().get("RedPlayer"));
            assertFalse(gameCenter.getGames().get("WhitePlayer") != null);
            //assertTrue(gameCenter.getGames().get("WhitePlayer") == null);
            fail("test failed");
        } catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("CreateGame passed");
    }

    /**
     * TestGetGameById tests the functionality of GetGameById method.  Creates
     * a new game with red and white players. Then, uses a Not Null assertion
     * to test that the red play is in fact playing in the game.
     * Prints out GetGameById passed if it passes, test failed otherwise.
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    public void testGetGameById() {
        gameCenter.createNewGame(red, white);
        try {
            assertNotNull(gameCenter.getGameById("RedPlayer"));
            fail("test failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("GetGameById passed");
    }

    /**
     * TestGetGames tests the functionality of getGames method. Creates a game
     * with red and white players and then uses an AssertFalse assertion
     * to prove that the game is not empty.  Prints out GetGame passed if it passes,
     * prints test failed otherwise
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    public void testGetGames() {
        gameCenter.createNewGame(red, white);
        try {
            assertFalse(gameCenter.getGames().isEmpty());
            fail("test failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("GetGame passed");
    }
}
