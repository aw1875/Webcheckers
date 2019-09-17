package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
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
class PostValidateMoveTest {
    private PostValidateMoveRoute postValidateMoveRoute;
    private Request request;
    private Response response;
    private Session session;
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
        current = new Player("Player1");
        other = new Player("Player2");
        gameCenter = new GameCenter();
        gson = new Gson();
        postValidateMoveRoute = new PostValidateMoveRoute(gson, gameCenter);
    }

    @Test
    void ValidMoveTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        CheckerGame checkerGame = gameCenter.getGameById(current.getGameID());
        Move move = new Move(new Position(5, 4), new Position(4, 3));
        checkerGame.pushMove(move);
        String json;
        try {
            json = (String) postValidateMoveRoute.handle(request, response);
            Message message = gson.fromJson(json, Message.class);
            assertEquals("Valid move", message.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void noMovesLeftTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        CheckerGame checkerGame = gameCenter.getGameById(current.getGameID());
        Move move = new Move(new Position(5, 4), new Position(4, 3));
        String json;
        try {
            json = (String) postValidateMoveRoute.handle(request, response);
            Message message = gson.fromJson(json, Message.class);
            assertEquals("You are out of moves", message.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
