import java.util.ArrayList;

/**
 * This class is used to build a ingeniousVersion.PrettyGame and compute the score for the given input
 */
public class PrettyGame {

    private Frame[] frames;
    private int score =0;

    private PrettyGame(Frame[] _frames){
        if(_frames.length>12){
            System.out.println("a bowling game may not contain more than 12 frames ... check the input please.");
            System.exit(1);
        }
        if((_frames.length==11 &&  _frames[9].getType()!= Frame.KindOfFrame.Spare)  ||
                (_frames.length==12 && _frames[9].getType()!= Frame.KindOfFrame.Strike)){
            System.out.println("A bowling game may contain 11(resp 12) frames iff the 10th frame is a spare (resp. a strike)... check the input please.");
            System.exit(1);
        }
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
            if(fallenPins[i]!=10){
                Frame tmp = new Frame( fallenPins[i], i==nbrOfrolls-1?0:fallenPins[i+1]);
                frames.add(0,tmp);
                i+=2;
            }else{
                frames.add(0,new Frame());
                i++;
            }
        }
        //last Frame
        if(i <  nbrOfrolls ){
            Frame lastOne;
            if(fallenPins[i]!=10){
                lastOne = new Frame( fallenPins[i], i==nbrOfrolls-1?0:fallenPins[i+1]);
                i++;
            }else{
                lastOne= new Frame();
            }
            i++;
            //get 3rolls
            while(i < nbrOfrolls) {
                if (lastOne.getType()== Frame.KindOfFrame.Regular || lastOne.addRoll(fallenPins[i])==-1){
                    System.out.println("The last frame may contain up to three rolls iff the player" +
                            " perform a strike or a spare before ... Check the input please.");
                    System.exit(1);
                }
                i++;
            }
            frames.add(0,lastOne);
        }

        PrettyGame prettyGame = new PrettyGame(frames.toArray(new Frame[0]));

        if(!frames.isEmpty()) // check empty game
            prettyGame.computeScore();

        return prettyGame;
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

        //start by the last frame => check if third rolls or not
        if(frames[0].getNumberOfPinsKnockedDownByTheThirdBall()!=-1){
            //inversion to handle spare rule
            pinsKnockedDownDuringFirstNextRoll=frames[0].getNumberOfPinsKnockedDownByTheThirdBall();
            pinsKnockedDownDuringSecondNextRoll=frames[0].getNumberOfPinsKnockedDownByTheSecondBall();
        }

        for(Frame frame : frames){
            int scoreOfFrame = frame.getPinsKnockedDown();
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
                    frames[0].getNumberOfPinsKnockedDownByTheFirstBall() + ":" + frames[0].getNumberOfPinsKnockedDownByTheSecondBall()
                    : frames[0].getType() == Frame.KindOfFrame.Spare ? "/ "+(frames[0].getNumberOfPinsKnockedDownByTheThirdBall()==10?"X":frames[0].getNumberOfPinsKnockedDownByTheThirdBall()) : "X"+frames[0].getNumberOfPinsKnockedDownByTheSecondBall()+frames[0].getNumberOfPinsKnockedDownByTheThirdBall()).append(col);
            score +=frames[0].getScore();
            Score.append(spaces, 0, 3 - String.valueOf(score).length()).append(score).append(col);
        }

        sep.append("#");

        toString+=sep+" Frame\n"+MyFrame+"\n"+sep+"\n"+Result+"\n"+sep+" Score\n"+Score;

        return toString;
    }
}
