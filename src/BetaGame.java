import java.util.ArrayList;

/**
 * This class is used to build a ingeniousVersion.PrettyGame and compute the score for the given input
 */
class BetaGame {

    private static ArrayList<Frame> frames = new ArrayList<>();
    private static int score=0;
    private static int numberOfRolls;
    private static int frameNumber=0;
    private static int counter=0;




    static int getScore(String[] args) {
        buildFramesAndComputeScore(args);
        return score;
    }

    private static void buildFramesAndComputeScore(String[] args){
        numberOfRolls=args.length;
        while(counter<numberOfRolls){
            buildFrame(args);
        }
    }

    private static int buildFrame(String[] args){
        if(counter<args.length){
            int pinsKnockedDownDuringFirstRoll=Utils.preProcess(args[counter]);
            int pinsKnockedDownDuringSecondRoll;

            Frame currentFrame;

            if(pinsKnockedDownDuringFirstRoll<10){                                                               //spare or regular
                counter++;
                pinsKnockedDownDuringSecondRoll=Utils.preProcess(args[counter]);
                currentFrame = new Frame(pinsKnockedDownDuringFirstRoll,pinsKnockedDownDuringSecondRoll);
            }else{                                                                                               //strike
                currentFrame = new Frame();
            }

            counter++;

            int scoreOfCurrentFrame = computeScoreOfCurrentFrame(args, frames, currentFrame);
            score+=scoreOfCurrentFrame;
            frames.add(currentFrame);
            return counter;
        }

        return numberOfRolls+1;
    }

    private static int computeScoreOfCurrentFrame(String[] args, ArrayList<Frame> frames, Frame currentFrame) {
        int scoreOfCurrentFrame = currentFrame.getPinsKnockedDown();
        frameNumber++;
        if(currentFrame.getType()!= Frame.KindOfFrame.Regular) {
            buildFrame(args);
            scoreOfCurrentFrame+=frames.get(frameNumber-2).getNumberOfPinsKnockedDownByTheFirstBall();
            if (currentFrame.getType() == Frame.KindOfFrame.Strike)
                scoreOfCurrentFrame +=
                        frames.get(frameNumber-2).getType()== Frame.KindOfFrame.Strike?
                                frames.get(frameNumber++).getNumberOfPinsKnockedDownByTheFirstBall():
                                    frames.get(frameNumber-2).getNumberOfPinsKnockedDownByTheSecondBall();

        }
        currentFrame.setScore(scoreOfCurrentFrame);
        return scoreOfCurrentFrame;
    }

    private static void showFrame() {
        for(Frame frame:frames)
            System.out.println(frame);
    }
}
