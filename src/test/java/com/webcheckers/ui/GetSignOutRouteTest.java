// Class name: GetSignOutRouteTest
// Description: This class tests for the GetSignOutRoute class
package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
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
 * This class tests the {@link GetSignOutRoute} class
 * @author Ayush Rout
 * @version 1.1
 * @since 1.0
 */
@Tag("UI-tier")
class GetSignOutRouteTest {
    private GetSignOutRoute getSignOutRoute;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private Player current;

    /**
     * This method initializes the environment for testing
     */
    @BeforeEach
    void init() {
        playerLobby = new PlayerLobby();
        session = mock(Session.class);
        request = mock(Request.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        current = new Player("Player1");
        getSignOutRoute = new GetSignOutRoute(playerLobby);
    }

    /**
     * This method tests the sign out functionality of the web page.
     */
    @Test
    void signOutTest() {
        final TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(session.attribute(WebServer.PLAYER_SESSION_KEY)).thenReturn(current);
        getSignOutRoute.handle(request, response);
        assertTrue(!playerLobby.userNameExists("Player1"));
        System.out.println("Tested GetSignOutRoute");
    }
}
