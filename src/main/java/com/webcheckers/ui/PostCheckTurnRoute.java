package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.GameWinner;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    private Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the PostCheckTurnRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public PostCheckTurnRoute(Gson gson, GameCenter gameCenter) {
        this.gson = gson;
        this.gameCenter = gameCenter;
        LOG.config("PostCheckTurn is initialized.");
    }

    /**
     * Renders the Game turn check route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type that represents the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostCheckTurn is invoked.");
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = gameCenter.getGameById(current.getGameID());
        boolean isMyTurn = game.isMyTurn(current);
        GameWinner winner = game.getWinner();

        if (winner == GameWinner.ingame) {
            Message turnMessage = new Message(Boolean.toString(isMyTurn), Message.Type.INFO);
            return gson.toJson(turnMessage);
        }
        else {
            Message turnMessage = new Message("true", Message.Type.INFO);
            return gson.toJson(turnMessage);
        }
    }
}
