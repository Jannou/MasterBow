import com.nanabaskint.core.Engine;
import com.nanabaskint.core.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestGame {

    private Game game;
    private final int NUMBER_OF_FRAMES_IN_THE_GAME = 5;
    @BeforeEach
    void setUp() {
        game = new Game(NUMBER_OF_FRAMES_IN_THE_GAME,null);
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Test
    void init() {
        game.init();
        assertEquals(game.getFrames().length, NUMBER_OF_FRAMES_IN_THE_GAME);
        assertEquals(1, game.getConductor().getFramesToInform().size());
        assertNotNull(game.getFrames()[0]);
        assertEquals(0, game.getScore());
        assertNull(game.getcBoard());
    }

    @Test
    void getNumberOfFrames() {
        assertEquals(NUMBER_OF_FRAMES_IN_THE_GAME,game.getNumberOfFrames());
    }

    @Test
    void updateScore() {
        game.updateScore(5);
        assertEquals(5,game.getScore());
        game.updateScore(15);
        assertEquals(20,game.getScore());
        game.updateScore(7);
        assertEquals(27,game.getScore());
        game.updateScore(-5);
        assertEquals(22,game.getScore());
        game.updateScore(0);
        assertEquals(22,game.getScore());
        game.updateScore(-30);
        assertEquals(-8,game.getScore());
    }

    @Test
    void lancer1() {
        init();
        game.lancer(5); //frame1
        assertEquals(5,game.getScore());
        game.lancer(10); //spare
        assertEquals(15,game.getScore());
        game.lancer(2);//first roll  frame2
        assertEquals(19,game.getScore());
        game.lancer(5); // second roll
        assertEquals(29,game.getScore());
        game.lancer(3);//end frame2
        assertEquals(32,game.getScore());
        game.lancer(15);//strike frame3
        assertEquals(47,game.getScore());
        game.lancer(15);//strike frame4 / 1 /
        assertEquals(77,game.getScore());
        game.lancer(4); //frame5(final)  / 2  / 1
        assertEquals(89,game.getScore());
        game.lancer(6); //  3 / 2
        assertEquals(107,game.getScore());
        game.lancer(5); //spare /3
        assertEquals(117,game.getScore());
        game.lancer(2);
        assertEquals(119,game.getScore());
        game.lancer(0);
        assertEquals(119,game.getScore());
        assertTrue(game.getConductor().getFramesToInform().isEmpty());

    }

    @Test
    void lancer2() {
        init();
        game.lancer(5); //frame1
        assertEquals(5,game.getScore());
        game.lancer(10); //spare
        assertEquals(15,game.getScore());
        game.lancer(2);//first roll  frame2
        assertEquals(19,game.getScore());
        game.lancer(5); // second roll
        assertEquals(29,game.getScore());
        game.lancer(3);//end frame2
        assertEquals(32,game.getScore());
        game.lancer(3);// frame3
        assertEquals(35,game.getScore());
        game.lancer(2);// frame3
        assertEquals(37,game.getScore());
        game.lancer(1);// frame3
        assertEquals(38,game.getScore());
        game.lancer(0);// frame4
        assertEquals(38,game.getScore());
        game.lancer(1);// frame4
        assertEquals(39,game.getScore());
        game.lancer(0);// frame4
        assertEquals(39,game.getScore());
        game.lancer(10);// frame5
        assertEquals(49,game.getScore());
        game.lancer(5);// frame5 spare 2extrarolll
        assertEquals(54,game.getScore());
        game.lancer(7);// frame5 spare 2extrarolll
        assertEquals(61,game.getScore());
        game.lancer(2);// frame5 spare 2extrarolll
        assertEquals(63,game.getScore());
        assertTrue(game.getConductor().getFramesToInform().isEmpty());
    }

    @Test
    void lancer3() {
        init();
        game.lancer(15); //strike1
        assertEquals(15,game.getScore());
        game.lancer(15); //strike2
        assertEquals(45,game.getScore());
        game.lancer(15); //strike3
        assertEquals(90,game.getScore());
        game.lancer(15); //strike4
        assertEquals(150,game.getScore());
        game.lancer(15); //strike1
        assertEquals(210,game.getScore());
        game.lancer(15); //strike1
        assertEquals(255,game.getScore());
        game.lancer(15); //strike1
        assertEquals(285,game.getScore());
        game.lancer(15); //strike1
        assertEquals(300,game.getScore());
        assertTrue(game.getConductor().getFramesToInform().isEmpty());
    }

}
