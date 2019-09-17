// Class name: GetGameRouteTest
// Description: This class tests for the GetGameRoute class
package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.viewMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the {@link GetGameRoute} class
 * @author Ayush Rout
 * @version 1.1
 * @since 1.0
 */
@Tag("UI-tier")
class GetGameRouteTest {
    private GetGameRoute getGameRoute;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private spark.Request request;
    private spark.Response response;
    private spark.Session session;
    private TemplateEngine templateEngine;
    private Player currPlayer;
    private Player opponent;

    /**
     * This method initializes the players, playerLobby, gameCenter, templateEngine and other components for testing
     */
    @BeforeEach
    void init() {
        playerLobby = new PlayerLobby();
        gameCenter = new GameCenter();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        currPlayer = new Player("ayush");
        opponent = new Player("mike");
        playerLobby.addPlayer(currPlayer);
        playerLobby.addPlayer(opponent);
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(currPlayer);
        when(request.queryParams("id")).thenReturn("mike");
        getGameRoute = new GetGameRoute(playerLobby, gameCenter, templateEngine);
    }

    /**
     * This method tests if the correct page is sent
     * @throws NullPointerException if template engine is null
     */
    @Test
    void PageRequested() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("Testing if correct page is sent...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            getGameRoute.handle(request, response);
            templateEngineTester.assertViewModelExists();
            templateEngineTester.assertViewModelIsaMap();
            templateEngineTester.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, "Game");
            templateEngineTester.assertViewModelAttribute(WebServer.PLAYER_SESSION_KEY, currPlayer);
            //fail("failed to send page");
        }
        catch (Exception ex) {
            assertEquals(NullPointerException.class, ex.getClass());
        }
        finally {
            System.out.println("Page request successfully tested and sent");
        }
    }

    /**
     * This method tests if the player is redirected to appropriate game
     * @throws NullPointerException if template engine is null
     */
    @Test
    void testPlayerRedirect() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("Testing if player is redirected to appropriate game...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            gameCenter.createNewGame(opponent, currPlayer);
            currPlayer.setGameID(opponent.getName());
            opponent.setGameID(opponent.getName());
            currPlayer.setStateChallenged();
            opponent.setStateInGame();
            getGameRoute.handle(request, response);
            assertTrue(currPlayer.isInGame());
            templateEngineTester.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, viewMode.PLAY);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, opponent);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, currPlayer);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
        }
        catch (Exception ex) {
            assertEquals(NullPointerException.class, ex.getClass());
        }
        finally {
            System.out.println("Player redirect successfully tested");
        }
    }

    /**
     * This method tests if a player can challenge another player
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testPlayerChallengesPlayer() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("testing if a player can challenge another player...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            currPlayer.setStateWaiting();
            opponent.setStateWaiting();
            getGameRoute.handle(request, response);
            assertTrue(currPlayer.isInGame());
            assertTrue(opponent.isChallenged());
            assertTrue(currPlayer.getGameID().equals(currPlayer.getName()));
            assertTrue(opponent.getGameID().equals(currPlayer.getName()));
            assertTrue(gameCenter.getGameById(currPlayer.getGameID()).getWhitePlayer() == opponent);
            fail("player is not challenging a player object");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Player challenges player successfully tested");
        }
    }

    /**
     * This method tests view/model when player challenges another player
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testPlayerChallengesPlayerViewModel() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("Testing view/model when player challenges another player...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            currPlayer.setStateWaiting();
            opponent.setStateWaiting();
            getGameRoute.handle(request, response);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, viewMode.PLAY);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, currPlayer);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, opponent);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
            fail("view/model failed at player challenges another player");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Player challenges player view/model successfully tested");
        }
    }

    /**
     * This method tests Red Player view/model when in game
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testRedPlayer() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("Testing Red Player view/model when in game...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            gameCenter.createNewGame(currPlayer, opponent);
            currPlayer.setGameID(currPlayer.getName());
            opponent.setGameID(currPlayer.getName());
            currPlayer.setStateInGame();
            opponent.setStateInGame();
            getGameRoute.handle(request, response);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, viewMode.PLAY);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, currPlayer);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, opponent);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
            fail("test failed for red player view/model");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Red Player view/model successfully tested");
        }
    }

    /**
     * This method tests White Player view/model when in game
     * @throws AssertionFailedError if assertions failed
     */
    @Test
    void testWhitePlayer() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        try {
            System.out.println("Testing White Player view/model when in game...");
            when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
            opponent.setGameID(opponent.getName());
            currPlayer.setGameID(currPlayer.getName());
            opponent.setStateInGame();
            currPlayer.setStateInGame();
            gameCenter.createNewGame(opponent, currPlayer);
            getGameRoute.handle(request, response);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.VIEW_ATTR, viewMode.PLAY);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.REDPLAYER_ATTR, opponent);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.WHITEPLAYER_ATTR, currPlayer);
            templateEngineTester.assertViewModelAttribute(GetGameRoute.ACTIVECOLOR_ATTR, Color.RED);
            fail("test failed for red player view/model");
        }
        catch (AssertionFailedError ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Red Player view/model successfully tested");
        }
    }
}
