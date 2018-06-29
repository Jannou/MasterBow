/**
 * This class represent a frame of a Bowling game.
 * A frame can either be a regular one, or a strike or a spare.
 * A frame is composed by 2 rolls (except if it is a strike, in this case the frame correspond to one roll)
 * Each roll is characterized by a number of fallen pins.
 * A frame has also a score and can be the last of the game.
 */
public class Frame {

    // type of the frame
    private KindOfFrame type;
    // total of fallen pins during the frame
    private int score  ;
    // number of pins knocked over during the first roll of the frame
    private int numberOfFallenPinByTheFirstBall;
    // number of pins knocked over during the second roll of the frame
    private int numberOfFallenPinByTheSecondBall;


    /**
     * Construct a frame which isn't a strike (2rolls)
     * @param resultOfTheFirstRoll number of pins knocked over during the first roll
     * @param resultOfTheSecondRoll number of pins knocked over during the second roll
     */
    Frame(int resultOfTheFirstRoll, int resultOfTheSecondRoll){
        numberOfFallenPinByTheFirstBall = resultOfTheFirstRoll;
        numberOfFallenPinByTheSecondBall = resultOfTheSecondRoll;
        score = numberOfFallenPinByTheFirstBall + numberOfFallenPinByTheSecondBall;
        if(score>10){
            System.out.println("violation of the rules : total of fallen pins is over 10 for a frame |"
                    +numberOfFallenPinByTheFirstBall+":"+numberOfFallenPinByTheSecondBall+"|... check the input please.");
            System.exit(1);
        }
        type = numberOfFallenPinByTheFirstBall + numberOfFallenPinByTheSecondBall ==10? KindOfFrame.Spare: KindOfFrame.Regular;
    }

    /**
     * Construct a strike frame (1roll).
     */
    Frame(){
        numberOfFallenPinByTheFirstBall = 10;
        score = numberOfFallenPinByTheFirstBall;
        type = KindOfFrame.Strike;
    }

    /**
     * Return the type of the frame.
     * @return the type of the frame.
     */
    KindOfFrame getType() {
        return type;
    }

    /**
     * Return the score of the frame.
     * @return the score of the frame.
     */
    int getScore() {
        return score;
    }

    /**
     * Return the number of fallen pin during the first roll.
     * @return the number of fallen pin during the first roll.
     */
    int getNumberOfFallenPinByTheFirstBall() {
        return numberOfFallenPinByTheFirstBall;
    }

    /**
     * Return the number of fallen pin during the second roll.
     * @return the number of fallen pin during the second roll.
     */
    int getNumberOfFallenPinByTheSecondBall() {
        return numberOfFallenPinByTheSecondBall;
    }


    /**
     * Set the score of the frame.
     * @param score the new score.
     */
    void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Frame{type=" + type + ", score=" + score + ", numberOfFallenPinByTheFirstBall=" + numberOfFallenPinByTheFirstBall + ", numberOfFallenPinByTheSecondBall=" + numberOfFallenPinByTheSecondBall +'}';
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
