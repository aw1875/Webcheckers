/**
 * Class Name: PlayerLobbyTest
 * Tests the PlayerLobby Class in the application tier
 */

package com.webcheckers.appl;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for PlayerLobby class
 *      Tests Adding a Player
 *      Tests to see if the userName already exists
 *      Tests Getting a player
 *      Tests if there any special characters in the user name
 *      Tests removing a player
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
public class PlayerLobbyTest {
    private PlayerLobby lobby = new PlayerLobby();
    private Player Taylor = new Player("TaylorHiggins");

    /**
     * TestAddPlayer tests the functionality of testAddPlayer in the PlayerLobby
     * Class. First tests to see without adding any players there are 0 to begin with.
     * Nest adds Mike to the lobby and asserts that there is now 1 player in the lobby.
     * Prints CreateGame passed if passes, test failed otherwise
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    public void testAddPlayer() {
        try {
            assertEquals(lobby.getPlayers().size(), 0);
            fail("test failed");
        }
        catch (Throwable ex){
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("Adding 1 player: Mike");
        lobby.addPlayer(new Player("Mike"));
        System.out.println("Mike added to lobby");
        try {
            assertEquals(lobby.getPlayers().size(), 1);
            fail("test failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("TestAddPlayer passed");
    }

    /**
     * TestUserNameExists tests the functionality of userNameExists from the PlayerLobby
     * Class. The method starts by adding 2 players to the lobby and then asserts that
     * there are 2 players in the lobby.  Then assertsTrue that there are 2 players in
     * the lobby with the username. Prints userNameExists passed if passes, test failed
     * otherwise
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    public void testUserNameExists() {
        assertFalse(lobby.userNameExists("Taylor"));
        try {
            assertEquals(lobby.getPlayers().size(), 0);
            lobby.addPlayer(new Player("ayush"));
            assertEquals(lobby.getPlayers().size(), 1);
            lobby.addPlayer(new Player("mike"));
            assertEquals(lobby.getPlayers().size(), 2);
            assertTrue(lobby.userNameExists("ayush"));
            assertTrue(lobby.userNameExists("mike"));
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("userNameExists passed");
    }

    /**
     * TestGetPlayer tests the functionality of GetPlayer from the PlayerLobby Class.
     * First asserts that no player exists in the Lobby.  Then will add Ayush and Taylor as Players
     * and then assert that the Player Taylor matches the player TaylorHiggins.
     * Finally asserts that the player lobby's size is one. Print GetPlayer passed if passes, test
     * failed otherwise.
     * @throws AssertionFailedError not thrown as no assertion failed
     * @throws NullPointerException not thrown as null is asserted with non-existent player in lobby
     */
    @Test
    public void testGetPlayer() {
        assertFalse(lobby.userNameExists("ayush"));
        try {
            assertNull(lobby.getPlayer("ayush"));
        }
        catch (Throwable ex) {
            assertEquals(NullPointerException.class, ex.getClass());
        }
        System.out.println("lobby is currently null(empty)");
        try {
            lobby.addPlayer(new Player("ayush"));
            lobby.addPlayer(Taylor);
            assertEquals(lobby.getPlayer("TaylorHiggins"), Taylor);
            assertEquals(lobby.getPlayers().size(), 1);
        }
        catch (Throwable ex){
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("GetPlayer passed");
    }

    /**
     * TestHasSpecialChar tests the functionality of hasSpecialChar from the
     * PlayerLobby Class.  First checks that "taylor" has no special characters.
     * Then asserts that t@yl0R!! does have special characters.  Prints
     * hasSpecialChar passed if passes, prints test failed otherwise.
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    public void testHasSpecialChar() {
        assertFalse(lobby.hasSpecialChar("taylor"));
        try {
            assertTrue(lobby.hasSpecialChar("t@yl0R!!"));
            fail("test failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("hasSpecialChar passed");
    }

    /**
     * TestRemovePlayer tests the functionality of RemovePlayer from PlayerLobby
     * Class. Adds Patric, and Adam to the lobby, while checking the size of the Lobby
     * Then attempts to remove Adam from the lobby and checks to prove the size is one and
     * Adam is no longer in the lobby, then does the same for Patric.  Prints RemovePlayer
     * passed if passes, test failed otherwise.
     * @throws AssertionFailedError not thrown as no assertion failed
     */

    @Test
    public void testRemovePlayer() {
        try {
            assertEquals(lobby.getPlayers().size(), 0);
            System.out.println("Adding Patrick to lobby...");
            lobby.addPlayer(new Player("Patrick"));
            assertEquals(lobby.getPlayers().size(), 1);
            System.out.println("Number of Players: " + lobby.getPlayers().size());
            System.out.println("Adding Adam to lobby...");
            lobby.addPlayer(new Player("Adam"));
            assertEquals(lobby.getPlayers().size(), 2);
            System.out.println("Number of Players: " + lobby.getPlayers().size());
            System.out.println("Removing Adam from lobby...");
            lobby.removePlayer("Adam");
            assertEquals(lobby.getPlayers().size(), 1);
            System.out.println("Number of Players: " + lobby.getPlayers().size());
            System.out.println("Removing Patrick from lobby...");
            lobby.removePlayer("Patrick");
            assertEquals(lobby.getPlayers().size(), 0);
            System.out.println("Number of Players: " + lobby.getPlayers().size());
            fail("test failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        System.out.println("RemovePlayer passed");
    }
}
