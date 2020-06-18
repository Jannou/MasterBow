package com.nanabaskint.core.Frames;

import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.core.Utils;
import com.nanabaskint.gui.controllers.CBasicFrame;

/**
 * basic frame is composed by 3 rolls. totals pins = 15.
 */
public class BasicFrame implements Frame {


    // the game
    private final Game game;
    // GUI component
    private final CBasicFrame cBasicFrame;
    // total of fallen pins during the frame
    private int pinsKnockedDown = 0;
    // current score of the frame
    private int score = 0;
    // type of the frame
    private FrameType type = FrameType.REGULAR;
    // index of the current roll
    private int currentRoll = 0;
    private int numberOfRollsToListen = numberOfRollsComposingTheFrame;
    //conductor
    private Conductor conductor;


    /**
     * A frame of our African bowling. A frame is a component of a game, it have to subcribe to a conductor,
     * and have to be paired with a GUI component called cBasicFrame
     *
     * @param _game        the game to which the frame below.
     * @param _conductor   the conductor for this frame.
     * @param _cBasicFrame the GUI controller paired with this frame.
     */
    public BasicFrame(Game _game, Conductor _conductor, CBasicFrame _cBasicFrame) {

        //subscribe new frame to the conductor
        conductor = _conductor;

        try {
            conductor.subscribe(this);
        } catch (NullPointerException e) {
            System.out.println("Warning : Conductor into BasicFrame is null, frame can not subscribe to conductor ! ");
        }

        cBasicFrame = _cBasicFrame;
        game = _game;


        try {
            cBasicFrame.activateInput(0);
        } catch (Exception e) {
            System.out.println("Warning : BasicFrame controller into BasicFrame is null ");
        }
    }

    @Override
    public void numberOfFallenPinsDuringCurrentRoll(int numberOfFallenPinsDuringCurrentRoll) {

        if (numberOfRollsToListen > 0) {
            try {
                //test if input does not exceed the max value allowed for frame.
                // but we don't have to perform this check if the frame is a spare or a strike
                // because the next frame already do it.
                if (type == FrameType.REGULAR) {
                    Utils.checkValue(numberOfFallenPinsDuringCurrentRoll, pinsKnockedDown, MAX_SUM);
                }

                // update number of pins knocked down
                pinsKnockedDown += numberOfFallenPinsDuringCurrentRoll;

                // update counter of roll to listen
                numberOfRollsToListen--;

                //activate next input, is frame is completed then there is another frame having an activated input on the com.nanabaskint.gui.
                activateInputOnGUI(isCompleted() ? -1 : currentRoll + 1);

                // update type of frame
                if (type == FrameType.REGULAR && pinsKnockedDown == 15) {
                    if (currentRoll == 0) { // strike
                        setAsStrike(); // deactivates all input of the frame
                    } else {
                        setAsSpare(currentRoll); // deactivates all input of the frame
                    }
                    //reset this counter to keep up to date the number of pins knocked down during next rolls.
                    pinsKnockedDown = MAX_SUM;
                }

                //update current Roll
                currentRoll++;

                //compute score
                updateScore();

                // update conductor subscription
                updateSubscriptionStatus();

            } catch (Utils.PinOutOfBound pinOutOfBound) {
                if (game != null) {
                    game.alert(pinOutOfBound.getMessage());
                    activateInputOnGUI(currentRoll);
                }
            }
        }
    }


    /**
     * Activate the frame for input
     *
     * @param indexOfInput index of input to activate
     */
    private void activateInputOnGUI(int indexOfInput) {
        if (cBasicFrame != null) {
            cBasicFrame.activateInput(indexOfInput);
        }
    }

    /**
     * Set the frame as a spare.
     *
     * @param currentRoll index of the roll that is a spare
     */
    private void setAsSpare(int currentRoll) {
        type = FrameType.SPARE;
        if (cBasicFrame != null) {
            cBasicFrame.isASpare(currentRoll);
        }
        numberOfRollsToListen = numberOfRollsToListenForASpare;
    }


    /**
     * Set the frame as a strike.
     */
    private void setAsStrike() {
        type = FrameType.STRIKE;
        if (cBasicFrame != null) {
            cBasicFrame.isAStrike();
        }
        numberOfRollsToListen = numberOfRollsToListenForAStrike;

    }

    /**
     * check when to unsubscribe
     */
    private void updateSubscriptionStatus() {
        if (numberOfRollsToListen == 0 && conductor != null) {
            conductor.unsubscribe(this);
        }
    }

    @Override
    public boolean isCompleted() {
        return numberOfRollsToListen == 0 // no rolls to listen (end of regular) => new frame needed
                || type != FrameType.REGULAR;// if not regular then it's a spare or a strike
        // whatever, frame is completed => new frame needed
        // due to probability and lazy eval, it's better to test in this order (for non proff player)
    }


    /**
     * This method keep the score up to date after each throw.
     */
    private void updateScore() {
        int oldScore = score;
        score = pinsKnockedDown;

        try {
            // keep global score up to date
            // add the delta after-before
            game.updateScore(score - oldScore);
        } catch (Exception e) {
            System.out.println("Warning : Game into BasicFrame is null");
        }
        updateDisplay();
    }

    /**
     * Uodate the GUI after computing the score.
     */
    private void updateDisplay() {

        try {
            cBasicFrame.setScore(String.valueOf(score)); // cleaner than score+""
        } catch (Exception e) {
            System.out.println("Warning : There is no GUI to pair with " + this);
        }

    }

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return pinsKnockedDown
     */
    public int getPinsKnockedDown() {
        return pinsKnockedDown;
    }

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return type
     */
    public FrameType getType() {
        return type;
    }

    public int getCurrentRoll() {
        return currentRoll;
    }

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return
     */
    public int getNumberOfRollsToListen() {
        return numberOfRollsToListen;
    }

    /**
     * Setter for Test Purpose to avoid use of reflexion
     *
     * @param conductor the conductor for this frame
     */
    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    @Override
    public String toString() {
        return "BasicFrame{" +
                " type=" + type +
                ", pinsKnockedDown=" + pinsKnockedDown +
                ", score=" + score +
                ", numberOfRollsToListen=" + numberOfRollsToListen +
                ", is completed ? " + isCompleted() +
                ", GUI component= " + (cBasicFrame != null ? cBasicFrame.getTitle() : "null ") + '}';
    }
}
