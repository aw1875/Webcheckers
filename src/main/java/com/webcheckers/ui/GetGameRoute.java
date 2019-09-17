// Class Name: GetGameRoute
// Description: This class is the UI controller to GET the game page.
package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.GameWinner;
import com.webcheckers.model.enums.viewMode;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.util.Message.Type.ERROR;
import static com.webcheckers.util.Message.Type.INFO;
import static spark.Spark.exception;
import static spark.Spark.halt;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='https://github.com/axr6077'>Ayush Rout</a>
 * @author couchcoders
 * @version 1.3
 * @since 1.0
 */
public class GetGameRoute implements Route{
    private static final Logger LOG  = Logger.getLogger(GetGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter center;

    static final String VIEW_ATTR = "viewMode";
    static final String REDPLAYER_ATTR = "redPlayer";
    static final String WHITEPLAYER_ATTR = "whitePlayer";
    static final String ACTIVECOLOR_ATTR = "activeColor";
    static final String MESSAGE_ATTR = "message";
    static final String TITLE_ATTR = "title";
    static final String BOARD_ATTR = "board";
    static final Message YOU_WIN = new Message("You win.", Message.Type.INFO);
    static final Message YOU_LOSE = new Message("You lose.", Message.Type.ERROR);

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(PlayerLobby playerLobby, GameCenter center, final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.center = center;
        LOG.config("GetGameRoute is initialized");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked!");
        Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        //get current user
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        //current.setStateWaiting();
        vm.put(WebServer.PLAYER_SESSION_KEY, current);
        vm.put(TITLE_ATTR, "Game");

        if (current.isChallenged()) {
            current.setStateInGame();
            httpSession.attribute("lastgame", this.center.getGameById(current.getGameID()));
            vm.put (VIEW_ATTR, viewMode.PLAY);
            vm.put(REDPLAYER_ATTR, this.center.getGameById(current.getGameID()).getRedPlayer());
            vm.put(WHITEPLAYER_ATTR, current);
            vm.put(ACTIVECOLOR_ATTR, Color.RED);
            vm.put(BOARD_ATTR, this.center.getGameById(current.getGameID()).getWhiteBoard());
        }

        else if (current.isWaiting()) {
            String name = request.queryParams("id");
            Player opponent = playerLobby.getPlayer(name);
            if (opponent == null || !opponent.isWaiting()) {
                response.redirect(WebServer.HOME_URL + "?error=783");
                halt();
            }

            if (opponent.isBot()) {
                current.setStateInGame();;
                if (opponent.getName().equals(playerLobby.REDBOT)) {
                    this.center.createNewBotGame(opponent, current, current);
                    httpSession.attribute("lastGame", this.center.getGameById(current.getGameID()));
                    vm.put(REDPLAYER_ATTR, opponent);
                    vm.put(WHITEPLAYER_ATTR, current);
                    vm.put(BOARD_ATTR, this.center.getGameById(current.getGameID()).getWhiteBoard());
                }
                else {
                    this.center.createNewBotGame(current, opponent, current);
                    httpSession.attribute("lastGame", this.center.getGameById(current.getGameID()));
                    vm.put(REDPLAYER_ATTR, current);
                    vm.put(WHITEPLAYER_ATTR, opponent);
                    vm.put(BOARD_ATTR, this.center.getGameById(current.getGameID()).getRedBoard());
                }
                vm.put(VIEW_ATTR, viewMode.PLAY);
                vm.put(ACTIVECOLOR_ATTR, Color.RED);
                return templateEngine.render(new ModelAndView(vm, "game.ftl"));
            }

            current.setStateInGame();
            opponent.setStateChallenged();
            this.center.createNewGame(current, opponent);
            current.setGameID(current.getName());
            opponent.setGameID(current.getName());
            httpSession.attribute("lastGame", this.center.getGameById(current.getGameID()));
            vm.put(VIEW_ATTR, viewMode.PLAY);
            vm.put(REDPLAYER_ATTR, current);
            vm.put(WHITEPLAYER_ATTR, opponent);
            vm.put(ACTIVECOLOR_ATTR, Color.RED);
            vm.put(BOARD_ATTR, this.center.getGameById(current.getGameID()).getRedBoard());

        }

        else {
            CheckerGame game = center.getGameById(current.getGameID());
            vm.put(VIEW_ATTR, viewMode.PLAY);
            vm.put(REDPLAYER_ATTR, game.getRedPlayer());
            vm.put(WHITEPLAYER_ATTR, game.getWhitePlayer());
            vm.put(ACTIVECOLOR_ATTR, game.getActiveColor());
            if (current == game.getRedPlayer()) {
                vm.put (BOARD_ATTR, game.getRedBoard());
            }
            else {
                vm.put(BOARD_ATTR, game.getWhiteBoard());
            }
            if (game.getWinner() != GameWinner.ingame) {
                if (game.getWinner() == GameWinner.red) {
                    if (game.getRedPlayer() == current) {
                        vm.put(MESSAGE_ATTR, YOU_WIN);
                    }
                    else {
                        vm.put(MESSAGE_ATTR, YOU_LOSE);
                    }
                }
                else if (game.getWinner() == GameWinner.white) {
                    if (game.getWhitePlayer() == current) {
                        vm.put(MESSAGE_ATTR, YOU_WIN);
                    }
                    else {
                        vm.put(MESSAGE_ATTR, YOU_LOSE);
                    }
                }
            }
        }
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}