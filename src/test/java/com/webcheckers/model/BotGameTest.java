package com.webcheckers.model;

import com.webcheckers.model.Board.enums.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class BotGameTest {
    @Test
    public void switchCurrentTurn() throws Exception {
        Player bot = new Player("bot", true);
        Player bot2 = new Player("bot");
        BotGame bg = new BotGame(bot,bot2);
        Color curcolor = bg.getActiveColor();
        bg.switchCurrentTurn();
        Color nextcolor = bg.getActiveColor();
        assertEquals(curcolor,nextcolor);
    }

}