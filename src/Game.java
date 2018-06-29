import java.util.ArrayList;


public class Game {

    private Frame[] frames;

    private Game(Frame[] _frames){
        if(_frames.length>12){
            System.out.println("a bowling game cannot contains more than 12 frames ... check the input please.");
            System.exit(1);
        }
        frames= _frames;
    }

    /**
     * Build a game on the basis of a array on integers
     * @return a game representing the inputs
     */
    static Game buildGame(Integer[] fallenPins) {

        int nbrOfrolls = fallenPins.length;
        ArrayList<Frame> frames = new ArrayList<>();
        int i = 0;
        while(i <  nbrOfrolls){
            if(fallenPins[i]!=10){
                Frame tmp = new Frame( fallenPins[i], i==nbrOfrolls-1?0:fallenPins[i+1]);
                frames.add(0,tmp);
                i+=2;
            }else{
                frames.add(0,new Frame());
                i++;
            }

        }

        Game game  = new Game(frames.toArray(new Frame[0]));
        game.computeScore();

        return game;
    }

    private void computeScore() {
        int tmp1=0;
        int tmp2=0;

        for(Frame frame : frames){

            int score = frame.getPinsKnockedDown();
            if(frame.getType()== Frame.KindOfFrame.Strike){
                score+=tmp1+tmp2;
            }
            else{
                if(frame.getType()== Frame.KindOfFrame.Spare){
                    score+=tmp1;
                }
                tmp1  = frame.getNumberOfFallenPinByTheSecondBall();
            }
            tmp2 = tmp1 ;
            tmp1 = frame.getNumberOfFallenPinByTheFirstBall();
            frame.setScore(score);

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

        for(int i = frames.length-1;i>=0;i--){ // O(n) just to display :/
            sep.append("####");
            MyFrame.append(" ").append(frames.length - i).append(String.valueOf((frames.length - i)).length() < 2 ? " " : "").append(col);
            Result.append(frames[i].getType() == Frame.KindOfFrame.Regular ?
                    frames[i].getNumberOfFallenPinByTheFirstBall() + ":" + frames[i].getNumberOfFallenPinByTheSecondBall()
                    : frames[i].getType() == Frame.KindOfFrame.Spare ? " / " : " X ").append(col);
            score +=frames[i].getScore();
            Score.append(spaces, 0, 3 - String.valueOf(score).length()).append(score).append(col);
        }
        sep.append("#");

        toString+=sep+" Frame\n"+MyFrame+"\n"+sep+"\n"+Result+"\n"+sep+" Score\n"+Score;

        return toString;
    }
}
