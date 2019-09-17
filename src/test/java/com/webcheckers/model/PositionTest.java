// PositionTest.java
// Used to test functions used in Position object
// Author: couchcoders
package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
public class PositionTest {

    private int row = 1;
    private int col = 1;

    private Position pos11 = new Position(row, col);
    private Position pos00 = new Position(0,0);
    private Position pos10 = new Position(1, 0);
    private Position pos01 = new Position(0, 1);

    @Test
    void testLocation()
    {
        try
        {
            System.out.println("Getting row value of position 'pos'...");
            assertEquals(pos11.getRow(), row);
            fail("[!] 'pos' row does not match expected value");

            // Why is it called cell?  Just call it the col smh.
            System.out.println("Getting cell value of position 'pos'...");
            assertEquals(pos11.getCell(), col);
            fail("[!] 'pos' cell/col does not match expected value");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("getRow()/getCell() test finished");
    }

    @Test
    void testMatch()
    {
        try
        {
            System.out.println("Testing two positions that do not match in either row or col...");
            assertNotEquals(pos11, pos00);
            fail("[!] The positions should not register as equal");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }

        try
        {
            System.out.println("Testing two positions with matching rows but not cols...");
            assertNotEquals(pos00, pos01);
            fail("[!] The positions should not register as equal");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        try
        {
            System.out.println("Testing two positions with matching cols but not rows...");
            assertNotEquals(pos00, pos10);
            fail("[!] The positions should not register as equal");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("Location testing finished");
    }

    @Test
    void testtoString()
    {
        try
        {
            System.out.println("Testing string output...");
            assertEquals(pos10.toString(), "[1,0]");
            fail("[!] String does not match expected output");
        }
        catch (Throwable e) { assertEquals(AssertionFailedError.class, e.getClass()); }
        finally { System.out.println("[+] Test successful"); }

        System.out.println("toString() test finished");
    }
}
