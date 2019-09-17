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
class PostBackupMoveRouteTest {
    private PostBackupMoveRoute postBackupMoveRoute;
    private PlayerLobby playerLobby;
    private Request request;
    private Response response;
    private Session session;
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
        postBackupMoveRoute = new PostBackupMoveRoute(gson, gameCenter);
    }

    @Test
    void noMovesTest() throws Exception {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);

        String json = (String)postBackupMoveRoute.handle(request, response);
        Message message = gson.fromJson(json, Message.class);
        assertEquals("No moves have been made", message.getText());
        System.out.println("tested no backup moves...");
    }

    @Test
    void  goodMovesTest() throws Exception {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        gameCenter.createNewGame(current, other);
        CheckerGame checkerGame = gameCenter.getGameById(current.getGameID());
        checkerGame.pushMove(new Move(new Position(2,2), new Position(3,3)));
        String json = (String)postBackupMoveRoute.handle(request, response);
        Message message = gson.fromJson(json, Message.class);
        assertEquals("One move undone.", message.getText());
        System.out.println("tested valid backup moves...");
    }
}
