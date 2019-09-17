package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.GameWinner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostResginRouteTest {
    private PostResignRoute postResignRoute;
    private GameCenter gameCenter;
    private Request request;
    private Session session;
    private Response response;
    private Player current;
    private Player other;
    private Gson gson;
    private CheckerGame game;

    @BeforeEach
    void init() {
        gameCenter = mock(GameCenter.class);
        gson = new Gson();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        current = new Player("Player1");
        other = new Player("Player2");
        game = new CheckerGame(current, other);
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        when(gameCenter.getGameById(current.getGameID())).thenReturn(game);
        postResignRoute = new PostResignRoute(gson, gameCenter);
    }

    @Test
    void currentPlayerWaitingTest() throws Exception {
        postResignRoute.handle(request, response);
        assertTrue(current.isWaiting());
        System.out.println("Current Player waiting tested...");
    }

    @Test
    void opponentWon() throws Exception {
        postResignRoute.handle(request, response);
        assertTrue(game.getWinner() == GameWinner.white);
        System.out.println("Opponent won tested...");
    }

    @Test
    void opponentTurn() throws Exception {
        postResignRoute.handle(request, response);
        assertTrue(game.isMyTurn(other));
        assertTrue(game.getActiveColor() == Color.WHITE);
        System.out.println("Opponent turn tested...");
    }
}
