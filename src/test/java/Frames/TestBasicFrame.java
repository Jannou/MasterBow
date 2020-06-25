package Frames;

import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.core.frames.BasicFrame;
import com.nanabaskint.core.frames.FrameType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBasicFrame {

    private BasicFrame frame;
    private Conductor conductor;
    private Game game;

    @BeforeEach
    public void init() {
        game = new Game(4, null);
        conductor = game.getConductor();
        frame = new BasicFrame(game, conductor, null);
    }

    @AfterEach
    public void clean() {
        game = null;
        conductor = null;
        frame = null;
    }

    @Test
    void testSubscribement() {
        assertTrue(-1 < conductor.getFramesToInform().indexOf(frame) && conductor.getFramesToInform().indexOf(frame) < 4);
    }


    @Test
    void numberOfFallenPinsDuringCurrentRollTest1() {
        // test 1 roll for value = 5
        int firstRoll = 5;
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(firstRoll, frame.getScore());
        assertEquals(firstRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.REGULAR, frame.getType());
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertEquals(1, frame.getCurrentRoll());
        assertFalse(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest2() {
        // test 2 rolls, values 5 and 7
        int firstRoll = 5;
        int secondRoll = 7;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(1, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertFalse(frame.isCompleted());
        assertEquals(2, frame.getCurrentRoll());
        assertEquals(firstRoll + secondRoll, frame.getScore());
        assertEquals(firstRoll + secondRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.REGULAR, frame.getType());
        assertEquals(1, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest3() {
        // test 3 rolls, values 5 and 7 and 2
        //REGULAR FRAME
        int firstRoll = 5;
        int secondRoll = 7;
        int thirdRoll = 2;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertFalse(frame.isCompleted());
        assertEquals(1, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertFalse(frame.isCompleted());
        assertEquals(2, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(3, frame.getCurrentRoll());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getScore());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.REGULAR, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest4() {
        // SPARE on secund roll
        int firstRoll = 5;
        int secondRoll = 10;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(1, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertEquals(firstRoll + secondRoll, frame.getScore());
        assertEquals(firstRoll + secondRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.SPARE, frame.getType());
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest5() {
        //spare on third roll
        int firstRoll = 5;
        int secondRoll = 7;
        int thirdRoll = 3;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(1, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertEquals(2, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(3, frame.getCurrentRoll());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getScore());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.SPARE, frame.getType());
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest6() {
        //strike
        int firstRoll = 15;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(1, frame.getCurrentRoll());
        assertEquals(firstRoll, frame.getScore());
        assertEquals(firstRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.STRIKE, frame.getType());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest7() {
        // test 3 rolls, values 5 and 7 and 2
        //REGULAR FRAME
        int firstRoll = 5;
        int secondRoll = 7;
        int thirdRoll = 2;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertFalse(frame.isCompleted());
        assertEquals(1, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertFalse(frame.isCompleted());
        assertEquals(2, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(3, frame.getCurrentRoll());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getScore());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.REGULAR, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(3, frame.getCurrentRoll());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getScore());
        assertEquals(firstRoll + secondRoll + thirdRoll, frame.getPinsKnockedDown());
        assertEquals(FrameType.REGULAR, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest8() {
        // 4 strike
        int strike = 15;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        assertEquals(1, frame.getCurrentRoll());
        assertEquals(strike, frame.getScore());
        assertEquals(strike, frame.getPinsKnockedDown());
        assertEquals(FrameType.STRIKE, frame.getType());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        assertEquals(4 * strike, frame.getScore());
        assertEquals(4 * strike, frame.getPinsKnockedDown());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest9() {
        // 4 strike + an extra roll than don't have to be counted
        int strike = 15;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        assertEquals(1, frame.getCurrentRoll());
        assertEquals(strike, frame.getScore());
        assertEquals(strike, frame.getPinsKnockedDown());
        assertEquals(FrameType.STRIKE, frame.getType());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        assertEquals(4 * strike, frame.getScore());
        assertEquals(4 * strike, frame.getPinsKnockedDown());
        frame.numberOfFallenPinsDuringCurrentRoll(strike);
        assertEquals(4 * strike, frame.getScore());
        assertEquals(4 * strike, frame.getPinsKnockedDown());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest10() {
        // 4 strike + an extra roll than don't have to be counted
        int firstRoll = 10;

        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(firstRoll, frame.getScore());
        assertEquals(2, frame.getNumberOfRollsToListen());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertEquals(firstRoll, frame.getScore());
        assertEquals(firstRoll, frame.getPinsKnockedDown());
    }
}