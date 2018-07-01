import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class TestFrame{
    private Frame testFrame;
    private Frame testFrameStrike;

    @BeforeEach
    void setUp() {
        testFrame = new Frame(7,1 );
        testFrameStrike = new Frame(false);
    }

    @Test
    void TestgetType() {
        Assertions.assertEquals(Frame.KindOfFrame.Strike,testFrameStrike.getType() );
        Assertions.assertEquals(Frame.KindOfFrame.Regular,testFrame.getType() );
        testFrame=new Frame(9,1 );
        Assertions.assertEquals(Frame.KindOfFrame.Spare,testFrame.getType() );
//        testFrame=new ingeniousVersion.Frame(9,5); // => system -1 ok


    }

    @Test
    void TestgetScore() {
        Assertions.assertEquals(10,testFrameStrike.getPinsKnockedDown() );
        Assertions.assertEquals(8,testFrame.getPinsKnockedDown() );

    }

    @Test
    void TestgetNumberOfFallenPinByTheFirstBall() {
        Assertions.assertEquals(10,testFrameStrike.getNumberOfPinsKnockedDownByTheFirstBall() );

        Assertions.assertEquals(7,testFrame.getNumberOfPinsKnockedDownByTheFirstBall() );

    }

    @Test
    void TestgetNumberOfFallenPinByTheSecondBall() {
        // by default a strike cannot have second ball
        Assertions.assertEquals(-1,testFrameStrike.getNumberOfPinsKnockedDownByTheSecondBall() );

        Assertions.assertEquals(1,testFrame.getNumberOfPinsKnockedDownByTheSecondBall() );
    }

    @Test
    void TestFinalFrame() {
        testFrameStrike = new Frame(true);
        testFrameStrike.customiseToLastFrame(9);
        Assertions.assertEquals(9,testFrameStrike.getNumberOfPinsKnockedDownByTheFirstBall());
        testFrameStrike.customiseToLastFrame(7);
        Assertions.assertEquals(7,testFrameStrike.getNumberOfPinsKnockedDownByTheSecondBall());
        testFrame = new Frame(9,1);
        testFrame.customiseToLastFrame(7);
        Assertions.assertEquals(9,testFrame.getNumberOfPinsKnockedDownByTheFirstBall());
        Assertions.assertEquals(7,testFrame.getNumberOfPinsKnockedDownByTheSecondBall() );
        testFrame = new Frame(9,0);
        Assertions.assertEquals(-1,testFrame.customiseToLastFrame(7));

    }

}