package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.GameWinner;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostCheckTurnRouteTest {
    private PostCheckTurnRoute postCheckTurnRoute;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Gson gson;
    private Player current;
    private Player other;

    @BeforeEach
    void init() {
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        current = new Player("Player1");
        other = new Player("Player2");
        gameCenter = new GameCenter();
        postCheckTurnRoute = new PostCheckTurnRoute(gson, gameCenter);
    }

    @Test
    void baseTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        CheckerGame checkerGame = gameCenter.getGameById(current.getGameID());
        String json = null;
        try {
            json = (String) postCheckTurnRoute.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message message = gson.fromJson(json, Message.class);

        assertEquals(true, checkerGame.isMyTurn(current));
        assertEquals(GameWinner.ingame, checkerGame.getWinner());
    }

    @Test
    void inGameTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        String json = null;
        try {
            json = (String) postCheckTurnRoute.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message message = gson.fromJson(json, Message.class);
        assertEquals("true", message.getText());
    }

    @Test
    void notInGameTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        CheckerGame checkerGame = gameCenter.getGameById(current.getGameID());
        checkerGame.setWinner(GameWinner.red);
        String json = null;
        try {
            json = (String) postCheckTurnRoute.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message message = gson.fromJson(json, Message.class);
        assertEquals("true", message.getText());
    }
}
