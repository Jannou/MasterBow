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
    private KindOfFrame type;
    // total of fallen pins during the frame
    private int pinsKnockedDown;
    // score of the frame ;
    private int score = 0;
    // number of pins knocked over during the first roll of the frame
    private int numberOfPinsKnockedDownByTheFirstBall;
    // number of pins knocked over during the second roll of the frame
    private int numberOfPinsKnockedDownByTheSecondBall=-1;
    // number of pins knocked over during the third roll of the frame (for final frame)
    private int numberOfPinsKnockedDownByTheThirdBall=-1;

    /**
     * qick fix t handler last frame (only for final frame)
     * @param numberOfPinsKnockedDown pins knocked down during next roll
     */
    int addRoll(int numberOfPinsKnockedDown){

        int retour = 0;
        if(numberOfPinsKnockedDownByTheSecondBall== -1){
            numberOfPinsKnockedDownByTheSecondBall = numberOfPinsKnockedDown;
        } else if(numberOfPinsKnockedDownByTheThirdBall == -1 && getType()!=KindOfFrame.Regular){
            numberOfPinsKnockedDownByTheThirdBall = numberOfPinsKnockedDown;
        } else
            retour = -1;
        return retour;
    }

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
            System.out.println("violation of the rules : total of pins knocked down during a frame is over 10  |"
                    + numberOfPinsKnockedDownByTheFirstBall +":"+ numberOfPinsKnockedDownByTheSecondBall +"|... check the input please.");
            System.exit(1);
        }
        type = numberOfPinsKnockedDownByTheFirstBall + numberOfPinsKnockedDownByTheSecondBall ==10? KindOfFrame.Spare: KindOfFrame.Regular;
    }

    /**
     * Construct a strike frame (1roll).
     */
    Frame(){
        type = KindOfFrame.Strike;
        numberOfPinsKnockedDownByTheFirstBall = 10;
        pinsKnockedDown = numberOfPinsKnockedDownByTheFirstBall;
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

    @Override
    public String toString() {
        return "Frame{type=" + type + ", pinsKnockedDown=" + pinsKnockedDown + ", numberOfPinsKnockedDownByTheFirstBall=" + numberOfPinsKnockedDownByTheFirstBall + ", numberOfPinsKnockedDownByTheSecondBall=" + numberOfPinsKnockedDownByTheSecondBall + ", numberOfPinsKnockedDownByTheThirdBall=" + numberOfPinsKnockedDownByTheThirdBall +", score="+getScore()+'}';
    }

    /**
     * Set the number of fallen pin during the third roll.
     * @param numberOfPinsKnockedDownByTheThirdBall the number of fallen pin during the third roll.
     */
    public void setNumberOfPinsKnockedDownByTheThirdBall(int numberOfPinsKnockedDownByTheThirdBall) {
        this.numberOfPinsKnockedDownByTheThirdBall = numberOfPinsKnockedDownByTheThirdBall;
    }

    /**
     * Return the number of fallen pin during the third roll.
     * @return the number of fallen pin during the third roll.
     */
    int getNumberOfPinsKnockedDownByTheThirdBall() {
        return numberOfPinsKnockedDownByTheThirdBall;
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
