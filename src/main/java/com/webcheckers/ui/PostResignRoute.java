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

public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());
    private Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the PostResignRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public PostResignRoute(Gson gson, GameCenter gameCenter) {
        this.gameCenter = gameCenter;
        this.gson = gson;
        LOG.config("PostResignRoute is initialized.");
    }

    /**
     * Renders the Game Resign route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type that represents the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostResignRoute is invoked.");
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = gameCenter.getGameById(current.getGameID());
        Player opponent = game.getOpponent(current);

        current.setStateWaiting();
        game.playerResigned(current);

        Message message = new Message("You have resigned.", Message.Type.INFO);
        return gson.toJson(message);
    }
}
