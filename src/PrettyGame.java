import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to build a PrettyGame and compute the score for the given input
 */
public class PrettyGame {

    // error msg
    private static final String LAST_FRAME_FORMAT_ERROR="The last frame may contain up to three rolls iff the player perform a strike or a spare before ... Check the input please.";

    private Frame[] frames;
    private int score =0;

    private PrettyGame(Frame[] _frames){
        frames= _frames;
    }

    /**
     * Build a game on the basis of a array on integers
     * @return a game representing the inputs
     */
    static PrettyGame buildGame(int[] fallenPins) {

        int nbrOfrolls = fallenPins.length;
        ArrayList<Frame> frames = new ArrayList<>(9);
        int i = 0;

        while(frames.size()<9 && i<nbrOfrolls ){
            i = buildFrame(fallenPins, nbrOfrolls, frames, i);
        }
        if(i <  nbrOfrolls ){  //last Frame
            ArrayList<Frame> tmpArray = new ArrayList<>(1);
            i = buildLastFrame(fallenPins, nbrOfrolls, tmpArray, i);
            Frame lastFrame = tmpArray.get(0);
            //get 3rolls
            while(i < nbrOfrolls) {
                if (lastFrame.customiseToLastFrame(fallenPins[i])==-1){ // fail if 3 rolls for a regular frame or if more than 3 roll for a strike or a spare
                    System.out.println(LAST_FRAME_FORMAT_ERROR);
                    System.exit(1);
                }
                i++;
            }
            frames.add(0,lastFrame);
        }
        PrettyGame prettyGame = new PrettyGame(frames.toArray(new Frame[0]));

        if(!frames.isEmpty()) // check empty game
            prettyGame.computeScore();

        return prettyGame;
    }


    private static int buildFrame(int[] fallenPins, int nbrOfrolls, ArrayList<Frame> frames, int i) {
        return buildNewFrame(fallenPins,nbrOfrolls , frames, i,false);
    }

    private static int buildLastFrame(int[] fallenPins, int nbrOfrolls, ArrayList<Frame> frames, int i) {
        return buildNewFrame(fallenPins,nbrOfrolls , frames, i,true);
    }

    private static int buildNewFrame(int[] fallenPins, int nbrOfrolls, ArrayList<Frame> frames, int i,boolean lastFrame) {
        Frame currentFrame = new Frame(lastFrame);
        if(fallenPins[i]!=10){
            currentFrame = new Frame( fallenPins[i], i==nbrOfrolls-1?0:fallenPins[i+1]);
            i++;
        }
        i++;
        frames.add(0,currentFrame);
        return i;
    }

    /**
     * Return the total score for this game.
     * @return the total score for this game.
     */
    int getTotalScore(){
        return score;
    }

    /**
     * Compute the total score for a game
     */
    private void computeScore() {

        int pinsKnockedDownDuringFirstNextRoll=0;
        int pinsKnockedDownDuringSecondNextRoll=0;

        int scoreOfFrame = frames[0].getPinsKnockedDown();

        //no choice here, have to handle 10th frame separately ...
        if(frames.length==10){ // handle 10th frame
            if(frames[0].getType()== Frame.KindOfFrame.Spare){
                scoreOfFrame+=frames[0].getNumberOfPinsKnockedDownByTheSecondBall();
                pinsKnockedDownDuringFirstNextRoll=frames[0].getNumberOfPinsKnockedDownByTheFirstBall();
                pinsKnockedDownDuringSecondNextRoll = 10 - pinsKnockedDownDuringFirstNextRoll;

            }else if(frames[0].getType()== Frame.KindOfFrame.Strike){
                pinsKnockedDownDuringFirstNextRoll = 10;
                pinsKnockedDownDuringSecondNextRoll = frames[0].getNumberOfPinsKnockedDownByTheFirstBall();
                scoreOfFrame+=pinsKnockedDownDuringSecondNextRoll+frames[0].getNumberOfPinsKnockedDownByTheSecondBall();
            }
            frames[0].setScore(scoreOfFrame);
            score+=scoreOfFrame;
        }

        for(Frame frame : Arrays.copyOfRange(frames, frames.length==10?1:0, frames.length)){
            scoreOfFrame = frame.getPinsKnockedDown();
            if(frame.getType()== Frame.KindOfFrame.Strike)
                scoreOfFrame+=pinsKnockedDownDuringFirstNextRoll+pinsKnockedDownDuringSecondNextRoll;
            else{
                if(frame.getType()== Frame.KindOfFrame.Spare)
                    scoreOfFrame+=pinsKnockedDownDuringFirstNextRoll;
                pinsKnockedDownDuringFirstNextRoll  = frame.getNumberOfPinsKnockedDownByTheSecondBall();
            }
            pinsKnockedDownDuringSecondNextRoll = pinsKnockedDownDuringFirstNextRoll ;
            pinsKnockedDownDuringFirstNextRoll = frame.getNumberOfPinsKnockedDownByTheFirstBall();
            frame.setScore(scoreOfFrame);
            score+=scoreOfFrame;
        }
    }

    @Override
    public String toString() {
        //look nice but useless.

        StringBuilder sep = new StringBuilder();
        String spaces="   ";
        String col = "|";
        StringBuilder MyFrame = new StringBuilder(col);
        StringBuilder Result  = new StringBuilder(col);
        StringBuilder Score = new StringBuilder(col);
        int score =0;
        String toString ="";

        for(int i = frames.length-1;i>=(frames.length==10?1:0);i--){ // O(n) just to display :/
            sep.append("####");
            MyFrame.append(" ").append(frames.length - i).append(String.valueOf((frames.length - i)).length() < 2 ? " " : "").append(col);
            Result.append(frames[i].getType() == Frame.KindOfFrame.Regular ?
                    frames[i].getNumberOfPinsKnockedDownByTheFirstBall() + ":" + frames[i].getNumberOfPinsKnockedDownByTheSecondBall()
                    : frames[i].getType() == Frame.KindOfFrame.Spare ? " / " : " X ").append(col);
            score +=frames[i].getScore();
            Score.append(spaces, 0, 3 - String.valueOf(score).length()).append(score).append(col);
        }

        if(frames.length==10){
            sep.append("####");
            MyFrame.append(" ").append(frames.length).append(String.valueOf((frames.length )).length() < 2 ? " " : "").append(col);
            Result.append(frames[0].getType() == Frame.KindOfFrame.Regular ?
                    frames[0].getNumberOfPinsKnockedDownByTheFirstBall() + ":" + frames[0].getNumberOfPinsKnockedDownByTheSecondBall() :
                    frames[0].getType() == Frame.KindOfFrame.Spare ? "/ "+formateStrike(frames[0].getNumberOfPinsKnockedDownByTheSecondBall()):
                            "X"+formateStrike(frames[0].getNumberOfPinsKnockedDownByTheFirstBall())+formateStrike(frames[0].getNumberOfPinsKnockedDownByTheSecondBall())).append(col);
            score +=frames[0].getScore();
            Score.append(spaces, 0, 3 - String.valueOf(score).length()).append(score).append(col);
        }


        sep.append("#");

        toString+=sep+" Frame\n"+MyFrame+"\n"+sep+"\n"+Result+"\n"+sep+" Score\n"+Score;

        return toString;
    }

    String formateStrike(int i){
        if(i==10) return "X";
        else return String.valueOf(i);
    }
}
