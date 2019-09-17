package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostReplayPrevTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostReplayPrevTurnRoute.class.getName());
    static final String NEWGAME_ATTR = "newGame";
    private Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the PostRelayPrevousTurnRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public PostReplayPrevTurnRoute(Gson gson, GameCenter gameCenter) {
        this.gson = gson;
        this.gameCenter = gameCenter;
        LOG.config("PostReplayPrevTurnRoute is initialized.");
    }

    /**
     * Renders the Game Replay Previous Turn route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type that represents the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostReplayPrevTurnRoute is invoked.");
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame newGame = httpSession.attribute(NEWGAME_ATTR);
        Message ret = newGame.previousTurn();
        return gson.toJson(ret);
    }
}
