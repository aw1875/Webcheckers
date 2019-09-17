package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.enums.GameWinner;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.net.URLDecoder;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;
    private GameCenter gameCenter;

    /**
     * The UI Constructor for the PostValidateMoveRoute that handles all the Get
     * HTTP requests
     * @param gson Gson type representing the gson
     * @param gameCenter Gamecenter type representing the gameCenter
     */
    public PostValidateMoveRoute(final Gson gson, GameCenter gameCenter) {
        this.gson = gson;
        this.gameCenter = gameCenter;
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    /**
     * Renders the Game Validate Move route for the CebCheckers game
     * @param request Request type representing the HTTP request
     * @param response Response type representing the HTTP response
     * @return Object type that represents the rendered Replay page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostValidateMoveRoute is invoked.");
        final Session httpSession = request.session();
        Player current = httpSession.attribute(WebServer.PLAYER_SESSION_KEY);
        CheckerGame game = gameCenter.getGameById(current.getGameID());
        Message moveMessage;

        if (game.getWinner() != GameWinner.ingame) {
            moveMessage = new Message("Cannot move piece when game is over", Message.Type.ERROR);
            return gson.toJson(moveMessage);
        }

        if(!game.playerHasMoves()) {
            String message;
            if (game.piecesBlocked) {
                message = "Your piece(s) are blocked and you must resign";
            }
            else {
                message = "You are out of moves";
            }
            moveMessage = new Message(message, Message.Type.ERROR);
            return gson.toJson(moveMessage);
        }
        final String moveMade = URLDecoder.decode(request.body(), "UTF-8").substring(11);
        final Move move = gson.fromJson(moveMade, Move.class);
        moveMessage = move.validateMove(game.getPossibleMoves(), game.jumpMoveAvailable());
        if (moveMessage.getType() != Message.Type.ERROR) {
            game.pushMove(move);
        }
        return gson.toJson(moveMessage);
    }
}
