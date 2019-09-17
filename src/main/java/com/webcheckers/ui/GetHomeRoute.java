package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.GameWinner;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;
  protected static final String PLAYERLIST_KEY = "players";
  protected static final String PLAYERLIST_SIZE_KEY = "numPlayers";
  protected PlayerLobby playerLobby;
  protected GameCenter center;
  static final String FINISHEDGAMES_KEY = "game";
  static final String TITLE_KEY = "title";


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  protected GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine,  GameCenter center) {
    Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
    this.center = center;
    //
    LOG.config("GetHomeRoute is initialized.");
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
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    Session httpSession = request.session();

    Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);

    if (current != null) {
      if (current.isChallenged()) {
        response.redirect(WebServer.GAME_URL);
      }
      if (center.getGameById(current.getGameID()) != null) {
        CheckerGame lastGame = center.getGameById(current.getGameID());
        GameWinner winner = lastGame.getWinner();
        if ((winner == GameWinner.red && current == lastGame.getRedPlayer()) || (winner == GameWinner.white && current == lastGame.getWhitePlayer())) {
          Player opponent = center.getGameById(current.getGameID()).getOpponent(current);
          opponent.setGameID(null);
          current.setGameID(null);
          opponent.setStateWaiting();
          current.setStateWaiting();
          center.removeGame(current.getGameID());
          httpSession.attribute("lastGame", lastGame);
        }
      }
      System.out.println(current.getName());
    }

    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_KEY, "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    if (request.queryParams("error") != null && request.queryParams("error").equals("783")) {
      vm.put("error", "The player is unavailable");
    }

    // render the View
    vm.put(WebServer.PLAYER_SESSION_KEY, httpSession.attribute(WebServer.PLAYER_SESSION_KEY));
    vm.put(PLAYERLIST_KEY, playerLobby.getPlayers());
    vm.put(FINISHEDGAMES_KEY, httpSession.attribute("lastGame"));
    vm.put(PLAYERLIST_SIZE_KEY, playerLobby.getPlayers().size()-2);
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}