// Class Name: GameCenter
// Description: This class manages the game and keeps the required records of the game under one class.
package com.webcheckers.appl;

import com.webcheckers.model.BotGame;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the game and keeps the required records of the game under one class
 * Principle: Information Expert
 * @author couchcoders
 * @version 1.2
 * @since 1.0
 */

public class GameCenter {
    private Map<String, CheckerGame> games;
    /**
     * Constructor of GameCenter
     */
    public GameCenter() {
        games = new HashMap<>();
    }

    /**
     * This method creates a new game passed two players as args
     * @param player1 the first player
     * @param player2 the second player
     */
    public void createNewGame(Player player1,Player player2) {
        CheckerGame newGame = new CheckerGame(player1, player2);
        String id = player1.getName();
        games.put(id, newGame);
        player1.setGameID(id);
        player2.setGameID(id);
    }

    /**
     * This method creates a new game with a bot player
     */
    public void createNewBotGame(Player player1, Player player2, Player currPlayer) {
        BotGame newGame = new BotGame(player1, player2);
        String id = currPlayer.getName();
        games.put(id, newGame);
        currPlayer.setGameID(id);
    }

    /**
     * Returns the game associated with the gameID
     * @param gameID the ID to pull the game from
     * @return CheckerGame the game associated with the gameID
     */
    public synchronized CheckerGame getGameById(String gameID) {
        return games.get(gameID);
    }

    /**
     * gets a map of all games
     * @return the map of all games
     */
    public Map<String, CheckerGame> getGames() {
        return this.games;
    }

    /**
     * This method removes a game based on its game id
     * @param gameID the gameID associated with the game being removed
     */
    public void removeGame(String gameID) {
        games.remove(gameID);
    }
}
