// Class Name: RowTest
// Description: JUnit test for Row Object
package com.webcheckers.model.Board;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
/**
 * This class tests the functionality of the Row object
 * @author couchcoders
 * @version 1.0
 * @since 1.0
 */
class RowTest {
    private Row row0 = new Row(0);
    private Row row1 = new Row(1);
    private Row row2 = new Row(2);
    private Row row3 = new Row(3);
    private Row row4 = new Row(4);
    private Row row5 = new Row(5);
    private Row row6 = new Row(6);
    private Row row7 = new Row(7);

    /**
     * This test method tests the GetIndex function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testGetIndex() {
        try {
            System.out.println("Fetching Row indices...");
            assertEquals(row0.getIndex(), 0);
            assertEquals(row1.getIndex(), 1);
            assertEquals(row2.getIndex(), 2);
            assertEquals(row3.getIndex(), 3);
            assertEquals(row4.getIndex(), 4);
            assertEquals(row5.getIndex(), 5);
            assertEquals(row6.getIndex(), 6);
            assertEquals(row7.getIndex(), 7);
            fail("Wrong row index received");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("GetIndex successfully tested");
        }
    }

    /**
     * This test method tests the Iterator function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testIterator() {
        try {
            System.out.println("Testing iterator...");
            assertFalse(row0.iterator().hasNext());
            assertFalse(row1.iterator().hasNext());
            assertFalse(row2.iterator().hasNext());
            assertFalse(row3.iterator().hasNext());
            assertFalse(row4.iterator().hasNext());
            assertFalse(row5.iterator().hasNext());
            assertFalse(row6.iterator().hasNext());
            assertFalse(row7.iterator().hasNext());
            System.out.println("Adding a new space at row 0...");
            row0.addSpace(new Space(0, true));
            assertTrue(row0.iterator().hasNext());
            System.out.println("Successfully added a new space at row 0...");
            assertEquals(row0.iterator().next().getCellIdx(), 0);
            fail("Iterator failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("Iterator successfully tested");
        }
    }

    /**
     * This test method tests the addSpace function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testaddSpace() {
        System.out.println("Adding a new space at row 0...");
        row0.addSpace(new Space(0, true));
        assertTrue(row0.iterator().hasNext());
        assertEquals(row0.iterator().next().getCellIdx(), 0);
        try {
            // Row 1
            System.out.println("Adding a new space at row 1...");
            row1.addSpace(new Space(1, true));
            assertTrue(row1.iterator().hasNext());
            System.out.println("Successfully added a new space at row 1...");
            assertEquals(row1.iterator().next().getCellIdx(), 1);
            // Row 2
            System.out.println("Adding a new space at row 2...");
            row2.addSpace(new Space(2, true));
            assertTrue(row2.iterator().hasNext());
            System.out.println("Successfully added a new space at row 2...");
            assertEquals(row2.iterator().next().getCellIdx(), 2);
            // Row 3
            System.out.println("Adding a new space at row 3...");
            row3.addSpace(new Space(3, true));
            assertTrue(row3.iterator().hasNext());
            System.out.println("Successfully added a new space at row 3...");
            assertEquals(row3.iterator().next().getCellIdx(), 3);
            // Row 4
            System.out.println("Adding a new space at row 4...");
            row4.addSpace(new Space(4, true));
            assertTrue(row4.iterator().hasNext());
            System.out.println("Successfully added a new space at row 4...");
            assertEquals(row4.iterator().next().getCellIdx(), 4);
            // Row 5
            System.out.println("Adding a new space at row 5...");
            row5.addSpace(new Space(5, true));
            assertTrue(row5.iterator().hasNext());
            System.out.println("Successfully added a new space at row 5...");
            assertEquals(row5.iterator().next().getCellIdx(), 5);
            // Row 6
            System.out.println("Adding a new space at row 6...");
            row6.addSpace(new Space(6, true));
            assertTrue(row6.iterator().hasNext());
            System.out.println("Successfully added a new space at row 6...");
            assertEquals(row6.iterator().next().getCellIdx(), 6);
            // Row 7
            System.out.println("Adding a new space at row 7...");
            row7.addSpace(new Space(7, true));
            assertTrue(row7.iterator().hasNext());
            System.out.println("Successfully added a new space at row 7...");
            assertEquals(row7.iterator().next().getCellIdx(), 7);
            fail("Adding spaces to rows failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("addSpace successfully tested");
        }
    }

    /**
     * This test method tests the getSpace function
     * @throws IndexOutOfBoundsException not thrown as indices were in range
     */
    @Test
    void testgetSpace() {
        try {
            System.out.println("Adding spaces to rows...");
            row0.addSpace(new Space(0, true));
            row1.addSpace(new Space(1, true));
            System.out.println("Fetching space 0 at row 0...");
            assertTrue(row0.getSpace(0) != null);
            System.out.println("Fetching space 1 at row 1...");
            assertTrue(row1.getSpace(1) != null);
            fail("Wrong space received");
        }
        catch (Throwable ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
        }
        finally {
            System.out.println("getSpace successfully tested");
        }
    }

    /**
     * This test method tests the toString function
     * @throws AssertionFailedError not thrown as no assertion failed
     */
    @Test
    void testtoString() {
        try {
            System.out.println(row0.toString());
            assertEquals(row0.toString(), "Row: 0");
            System.out.println(row1.toString());
            assertEquals(row1.toString(), "Row: 1");
            System.out.println(row2.toString());
            assertEquals(row2.toString(), "Row: 2");
            System.out.println(row3.toString());
            assertEquals(row3.toString(), "Row: 3");
            System.out.println(row4.toString());
            assertEquals(row4.toString(), "Row: 4");
            System.out.println(row5.toString());
            assertEquals(row5.toString(), "Row: 5");
            System.out.println(row6.toString());
            assertEquals(row6.toString(), "Row: 6");
            System.out.println(row7.toString());
            assertEquals(row7.toString(), "Row: 7");
            fail("toString override failed");
        }
        catch (Throwable ex) {
            assertEquals(AssertionFailedError.class, ex.getClass());
        }
        finally {
            System.out.println("toString successfully tested");
        }
    }
}
