package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class GetReplayStopRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetReplayStopRoute.class.getName());
    private Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the GameReplayStopRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public GetReplayStopRoute(Gson gson, GameCenter gameCenter) {
        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * Renders Game Replay route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type representing the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        current.setStateWaiting();
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
