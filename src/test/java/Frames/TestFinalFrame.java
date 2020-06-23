package Frames;

import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.core.frames.FinalFrame;
import com.nanabaskint.core.frames.FrameType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestFinalFrame {

    private FinalFrame frame;
    private Conductor conductor;
    private Game game;

    @BeforeEach
    public void init() {
        game = new Game(4, null);
        conductor = game.getConductor();
        frame = new FinalFrame(game, conductor, null);
    }

    @AfterEach
    public void clean() {
        game = null;
        conductor = null;
        frame = null;
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
        assertEquals(0, frame.getExtraPinsKnockedDown());
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
        assertEquals(0, frame.getExtraPinsKnockedDown());
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
        assertEquals(0, frame.getExtraPinsKnockedDown());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest4() {
        //SPARE FRAME
        int firstRoll = 5;
        int secondRoll = 10;
        assertEquals(0, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertFalse(frame.isCompleted());
        assertEquals(1, frame.getCurrentRoll());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertEquals(2, frame.getCurrentRoll());
        assertEquals(firstRoll + secondRoll, frame.getScore());
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(FrameType.SPARE, frame.getType());
        assertFalse(frame.isCompleted());
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertEquals(15, frame.getExtraPinsKnockedDown());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest5() {
        //SPARE FRAME + 2rolls to forme a new spare
        int firstRoll = 5;
        int secondRoll = 10;
        int thirdRoll = 5;
        int fourRoll = 10;

        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(3, frame.getCurrentRoll());
        assertEquals(1, frame.getNumberOfRollsToListen());
        frame.numberOfFallenPinsDuringCurrentRoll(fourRoll);
        assertEquals(4, frame.getCurrentRoll());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertEquals(firstRoll + secondRoll + thirdRoll + fourRoll, frame.getScore());
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(FrameType.SPARE, frame.getType());
        assertTrue(frame.isCompleted());
        assertEquals(30, frame.getExtraPinsKnockedDown());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest6() {
        //SPARE FRAME + 2rolls regular
        int firstRoll = 5;
        int secondRoll = 10;
        int thirdRoll = 5;
        int fourRoll = 1;

        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(3, frame.getCurrentRoll());
        assertEquals(1, frame.getNumberOfRollsToListen());
        assertEquals(5, frame.getPinsKnockedDown());
        assertEquals(15, frame.getExtraPinsKnockedDown());
        frame.numberOfFallenPinsDuringCurrentRoll(fourRoll);
        assertEquals(4, frame.getCurrentRoll());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertEquals(firstRoll + secondRoll + thirdRoll + fourRoll, frame.getScore());
        assertEquals(6, frame.getPinsKnockedDown());
        assertEquals(FrameType.SPARE, frame.getType());
        assertTrue(frame.isCompleted());
        assertEquals(15, frame.getExtraPinsKnockedDown());
    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest7() {
        // test 3 rolls, values 5 and 7 and 2
        //SPARE FRAME + 2 strike
        int firstRoll = 5;
        int secondRoll = 7;
        int thirdRoll = 3;
        int fourRoll = 15;
        int fiftRoll = 15;
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
        assertEquals(0, frame.getPinsKnockedDown());
        frame.numberOfFallenPinsDuringCurrentRoll(fourRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(30, frame.getExtraPinsKnockedDown());
        assertEquals(1, frame.getNumberOfRollsToListen());
        frame.numberOfFallenPinsDuringCurrentRoll(fiftRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(45, frame.getExtraPinsKnockedDown());
        assertEquals(firstRoll + secondRoll + thirdRoll + fourRoll + fiftRoll, frame.getScore());
        assertEquals(FrameType.SPARE, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());

    }

    @Test
    void numberOfFallenPinsDuringCurrentRollTest8() {
        // test 4 rolls full strike
        //Strike
        int firstRoll = 15;
        int secondRoll = 15;
        int thirdRoll = 15;
        int fourRoll = 15;

        assertEquals(0, frame.getExtraPinsKnockedDown());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertEquals(0, frame.getPinsKnockedDown());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(15, frame.getExtraPinsKnockedDown());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(30, frame.getExtraPinsKnockedDown());
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(45, frame.getExtraPinsKnockedDown());
        assertEquals(1, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(0, frame.getNumberOfRollsToListen());

        assertEquals(firstRoll + secondRoll + thirdRoll + fourRoll, frame.getScore());
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(FrameType.STRIKE, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
        assertEquals(60, frame.getExtraPinsKnockedDown());
    }


    @Test
    void numberOfFallenPinsDuringCurrentRollTest9() {
        //try to add another input after the end of the frame
        // test 5 rolls full strike
        //Strike
        int firstRoll = 15;
        int secondRoll = 15;
        int thirdRoll = 15;
        int fourRoll = 15;
        int fiftRoll = 15;

        assertEquals(0, frame.getExtraPinsKnockedDown());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertEquals(0, frame.getPinsKnockedDown());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(firstRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(15, frame.getExtraPinsKnockedDown());
        assertEquals(3, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(secondRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(30, frame.getExtraPinsKnockedDown());
        assertEquals(2, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(thirdRoll);
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(45, frame.getExtraPinsKnockedDown());
        assertEquals(1, frame.getNumberOfRollsToListen());
        assertFalse(frame.isCompleted());
        frame.numberOfFallenPinsDuringCurrentRoll(fourRoll);
        assertEquals(0, frame.getNumberOfRollsToListen());

        assertEquals(firstRoll + secondRoll + thirdRoll + fourRoll, frame.getScore());
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(FrameType.STRIKE, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
        assertEquals(60, frame.getExtraPinsKnockedDown());
        frame.numberOfFallenPinsDuringCurrentRoll(fiftRoll);
        assertEquals(firstRoll + secondRoll + thirdRoll + fourRoll, frame.getScore());
        assertEquals(0, frame.getPinsKnockedDown());
        assertEquals(FrameType.STRIKE, frame.getType());
        assertEquals(0, frame.getNumberOfRollsToListen());
        assertTrue(frame.isCompleted());
        assertEquals(60, frame.getExtraPinsKnockedDown());
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