/**
 * This class represent a frame of a Bowling game.
 * A frame can either be a regular one, or a strike or a spare.
 * A frame is composed by 2 rolls.
 * If the frame is for a strike then frame contains one roll.
 * if the frame is the final frame of a game (i.e. the 10th frame)
 * then it may contain up to 2 (resp. 3) rolls iff the player perform a strike (resp. spare) before.
 * Each roll is characterized by a number of fallen pins.
 * A frame has also a pinsKnockedDown.
 */
public class Frame {

    //TODO change design

    // type of the frame
    private final KindOfFrame type;
    // total of fallen pins during the frame
    private final int pinsKnockedDown;
    // score of the frame ;
    private int score = 0;
    // number of pins knocked over during the first roll of the frame
    private int numberOfPinsKnockedDownByTheFirstBall;
    // number of pins knocked over during the second roll of the frame
    private int numberOfPinsKnockedDownByTheSecondBall;

    // error msg
    private static final String RULES_ERROR = "violation of the rules : total of pins knocked down during a frame is over 10  |%d:%d|... Check the input please.";

    /**
     * Construct a frame which isn't a strike (2rolls)
     * @param resultOfTheFirstRoll number of pins knocked over during the first roll
     * @param resultOfTheSecondRoll number of pins knocked over during the second roll
     */
    Frame(int resultOfTheFirstRoll, int resultOfTheSecondRoll){
        numberOfPinsKnockedDownByTheFirstBall = resultOfTheFirstRoll;
        numberOfPinsKnockedDownByTheSecondBall = resultOfTheSecondRoll;
        pinsKnockedDown = numberOfPinsKnockedDownByTheFirstBall + numberOfPinsKnockedDownByTheSecondBall;
        if(pinsKnockedDown >10){
            System.out.format(RULES_ERROR, numberOfPinsKnockedDownByTheFirstBall , numberOfPinsKnockedDownByTheSecondBall);
            System.exit(1);
        }
        type = pinsKnockedDown==10? KindOfFrame.Spare: KindOfFrame.Regular;
    }

    /**
     * Construct a strike frame (1roll).
     */
    Frame(boolean isLast){
        type = KindOfFrame.Strike;
        numberOfPinsKnockedDownByTheFirstBall=isLast?-1:10;
        pinsKnockedDown = 10;
    }



    /**
     * Return the type of the frame.
     * @return the type of the frame.
     */
    KindOfFrame getType() {
        return type;
    }

    /**
     * Return the number of pins knocked down during the frame.
     * @return the number of pins knocked down during the frame.
     */
    int getPinsKnockedDown() {
        return pinsKnockedDown;
    }

    /**
     * Return the number of fallen pin during the first roll.
     * @return the number of fallen pin during the first roll.
     */
    int getNumberOfPinsKnockedDownByTheFirstBall() {
        return numberOfPinsKnockedDownByTheFirstBall;
    }

    /**
     * Return the number of fallen pin during the second roll.
     * @return the number of fallen pin during the second roll.
     */
    int getNumberOfPinsKnockedDownByTheSecondBall() {
        return numberOfPinsKnockedDownByTheSecondBall;
    }

    /**
     * Return the score of the frame.
     * @return the score of the frame.
     */
    int getScore() {
        return score;
    }

    /**
     * Set the score of the frame.
     * @param score the new score of the frame.
     */
    void setScore(int score) {
        this.score = score;
    }


    /**
     * Customise a Frame to manage 10th frame behavior
     * Yeap, I did that x)
     *
     * @param arg1 first roll to be played
     * @return 0 if no rule violation -1 otherwise
     */
    int customiseToLastFrame(int arg1){
        int result = -1; // try to customize finished frame  or more than 3 rolls

        if(getType()==KindOfFrame.Strike){                                                                     //STRIKE => up to 2 extra roll to be played
             if(numberOfPinsKnockedDownByTheFirstBall==-1) {
                numberOfPinsKnockedDownByTheFirstBall = arg1;
                result = 0;
            }else if(numberOfPinsKnockedDownByTheSecondBall==-1) {
                numberOfPinsKnockedDownByTheSecondBall = arg1;
                result = 0;
            }
        }else if(getType()==KindOfFrame.Spare && numberOfPinsKnockedDownByTheFirstBall<10){                     //SPARE 1 extra roll to be played
            numberOfPinsKnockedDownByTheSecondBall= arg1;
            result = 0;
        }
        return result;
    }




    @Override
    public String toString() {
        return "Frame{type="
                + type + ", pinsKnockedDown="
                + pinsKnockedDown + ", numberOfPinsKnockedDownByTheFirstBall="
                + numberOfPinsKnockedDownByTheFirstBall + ", numberOfPinsKnockedDownByTheSecondBall="
                + numberOfPinsKnockedDownByTheSecondBall +", score="+getScore()+'}';
    }


    ////////////////////////////////////////////////////////////////////





    /**
     * In accordance with the fallen pins, we consider that a frame may be either a spare, a strike or a regular one.
     */
    public enum KindOfFrame{
        Regular,
        Strike,
        Spare
    }
}
