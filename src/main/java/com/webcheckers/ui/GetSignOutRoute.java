// Class Name: GetSignOutRoute
// Description: This class is the UI controller to GET the Sign Out page.
package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Sign Out page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='https://github.com/axr6077'>Ayush Rout</a>
 * @author couchcoders
 * @version 1.1
 * @since 1.0
 */
public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignOutRoute.class.getName());
    private final PlayerLobby playerLobby;
    public static String TITLE = "Signing out!";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby
     *   PlayerLobby object that holds and manages critical details
     *   about the players online
     */
    public GetSignOutRoute(PlayerLobby playerLobby) {
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        this.playerLobby = playerLobby;
        LOG.config("GetSignOutRoute is initialized");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignOutRoute is invoked");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", TITLE);
        final Session session = request.session();
        Player current = session.attribute(WebServer.PLAYER_SESSION_KEY);
        playerLobby.removePlayer(current.getName());
        session.attribute(WebServer.PLAYER_SESSION_KEY, null);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
