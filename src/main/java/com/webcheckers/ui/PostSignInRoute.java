// Class Name: PostSignInRoute
// Description: This class is the UI controller to POST the Sign In page.
package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the Sign In page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='https://github.com/axr6077'>Ayush Rout</a>
 * @author couchcoders
 * @version 1.1
 * @since 1.0
 */
public class PostSignInRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    protected static final String TITLE = "Sign In";
    protected static final String FAIL_KEY = "signInFail";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param playerLobby
     *   PlayerLobby object that holds and manages critical details
     *   about the players online
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        LOG.config("PostSignInRoute Initialized.");
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
        LOG.finer("PostSignInRoute is invoked");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", TITLE);

        final Session session = request.session();

        final String username = request.queryParams("username");
        if(username.length() > 0) {
            if(username.length() >= 25) {
                vm.put(FAIL_KEY, "Please choose your username less than 25 characters");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            }
            if(this.playerLobby.userNameExists(username)) {
                vm.put(FAIL_KEY, "Username already exists");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            }
            else if (this.playerLobby.hasSpecialChar(username)){
                vm.put(FAIL_KEY, "No special characters allowed!");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            }
            else {
                Player player = new Player(username);
                this.playerLobby.addPlayer(player);
                session.attribute(WebServer.PLAYER_SESSION_KEY, player);
                response.redirect(WebServer.HOME_URL);
                player.setStateWaiting();
                return null;
            }
        }
        else {
            vm.put(FAIL_KEY, "Please enter a valid username");
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}
