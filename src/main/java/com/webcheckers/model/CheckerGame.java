// Class Name: CheckerGame
// Description: This is the crucial code for the game itself. It contains the game board.
package com.webcheckers.model;


import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.Board.MoveValidator;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;
import com.webcheckers.model.enums.GameWinner;
import com.webcheckers.util.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * This class has the game board and manages it. It takes two players and initializes
 * a Checkers game board to play on.
 * @author couchcoders
 * @version 1.3
 * @since 1.0
 */
public class CheckerGame {
    protected BoardView board;
    public Player redPlayer;
    public Player whitePlayer;
    public Player currentUser;
    public int numWhite;
    public int numRed;
    public Color activeColor;
    public GameWinner winner;
    public ArrayList<ArrayList<Move>> allMoves;
    public ArrayList<ArrayList<Piece>> piecesTaken;
    public ArrayList<Piece> pieceMoved;
    public MoveValidator moveValidator;
    private boolean multiMove;
    private ArrayList<Position> cantReturn = new ArrayList<>();
    private boolean isKing;
    private int movectr;
    public boolean piecesBlocked = false;
    protected Stack<Move> moves = new Stack<>();

    /**
     * The constructor takes two players, a red and a white and generates
     * a new Game board to play on. This also sets the essential properties
     * about the game.
     * @param redPlayer the first player
     * @param whitePlayer the second player
     */
    public CheckerGame(Player redPlayer, Player whitePlayer) {
        board = new BoardView();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currentUser = redPlayer;
        this.multiMove = false;
        activeColor = Color.RED;
        this.winner = GameWinner.ingame;
        numWhite = 12;
        numRed = 12;
        moveValidator = new MoveValidator(board, true);
        this.allMoves = new ArrayList<ArrayList<Move>>();
        this.piecesTaken = new ArrayList<ArrayList<Piece>>();
        this.pieceMoved = new ArrayList<Piece>();
        this.movectr = 0;
    }

    //constructor for replay mode
    public CheckerGame (Player redPlayer, Player whitePlayer, ArrayList<ArrayList<Move>> allMoves, ArrayList<ArrayList<Piece>> piecesTaken, ArrayList<Piece> pieceMoved) {
        board = new BoardView();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currentUser = redPlayer;
        this.multiMove = false;
        activeColor = Color.RED;
        this.winner = GameWinner.ingame;
        numWhite = 12;
        numRed = 12;
        this.allMoves = allMoves;
        moveValidator = new MoveValidator(board, true);
        this.piecesTaken = piecesTaken;
        this.pieceMoved = pieceMoved;
        this.movectr = 0;
    }

    /**
     * this getter function returns the BoardView object for the current game session
     * @return the rendered BoardView object
     */
    public BoardView getBoard() {
        return board;
    }

    /**
     * this getter function returns the board view associated with the red player
     * @return the corresponding board view
     */
    public BoardView getRedBoard () {
        return board.getRedPlayerView();
    }

    /**
     * this getter function returns the board view associated with the white player
     * @return the corresponding board view
     */
    public BoardView getWhiteBoard () {
        return board.getWhitePlayerView();
    }

    /**
     * this getter function returns the list of all moves
     * @return ArrayList<Move> the list of Move objects
     */
    public ArrayList<ArrayList<Move>> getAllMoves() {
        return allMoves;
    }

    /**
     * this getter function returns a list of pieces moved
     * @return ArrayList<Piece> the list of moved Piece objects
     */
    public ArrayList<Piece> getPieceMoved() {
        return pieceMoved;
    }

    /**
     * this getter function returns a list of pieces that is taken
     * @return ArrayList<Piece> the list of taken Piece objects
     */
    public ArrayList<ArrayList<Piece>> getPiecesTaken() {
        return piecesTaken;
    }

    public int getMovectr() {
        return movectr;
    }

    /**
     * This method sets its the turn of the current user/player
     * @param player the current player
     * @return bool true if its my turn, false otherwise
     */
    public boolean isMyTurn(Player player) {
        return (currentUser == player);
    }

    /**
     * this getter method returns the color of the player with current turn
     * @return activeColor the color object of current turn
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * this getter function returns the red player
     * @return redPlayer the Red Player Object
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * this getter function returns the white player
     * @return whitePlayer the White Player object
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * this getter function gets the opponent of the current player
     * @param player1 the current Player object
     * @return the opponent Player object
     */
    public Player getOpponent(Player player1) {
        if (player1 == this.redPlayer) {
            return this.whitePlayer;
        }
        if (player1 == this.whitePlayer) {
            return this.redPlayer;
        }
        else {
                return null;
        }
    }

    /**
     * this boolean method tells if the white player has pieces left
     * @return bool, true if white player has pieces left, false otherwise
     */
    public boolean whiteHasPieces() {
        return numWhite > 0;
    }

    /**
     * this boolean method tells if the red player has pieces left
     * @return bool, true if red player has pieces left, false otherwise
     */
    public boolean redHasPieces() {
        return numRed > 0;
    }

    //sets the number of white pieces left
    public void setNumWhite(int number) {numWhite = number;}

    //sets the number of red pieces left
    public void setNumRed(int number) {numRed = number;}

    /**
     * this getter method returns the winner of the current game session
     * @return GameWinner, the property of Player that won
     */
    public GameWinner getWinner() {
        return winner;
    }

    /**
     * Sets the winner of the game
     * @param winner GameWinner type representing the winner of the game
     */
    public void setWinner(GameWinner winner) {
        this.winner = winner;
    }

    /**
     * Checks to see if red doesn't have any pieces, if so, then the winner
     * is the white player. Else, checks to see if white doesn't have any pieces,
     * if so, then red in the winner
     */
    public void playerHasNoPiecesLose() {
        if (!redHasPieces()) {
            winner = GameWinner.white;
        }
        else if (!whiteHasPieces()) {
            winner = GameWinner.red;
        }
    }

    /**
     * If the red player resigns then the white player is identified as the winner.
     * Else, if the white player resigns then the red player is the winner.
     * Else the players switch turns.
     * @param p1 Player type representing the player
     */
    public void playerResigned(Player p1) {
        if (p1 == this.redPlayer) {
            winner = GameWinner.white;
        }
        else if (p1 == this.whitePlayer) {
            winner = GameWinner.red;
        }
        if (currentUser == p1) {
            switchCurrentTurn();
        }
    }

    /**
     * If the current player is the white player then the move on the
     * board is flipped to set it on the white players board accordingly.
     * Otherwise pushes the move to the stack
     * @param newMove Move type representing a player's move
     */
    public void pushMove(Move newMove) {
        if (currentUser == whitePlayer) {
            newMove.flipMove();
        }
        moves.push(newMove);
    }

    /**
     * Gets all the moves from the stack
     * @return Stack of type Move representing all the moves
     */
    public Stack<Move> getMoveStack() {
        return moves;
    }

    /**
     * If the moves stack is not empty then returns true.
     * Otherwise, returns false
     * @return boolean type that represents if the player has any moves
     */
    public boolean playerHasMoves() {
        boolean hasMoves;
        if (moves.isEmpty()) {
            hasMoves = moveValidator.generateAllPossibleMoves();
            if (!hasMoves) {
                piecesBlocked = true;
            }
        }
        else {
            Move move = moves.peek();
            if (move.isJump()) {
                cantReturn.add(move.getEnd());
                System.out.println(cantReturn);
                if(!multiMove) {
                    isKing = board.getBoard().get(move.getStart().getRow()).getSpace(move.getStart().getCell()).getPiece().getType() == Type.KING;
                    multiMove = true;
                }
                hasMoves = moveValidator.getPossibleJumpMovesOnPosition(move.getEnd(), isKing, cantReturn);
            }
            else {
                hasMoves = false;
            }
        }
        moveValidator.printSetStatus();
        return hasMoves;
    }

    /**
     * Gets all the possible moves the player has
     * @return HashSet of type Move representing all the moves
     */
    public HashSet<Move> getPossibleMoves() {
        return moveValidator.getPossibleMoves();
    }

    /**
     * If the player is able to jump then returns true. Otherwise, returns false
     * @return boolean type representing if the player can jump or not
     */
    public boolean jumpMoveAvailable() {
        return moveValidator.jumpMoveAvailable();
    }

    /**
     * If it was the red players turn it switches to the white players turn,
     * and vice versa for the white player
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
    }

    /**
     * Returns a Message for the player to be able to submit their turn.
     * If the player has a jump returns an appropriate message. Also, if the the
     * player wins based on their submission, Message is returned.
     * @return Message type for the player's submitted turn
     */
    public Message submitTurn() {
        if (playerHasMoves()) {
            return  new Message("Can't submit turn white jump still available", Message.Type.ERROR);
        }
        Position finalPosition;
        Position initialPosition;
        try {
            finalPosition = moves.peek().getEnd();
            initialPosition = moves.peek().getStart();
        }
        catch (Exception ex) {
            if (currentUser == redPlayer) {
                setWinner(GameWinner.white);
            }
            else {
                setWinner(GameWinner.red);
            }
            return new Message("You Win!", Message.Type.INFO);
        }
        ArrayList<Move> turn = new ArrayList<Move>();
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        while(!moves.empty()) {
            Move currMove = moves.pop();
            turn.add(0, currMove);
            if (currMove.isJump()) {
                Position jumpedPosition = currMove.getOpponentPiecePosition();
                System.out.println("Opponent Piece " + jumpedPosition);
                pieces.add(0, board.removeTakenPiece(jumpedPosition));
                if (activeColor == Color.RED) {
                    numWhite--;
                }
                else {
                    numRed--;
                }
                playerHasNoPiecesLose();
            }
            initialPosition = currMove.getStart();
        }
        Move total = new Move (initialPosition, finalPosition);
        Piece currPiece = board.getSpace(initialPosition).getPiece();
        Piece newPiece = new Piece(currPiece.getType(), currPiece.getColor());
        board.movePiece(total);
        allMoves.add(turn);
        piecesTaken.add(pieces);
        pieceMoved.add(newPiece);
        multiMove = false;
        cantReturn = new ArrayList<>();
        switchCurrentTurn();
        return new Message("All moves submitted", Message.Type.INFO);
    }

    /**
     * Message identifying there is a next turn for the replay mode
     * @return Message stating there is another turn
     */
    public Message nextTurn() {
        Position finalPosition;
        Position initialPosition;
        ArrayList<Move> turn = allMoves.get(movectr);
        initialPosition = turn.get(0).getStart();
        finalPosition = turn.get(0).getEnd();
        for (int i = 0; i < turn.size(); i++) {
            Move currMove = turn.get(i);
            if (currMove.isJump()) {
                Position jumpedPosition = currMove.getOpponentPiecePosition();
                board.removeTakenPiece(jumpedPosition);
                if (activeColor == Color.RED) {
                    numWhite--;
                }
                else {
                    numRed--;
                }
            }
            finalPosition = currMove.getEnd();
        }
        Move total = new Move(initialPosition, finalPosition);
        System.out.println(total);
        board.movePiece(total);
        switchCurrentTurn();
        movectr++;
        return new Message("true", Message.Type.INFO);
    }

    /**
     * Message returned identifying that there is a previous turn in the
     * replay mode
     * @return Message stating there is a previous move
     */
    public Message previousTurn() {
        Position finalPosition;
        Position initialPosition;
        ArrayList<Move> turn = allMoves.get(movectr - 1);
        ArrayList<Piece> pieces = piecesTaken.get(movectr - 1);
        Piece previousPiece = pieceMoved.get(movectr - 1);
        int last = turn.size() - 1;
        initialPosition = turn.get(last).getEnd();
        finalPosition = turn.get(last).getStart();

        for (int i = last; i >= 0; i--) {
            Move currMove = turn.get(i);
            if (currMove.isJump()) {
                Position jumpedPosition = currMove.getOpponentPiecePosition();
                board.placeNewPiece(jumpedPosition, pieces.get(i));
            }
            finalPosition = currMove.getStart();
        }
        Move total = new Move(initialPosition, finalPosition);
        if (board.getSpace(initialPosition).getPiece().getType() == Type.KING) {
            if (pieceMoved.get(movectr - 1).getType() == Type.SINGLE) {
                board.getSpace(initialPosition).getPiece().setSingle();
            }
        }
        board.movePiece(total);
        switchCurrentTurn();
        movectr--;
        return new Message("true", Message.Type.INFO);
    }
}
