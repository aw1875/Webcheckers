// Class Name: Row
// Description: Class to define rows on a game board.
package com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This iterable class is to implement an object Row that is used by game.ftl
 * @author couchcoders
 * @version 1.1
 * @since 1.0
 */
public class Row implements Iterable<Space> {
    private int index;
    private ArrayList<Space> spaces;

    /**
     * Iterate through every Space in every Row and set appropriate properties
     * of the Row as in game.ftl
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Space> iterator() {
        return new Iterator<Space>() {
            int indexPosition = 0;
            @Override
            public boolean hasNext() {
                if(indexPosition >= spaces.size())
                    return false;
                return true;
            }

            @Override
            public Space next() {
                Space val = spaces.get(indexPosition);
                indexPosition++;
                return val;
            }
        };
    }

    /**
     * Constructor that creates a new arraylist of Space objects and sets
     * index of this row
     * @param index a particular Row
     */
    public Row (int index) {
        this.index = index;
        spaces = new ArrayList<Space>();
    }

    /**
     * Add a new Space to the array of Spaces
     * @param newSpace the new Space Object to be added
     */
    public void addSpace (Space newSpace) {
        spaces.add(newSpace);
    }

    /**
     * getter function to get the index of a Row
     * @return the index of this row
     */
    public int getIndex() {
        return index;
    }

    /**
     * getter function to get a Space in a row with the given
     * index
     * @param index the index of the Space in the Row
     * @return the Space at the given index
     */
    public Space getSpace(int index) {
        return spaces.get(index);
    }

    /**
     * Create a white space at the given index
     * @param index the index its creating a Space at
     * @return the Space added at the index
     */
    public Space createWhiteSpace(int index) {
        Space newSpace = new Space(spaces.get(index));
        return newSpace;
    }

    /**
     * Pretty print details about the Row
     * @return the pretty printed String
     */
    @Override
    public String toString() {
        return ("Row: " + index);
    }
}
