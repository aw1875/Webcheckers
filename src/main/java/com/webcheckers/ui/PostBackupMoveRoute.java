package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the PostBackupMoveRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public PostBackupMoveRoute(Gson gson, GameCenter gameCenter) {
        this.gson = gson;
        this.gameCenter = gameCenter;
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    /**
     * Renders the Game Move Backup route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type that represents the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostBackupMoveRoute is invoked.");
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = gameCenter.getGameById(current.getGameID());
        Message message;

        if(game.getMoveStack().isEmpty()) {
            message = new Message("No moves has been made", Message.Type.ERROR);
        }
        else {
            Move last = game.getMoveStack().pop();
            message = new Message("Move undone", Message.Type.INFO);
        }
        return gson.toJson(message);
    }
}
