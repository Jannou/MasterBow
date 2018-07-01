import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class TestFrame{
    private Frame testFrame;
    private Frame testFrameStrike;

    @BeforeEach
    void setUp() {
        testFrame = new Frame(7,1 );
        testFrameStrike = new Frame();
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
        testFrameStrike.addRoll(9);
        Assertions.assertEquals(9,testFrameStrike.getNumberOfPinsKnockedDownByTheSecondBall());
        testFrameStrike.addRoll(7);
        Assertions.assertEquals(7,testFrameStrike.getNumberOfPinsKnockedDownByTheThirdBall());
        testFrame = new Frame(9,1);
        testFrame.addRoll(7);
        Assertions.assertEquals(1,testFrame.getNumberOfPinsKnockedDownByTheSecondBall() );
        Assertions.assertEquals(7,testFrame.getNumberOfPinsKnockedDownByTheThirdBall());
        testFrame = new Frame(9,0);
        testFrame.addRoll(7);
        Assertions.assertEquals(0,testFrame.getNumberOfPinsKnockedDownByTheSecondBall() );
        Assertions.assertEquals(-1,testFrame.getNumberOfPinsKnockedDownByTheThirdBall());

    }

}