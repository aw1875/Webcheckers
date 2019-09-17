// Class name: GetHomeRouteTest
// Description: This class tests for the GetHomeRoute class
package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the {@link GetHomeRoute} class
 * @author Ayush Rout
 * @version 1.1
 * @since 1.0
 */
@Tag("UI-tier")
class GetHomeRouteTest {
    private GetHomeRoute getHomeRoute;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private GameCenter gameCenter;

    /**
     * This method initializes the players, playerLobby, gameCenter, templateEngine and other components for testing
     */
    @BeforeEach
    void init() {
        playerLobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
        getHomeRoute = new GetHomeRoute(playerLobby, templateEngine, gameCenter);
    }

    /**
     * This method tests if player lobby initially has no current player and is signed out.
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testSignOut() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("testing Signed Out...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(null);
            getHomeRoute.handle(request, response);
            templateEngineTester.assertViewModelExists();
            templateEngineTester.assertViewModelIsaMap();
            templateEngineTester.assertViewModelAttribute("title", "title");
            templateEngineTester.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_KEY, playerLobby.getPlayers());
            templateEngineTester.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_SIZE_KEY, playerLobby.getPlayers().size());
            templateEngineTester.assertViewModelAttribute(WebServer.PLAYER_SESSION_KEY, null);
            templateEngineTester.assertViewName("home.ftl");
            fail("sign out failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Signed Out successfully tested");
        }
    }

    /**
     * This method tests if player can successfully sign in and be added to playerLobby.
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testSignIn() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        Player currPlayer = new Player("Ayush");
        Player otherPlayer = new Player("Ghost");
        try {
            System.out.println("testing sign in...");
            System.out.println("Adding " + currPlayer.getName() + " and " + otherPlayer.getName() + " to player lobby.");
            playerLobby.addPlayer(currPlayer);
            playerLobby.addPlayer(otherPlayer);
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(playerLobby.getPlayer("Ayush"));
            getHomeRoute.handle(request, response);
            templateEngineTester.assertViewModelExists();
            templateEngineTester.assertViewModelIsaMap();

            templateEngineTester.assertViewModelAttribute(WebServer.PLAYER_SESSION_KEY, session.attribute(WebServer.PLAYER_SESSION_KEY));
            templateEngineTester.assertViewModelAttribute("title", "title");
            templateEngineTester.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_KEY, playerLobby.getPlayers());
            templateEngineTester.assertViewModelAttribute(GetHomeRoute.PLAYERLIST_SIZE_KEY, 0);
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Sign in successfully tested");
        }
    }

    /**
     * This method tests if a game is removed from gameCenter.
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testRemoveGame() {
        Player currPlayer = new Player("Ayush");
        Player otherPlayer = new Player("Ghost");
        gameCenter = new GameCenter();
        gameCenter.createNewGame(currPlayer, otherPlayer);
        CheckerGame checkerGame = gameCenter.getGameById(currPlayer.getGameID());
        try {
            System.out.println("testing remove game from game center...");
            when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(currPlayer);
            getHomeRoute.handle(request, response);
            assertEquals(gameCenter.getGameById(currPlayer.getGameID()).getOpponent(currPlayer), otherPlayer);
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("remove game from game center successfully tested");
        }
    }
}
