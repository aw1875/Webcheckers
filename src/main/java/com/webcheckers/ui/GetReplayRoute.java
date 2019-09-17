package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.viewMode;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    private Gson gson;
    private GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    static final String TITLE_ATTR = "title";
    static final String REDPLAYER_ATTR = "redPlayer";
    static final String WHITEPLAYER_ATTR = "whitePlayer";
    static final String ACTIVECOLOR_ATTR = "activeColor";
    static final String VIEW_ATTR = "viewMode";
    static final String BOARD_ATTR = "board";
    static final String MESSAGE_ATTR = "message";
    static final String MODE_ATTR = "modeOptionsAsJSON";
    static final String NEWGAME_ATTR = "newGame";

    /**
     * The UI Constructor for the GameReplayRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     * @param templateEngine TemplateEngine type for HTTP template rendering
     */
    public GetReplayRoute (Gson gson, GameCenter gameCenter, final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.gson = gson;
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
        LOG.config("GetReplayRoute is initialized.");
    }

    /**
     * Renders the Game Replay route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type representing the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetReplayRoute is invoked.");
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame newGame;
        if (!(current.isReplay())) {
            CheckerGame lastGame = httpSession.attribute("lastGame");
            newGame = new CheckerGame(lastGame.getRedPlayer(), lastGame.getWhitePlayer(), lastGame.getAllMoves(), lastGame.getPiecesTaken(), lastGame.getPieceMoved());
            httpSession.attribute(NEWGAME_ATTR, newGame);
        }
        else {
            newGame = httpSession.attribute(NEWGAME_ATTR);
        }
        current.setStateReplay();
        vm.put(WebServer.PLAYER_SESSION_KEY, current);
        vm.put(TITLE_ATTR, "Replay");
        vm.put(VIEW_ATTR, viewMode.REPLAY);
        vm.put(REDPLAYER_ATTR, newGame.getRedPlayer());
        vm.put(WHITEPLAYER_ATTR, newGame.getWhitePlayer());
        vm.put(ACTIVECOLOR_ATTR, newGame.getActiveColor());

        if(current == newGame.getRedPlayer()) {
            vm.put(BOARD_ATTR, newGame.getRedBoard());
        }
        else {
            vm.put(BOARD_ATTR, newGame.getWhiteBoard());
        }

        Map<String, Object> modeOptions = new HashMap<>();
        modeOptions.put("hasNext", false);
        modeOptions.put("hasPrevious", false);

        if(newGame.getMovectr() < newGame.getAllMoves().size()) {
            modeOptions.put("hasNext", true);
        }
        if (newGame.getMovectr() > 0) {
            modeOptions.put("hasPrevious", true);
        }
        vm.put(MODE_ATTR, gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
