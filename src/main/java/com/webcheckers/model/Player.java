// Class Name: Player
// Description: This class represents te Player model
package com.webcheckers.model;

import com.webcheckers.model.enums.PlayerState;

import java.util.ArrayList;

/**
 * This class implements the Player object that contains critical information
 * of a player.
 * @author couchcoders
 * @version 1.4
 * @since 1.0
 */
public class Player {
    private String userName;
    private String gameID;
    private ArrayList<Move> moves;
    private PlayerState state;
    private boolean isBot;

    /**
     * Constructor for Player that sets the username and creates an ArrayList of moves
     * @param name the name of the player that its representing
     */
    public Player (String name) {
        userName = name;
        moves = new ArrayList<Move>();
        isBot = false;
    }

    public Player (String name, boolean bot) {
        userName = name;
        moves = new ArrayList<Move>();
        isBot = bot;
    }

    public boolean isBot() {
        return isBot;
    }

    /**
     * getter method to return the username of the associated player
     * @return the username associated with this player
     */
    public String getName() {
        return userName;
    }

    /**
     * This method sets a gameID for each player
     * @param gameID the gameID set for the player (user id)
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /**
     * This function sets the player status to waiting
     */
    public void setStateWaiting() {state = PlayerState.WAITING;}

    /**
     * This function sets the player status to in game
     */
    public void setStateInGame() {state = PlayerState.INGAME;}

    /**
     * This function sets the player status to challenged
     */
    public void setStateChallenged() {state = PlayerState.CHALLENGED;}

    public void setStateReplay() {
        state = PlayerState.REPLAY;
    }

    /**
     * This method returns if the associated player is challenged
     * @return bool, true if the player has challenged status, false otherwise
     */
    public boolean isChallenged() {
        return state == PlayerState.CHALLENGED;
    }

    /**
     * This method returns if the associated player is waiting
     * @return bool, true if the player has waiting status, false otherwise
     */
    public boolean isWaiting() {
        return state == PlayerState.WAITING;
    }

    /**
     * This method returns if the associated player is in game
     * @return bool, true if the player has ingame status, false otherwise
     */
    public boolean isInGame() {
        return state == PlayerState.INGAME;
    }

    /**
     * Pretty print status of the Player
     * @return status of the player as a formatted string
     */
    @Override
    public String toString() {

        if (!isChallenged()) {
            if (isInGame()) {
                return (PlayerState.INGAME.name() + "  ");
            }
            else {
            return (PlayerState.WAITING.name() + "  ");
            }
        }

        else if (isChallenged()) {
            return (PlayerState.CHALLENGED.name() + "  ");
        }

        else if (isInGame()) {
            return (PlayerState.INGAME.name() + "  ");
        }

        else if (isReplay()) {
            return (PlayerState.REPLAY.name() + "  ");
        }
        return null;
    }

    /**
     * this getter method returns the gameID associated with the Player
     * @return the associated gameID
     */
    public String getGameID() {
        return gameID;
    }

    public boolean isReplay() {
        return state == PlayerState.REPLAY;
    }
}
