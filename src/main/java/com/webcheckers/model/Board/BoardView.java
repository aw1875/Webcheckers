// Class Name: BoardView
// Description: Class to implement rows in game.ftl
package com.webcheckers.model.Board;

import com.webcheckers.model.Board.enums.Color;
import com.webcheckers.model.Board.enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This iterable class is to implement the rows and render the board
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
public class BoardView implements Iterable{
    private ArrayList<Row> board;
    private final int MAX_ROWS = 7;

    /**
     * This constructor creates an arraylist of Row objects
     * and calls the initBoard method
     */
    public BoardView() {
        board = new ArrayList<Row>();
        this.initBoard();
    }

    /**
     * getter function to return the board
     * @return board
     */
    public ArrayList<Row> getBoard() {
        return board;
    }

    /**
     * This constructor displays the appropriate board config
     * for both Red and White players
     * @param board the board that either player sees
     */
    public BoardView(ArrayList<Row> board) {
        this.board = board;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * Iterate through every Row and set the properties of each
     * index row
     * @return an Iterator Object.
     */
    @Override
    public Iterator iterator() {
        return new Iterator<Row>() {
            int indexPosition = 0;
            @Override
            public boolean hasNext() {
                if (indexPosition >= board.size())
                    return false;
                return true;
            }

            @Override
            public Row next() {
                Row val = board.get(indexPosition);
                indexPosition++;
                return val;
            }
        };
    }

    /**
     * this method initializes the board by iterating through Spaces and sets the
     * required properties, like color and Pieces
     */
    private void initBoard() {
        for (int i = 0; i < MAX_ROWS+1; i++) {
            board.add(new Row(i));
            for (int j = 0; j < MAX_ROWS+1; j++) {
                if ((i+j)%2 == 0) {
                    board.get(i).addSpace(new Space(j, false));
                }
                else
                    board.get(i).addSpace(new Space(j, true));
            }
        }

        for (int i = 0; i < MAX_ROWS+1; i += 2) {
            board.get(7).getSpace(i).createPiece(Type.SINGLE, Color.RED);
        }
        for (int i = 1; i < MAX_ROWS+1; i += 2) {
            board.get(6).getSpace(i).createPiece(Type.SINGLE, Color.RED);
        }
        for (int i = 0; i < MAX_ROWS+1; i += 2) {
            board.get(5).getSpace(i).createPiece(Type.SINGLE, Color.RED);
        }

        for (int i = 1; i < MAX_ROWS+1; i += 2) {
            board.get(0).getSpace(i).createPiece(Type.SINGLE, Color.WHITE);
        }
        for (int i = 0; i < MAX_ROWS+1; i += 2) {
            board.get(1).getSpace(i).createPiece(Type.SINGLE, Color.WHITE);
        }
        for (int i = 1; i < MAX_ROWS+1; i += 2) {
            board.get(2).getSpace(i).createPiece(Type.SINGLE, Color.WHITE);
        }
    }

    /**
     * this getter method returns rows on the board
     * @return an array of rows
     */
    public ArrayList<Row> getBoardArray() {
        return board;
    }

    /**
     * render the appropriate board view for the red player
     * @return BoardView object for red player
     */
    public BoardView getRedPlayerView () {
        return new BoardView(this.board);
    }

    /**
     * render the appropriate board view for the white player
     * @return BoardView object for white player
     */
    public BoardView getWhitePlayerView() {
        ArrayList<Row> tempBoard = new ArrayList<Row>();
        for (int i = 0; i < MAX_ROWS+1; i++) {
            tempBoard.add(i, new Row(i));
            for (int j = 0; j < MAX_ROWS+1; j++) {
                Space s = this.board.get(MAX_ROWS-i).createWhiteSpace(MAX_ROWS-j);
                tempBoard.get(i).addSpace(s);
            }
        }
        return new BoardView(tempBoard);
    }

    /**
     * implements a single Move move
     * @param singleMove the move triggered/received
     */
    public void movePiece(Move singleMove) {
        Position start = singleMove.getStart();
        Position end = singleMove.getEnd();
        Space initialSpace = board.get(start.getRow()).getSpace(start.getCell());
        Space finalSpace = board.get(end.getRow()).getSpace(end.getCell());

        if(!finalSpace.isValid()) {
            System.out.println("error : final invalid");
        }
        Piece thisPiece;
        if (initialSpace.getPiece() == null) {
            System.out.println(initialSpace + "error: initial invalid!");
            return;
        }
        else {
            thisPiece = initialSpace.getPiece();
        }
        finalSpace.setPiece(thisPiece);
        initialSpace.removePiece();

        //king
        if (end.getRow() == 0 && thisPiece.getType() != Type.KING && thisPiece.getColor() == Color.RED) {
            thisPiece.setKing();
        }
        else if (end.getRow() == MAX_ROWS && thisPiece.getType() != Type.KING && thisPiece.getColor() == Color.WHITE) {
            thisPiece.setKing();
        }
    }

    /**
     * Remove a jumped piece thats taken
     * @param position the position that has the piece
     * @return the new board after piece is removed
     */
    public Piece removeTakenPiece(Position position) {
        return board.get(position.getRow()).getSpace(position.getCell()).removePiece();
    }

    /**
     * place a piece on Board
     * @param position the position to place the piece at
     * @param piece the piece that is placed on the board
     */
    public void placeNewPiece (Position position, Piece piece) {
        board.get(position.getRow()).getSpace(position.getCell()).setPiece(piece);
    }

    /**
     * getter function to get a Space on board
     * @param position the position that holds the Space object
     * @return the Board object
     */
    public Space getSpace(Position position) {
        return board.get(position.getRow()).getSpace(position.getCell());
    }

    /**
     * Clear the pieces currently on board (for debugging)
     */
    public void clearBoard () {
        for (int i = 0; i <= 3; i++) {
            Row row = board.get(i);
            for (int j = 0; j <= MAX_ROWS; j++) {
                if (row.getSpace(j).isColored()) {
                    row.getSpace(j).removePiece();
                }
            }
        }
        for (int i = MAX_ROWS; i >= 5; i--) {
            Row row = board.get(i);
            for (int j = 0; j <= MAX_ROWS; j++) {
                if(row.getSpace(j).isColored()) {
                    row.getSpace(j).removePiece();
                }
            }
        }
    }

    /**
     * Function for testing board rows and spaces
     */
    public void printBoard() {
        for (int i = 0; i < MAX_ROWS + 1; i++) {
            for (int j = 0; j < MAX_ROWS + 1; j++) {
                System.out.println(board.get(i).getSpace(j).toString());
            }
            System.out.println("");
        }
    }
}
