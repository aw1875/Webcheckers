// Class name: GetReplayStopRouteTest
// Description: This class tests for the GetReplayStopRoute class
package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the {@link GetReplayStopRoute} class
 * @author Ayush Rout
 * @version 1.1
 * @since 1.0
 */
@Tag("UI-Tier")
class GetReplayStopRouteTest {
    private GetReplayStopRoute getReplayStopRoute;
    private Request request;
    private Session session;
    private Response response;
    private Gson gson;
    private GameCenter gameCenter;
    private Player current;
    private Player other;

    /**
     * This method initializes the environment for testing
     */
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
        getReplayStopRoute = new GetReplayStopRoute(gson, gameCenter);
    }

    /**
     * Test for when player is waiting and not watching replay(already exited)
     * @throws Exception if failed
     */
    @Test
    void exitReplayTest() throws Exception {
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        getReplayStopRoute.handle(request, response);
        assertTrue(current.isWaiting());
        System.out.println("Tested GetReplayStopRoute...");
    }
}
