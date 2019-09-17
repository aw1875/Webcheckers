// Class name: PlayerLobby
// Description: This class creates a lobby for players of the game
package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Creates a lobby for the players.
 * @author couchcoders
 * @version 1.2
 * @since 1.0
 */
public class PlayerLobby {
    public final String WHITEBOT = "Tom";
    public final String REDBOT = "Jerry";
    public static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());
    private ArrayList<Player> players;

    /**
     * constructor for PlayerLobby, which is a list of players
     */
    public PlayerLobby() {
        players = new ArrayList<>();
    }

    /**
     * This method checks if the userName already exists.
     * @param username the username to check for existence
     * @return bool true if username exists, false otherwise
     */
    public boolean userNameExists(String username) {
        for (Player player: players) {
            if (player.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the username contains special characters.
     * @param name the username that its checking
     * @return boolean true if the string buffer has special characters, false otherwise
     */
    public boolean hasSpecialChar(String name) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        return p.matcher(name).find();
    }

    /**
     * This method adds logged in players to lobby.
     * @param player the player that gets added to the lobby
     */
    public void addPlayer(Player player) {
        players.add(player);
        player.setStateWaiting();
    }

    /**
     * This getter method returns the players in the lobby.
     * @return the list of players in the lobby
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This getter method returns a player based on the username arg passed
     * @param name the username of the Player to get
     * @return the Player associated with the name
     */
    public Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * This method removes a player from the lobby
     * @param name the username of the player that gets removed
     */
    public void removePlayer(String name) {
        players.remove(getPlayer(name));
    }
}
