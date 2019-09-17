package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
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
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostSubmitTurnRouteTest {
    private PostSubmitTurnRoute postSubmitTurnRoute;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;
    private Player current;
    private Player other;

    @BeforeEach
    void init() {
        playerLobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        current = new Player("Player1");
        other = new Player("Player2");
        gameCenter = new GameCenter();
        postSubmitTurnRoute = new PostSubmitTurnRoute(gson, gameCenter);
    }

    @Test
    void submitTurnTest() {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        CheckerGame checkerGame = gameCenter.getGameById(current.getGameID());
        checkerGame.pushMove(new Move(new Position(2, 2), new Position(3, 3)));
        String json = null;
        try {
            json = (String) postSubmitTurnRoute.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message message = gson.fromJson(json, Message.class);
        assertEquals("All moves submitted", message.getText());
    }
}
