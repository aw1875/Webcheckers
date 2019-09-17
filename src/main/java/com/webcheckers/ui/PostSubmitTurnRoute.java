package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the PostSubmitTurnRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public PostSubmitTurnRoute(Gson gson, GameCenter gameCenter) {
        LOG.config("PostSubmitTurnRoute is initialized.");
        this.gson = gson;
        this.gameCenter = gameCenter;
    }

    /**
     * Renders the Game Submitting the players turn route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type that represents the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSubmitTurnRoute is invoked.");
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = gameCenter.getGameById(current.getGameID());
        Message submitMessage = game.submitTurn();
        return gson.toJson(submitMessage);
    }
}
