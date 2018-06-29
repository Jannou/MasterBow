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
    void getType() {
        Assertions.assertEquals(Frame.KindOfFrame.Strike,testFrameStrike.getType() );
        Assertions.assertEquals(Frame.KindOfFrame.Regular,testFrame.getType() );
        testFrame=new Frame(9,1 );
        Assertions.assertEquals(Frame.KindOfFrame.Spare,testFrame.getType() );
//        testFrame=new Frame(9,5); // => system -1 ok


    }

    @Test
    void getScore() {
        Assertions.assertEquals(10,testFrameStrike.getPinsKnockedDown() );
        Assertions.assertEquals(8,testFrame.getPinsKnockedDown() );

    }

    @Test
    void getNumberOfFallenPinByTheFirstBall() {
        Assertions.assertEquals(10,testFrameStrike.getNumberOfFallenPinByTheFirstBall() );

        Assertions.assertEquals(7,testFrame.getNumberOfFallenPinByTheFirstBall() );

    }

    @Test
    void getNumberOfFallenPinByTheSecondBall() {

        Assertions.assertEquals(0,testFrameStrike.getNumberOfFallenPinByTheSecondBall() );

        Assertions.assertEquals(1,testFrame.getNumberOfFallenPinByTheSecondBall() );
    }
}