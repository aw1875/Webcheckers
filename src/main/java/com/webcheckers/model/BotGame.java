package com.webcheckers.model;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.enums.GameWinner;
import com.webcheckers.util.Message;

import java.util.HashSet;

public class BotGame extends CheckerGame{

    /**
     * Constructor for creating a Game with a Bot. Takes in 2 players. Checks to see if the second
     * player is a bot.  If it is a bot, then will call the method for the bot to make the move. Otherwise
     * the bot waits.
     * @param p1 Player type representing the first player/current user
     * @param p2 Player type representing a bot
     */
    public BotGame(Player p1, Player p2) {
        super(p1, p2);
        if(p1.isBot()) {
            botMove();
        }
    }

    /**
     * Method to handle the move to be made by a bot
     */
    private void botMove() {
        boolean moved = false;
        while(playerHasMoves()) {
            moved = true;
            HashSet<Move> possibleMoves = getPossibleMoves();
            boolean MoveAreJumps = jumpMoveAvailable();
            Object[] possibleMovesArray = possibleMoves.toArray();
            Move move;
            do {
                int x = possibleMovesArray.length;
                x = (int) (Math.random() * x);
                move = (Move) (possibleMovesArray[x]);
            }
            while (move.validateMove(possibleMoves, MoveAreJumps).getType() != Message.Type.INFO);
            pushMove(move);
        }
        if (moved) {
            submitTurn();
        }
        else {
            if (currentUser == redPlayer) {
                setWinner(GameWinner.white);
            }
            else {
                setWinner(GameWinner.red);
            }
        }
    }

    /**
     * Switches the turns of the players.  If it was the red players turn, changes to the white player
     * and vice versa.
     */
    public void switchCurrentTurn() {
        if (currentUser == redPlayer) {
            currentUser = whitePlayer;
            activeColor = Color.WHITE;
        }
        else {
            currentUser = redPlayer;
            activeColor = Color.RED;
        }
        moveValidator.switchActivePlayer();
        if (currentUser.isBot()) {
            botMove();
        }
    }
}
