import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.frames.BasicFrame;
import com.nanabaskint.core.frames.FrameType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestConductor {

    private BasicFrame frame1;
    private BasicFrame frame2;
    private BasicFrame frame3;
    private BasicFrame frame4;
    private Conductor conductor;

    @BeforeEach
    void setUp() {
        conductor = new Conductor(4);
        frame1 = new BasicFrame(null, null, null);
        frame2 = new BasicFrame(null, null, null);
        frame3 = new BasicFrame(null, null, null);
        frame4 = new BasicFrame(null, null, null);
    }

    @AfterEach
    void tearDown() {
        conductor = null;
        frame1 = null;
        frame2 = null;
        frame3 = null;
        frame4 = null;
    }


    @Test
    void subscribe() {
        frame1.setConductor(conductor);
        conductor.subscribe(frame1);
        assertEquals(0, conductor.getFramesToInform().indexOf(frame1));
        frame2.setConductor(conductor);
        conductor.subscribe(frame2);
        assertEquals(1, conductor.getFramesToInform().indexOf(frame2));
        frame3.setConductor(conductor);
        conductor.subscribe(frame3);
        assertEquals(2, conductor.getFramesToInform().indexOf(frame3));
        frame4.setConductor(conductor);
        conductor.subscribe(frame4);
        assertEquals(3, conductor.getFramesToInform().indexOf(frame4));
    }

    @Test
    void unsubscribe() {
        frame1 = new BasicFrame(null, conductor, null);
        assertEquals(0, conductor.getFramesToInform().indexOf(frame1));
        conductor.unsubscribe(frame1);
        conductor.updateStack();
        assertFalse(conductor.getFramesToInform().contains(frame1));
    }

    @Test
    void informAll1() {
        //regular frame
        int input = 4;
        frame1 = new BasicFrame(null, conductor, null);
        conductor.informAll(input);
        assertEquals(2, frame1.getNumberOfRollsToListen());
        assertEquals(1, frame1.getCurrentRoll());
        assertEquals(input, frame1.getScore());
        assertEquals(input, frame1.getPinsKnockedDown());
        conductor.informAll(input);
        assertEquals(1, frame1.getNumberOfRollsToListen());
        assertEquals(2, frame1.getCurrentRoll());
        assertEquals(2 * input, frame1.getScore());
        assertEquals(2 * input, frame1.getPinsKnockedDown());
        conductor.informAll(input);
        assertEquals(0, frame1.getNumberOfRollsToListen());
        assertEquals(3, frame1.getCurrentRoll());
        assertEquals(3 * input, frame1.getScore());
        assertEquals(3 * input, frame1.getPinsKnockedDown());
        // frame is completed => have to be unsubscibed
        assertTrue(conductor.getFramesToInform().isEmpty());
    }

    @Test
    void informAll2() {
        int input = 5;
        frame1 = new BasicFrame(null, conductor, null);
        conductor.informAll(input);
        assertEquals(2, frame1.getNumberOfRollsToListen());
        assertEquals(1, frame1.getCurrentRoll());
        assertEquals(input, frame1.getScore());
        assertEquals(input, frame1.getPinsKnockedDown());
        conductor.informAll(input);
        assertEquals(1, frame1.getNumberOfRollsToListen());
        assertEquals(2, frame1.getCurrentRoll());
        assertEquals(2 * input, frame1.getScore());
        assertEquals(2 * input, frame1.getPinsKnockedDown());
        conductor.informAll(input);

        assertEquals(3, frame1.getCurrentRoll());
        assertEquals(3 * input, frame1.getScore());
        assertEquals(3 * input, frame1.getPinsKnockedDown());
        assertEquals(FrameType.SPARE, frame1.getType());
        //it's a spare, frame need to listen 2 next rolls
        assertEquals(2, frame1.getNumberOfRollsToListen());
        assertFalse(conductor.getFramesToInform().isEmpty());
        conductor.informAll(input);
        assertEquals(1, frame1.getNumberOfRollsToListen());
        assertEquals(4, frame1.getCurrentRoll());
        assertEquals(4 * input, frame1.getScore());
        assertEquals(4 * input, frame1.getPinsKnockedDown());
        conductor.informAll(input);
        assertEquals(0, frame1.getNumberOfRollsToListen());
        assertEquals(5, frame1.getCurrentRoll());
        assertEquals(5 * input, frame1.getScore());
        assertEquals(5 * input, frame1.getPinsKnockedDown());
        //spare completed, unsub
        assertTrue(conductor.getFramesToInform().isEmpty());
    }


    @Test
    void informAll3() {
        //fill up conductor with strike
        int input = 15;
        frame1 = new BasicFrame(null, conductor, null);
        conductor.informAll(input);
        //strike => new frame
        frame2 = new BasicFrame(null, conductor, null);
        conductor.informAll(input);
        //strike => new frame
        frame3 = new BasicFrame(null, conductor, null);
        conductor.informAll(input);
        //strike => new frame
        frame4 = new BasicFrame(null, conductor, null);

        assertTrue(conductor.getFramesToInform().contains(frame1)); // strike on frame1 is not completed yet

        conductor.informAll(input);
        assertFalse(conductor.getFramesToInform().contains(frame1));// strike on frame1 is completed
        assertTrue(conductor.getFramesToInform().contains(frame2));// strike on frame2 is not completed yet

        conductor.informAll(input);
        assertFalse(conductor.getFramesToInform().contains(frame2));// strike on frame2 is completed
        assertTrue(conductor.getFramesToInform().contains(frame3));// strike on frame3 is not completed yet

        conductor.informAll(input);
        assertFalse(conductor.getFramesToInform().contains(frame3));// strike on frame3 is completed
        assertTrue(conductor.getFramesToInform().contains(frame4));// strike on frame4 is not completed yet

        conductor.informAll(input);
        assertFalse(conductor.getFramesToInform().contains(frame4));// strike on frame4 is completed

        assertTrue(conductor.getFramesToInform().isEmpty());// stack is empty
    }

    @Test
    void clean() {
        conductor.subscribe(frame1);
        conductor.subscribe(frame2);
        conductor.subscribe(frame3);
        conductor.subscribe(frame4);
        assertTrue(!conductor.getFramesToInform().isEmpty());
        conductor.clear();
        assertTrue(conductor.getFramesToInform().isEmpty());
    }


}