package com.nanabaskint.core.frames;


import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.core.Utils;
import com.nanabaskint.gui.controllers.CFinalFrame;

/**
 * The final frame of a game. it might be composed by 3,4 or 5 rolls.
 */
public class FinalFrame implements Frame {


    // the game
    private final Game game;
    //conductor
    private final Conductor conductor;
    // GUI component
    private final CFinalFrame cFinalFrame;
    // total of fallen pins during the frame
    private int pinsKnockedDown = 0;
    // extra counter
    private int extraPinsKnockedDown = 0;
    // current score of the frame
    private int score = 0;
    // type of the frame
    private FrameType type = FrameType.REGULAR;
    // index of the current roll
    private int currentRoll = 0;
    private int numberOfRollsToListen = numberOfRollsComposingTheFrame;


    /**
     * A frame of our African bowling. A frame is a component of a game, it have to subcribe to a conductor,
     * and have to be paired with a GUI component called cFinalFrame.
     *
     * @param _game        the game to which the frame below.
     * @param _conductor   the conductor for this frame.
     * @param _cFinalFrame the GUI controller paired with this frame.
     */
    public FinalFrame(Game _game, Conductor _conductor, CFinalFrame _cFinalFrame) {
        conductor = _conductor;
        //subscribe new frame to the conductor
        try {
            conductor.subscribe(this);
        } catch (NullPointerException e) {
            System.out.println("Warning : Conductor into FinalFrame is null, frame can not subscribe to conductor ! ");
        }
        cFinalFrame = _cFinalFrame;
        game = _game;

        try {
            cFinalFrame.activateInput(0);
        } catch (Exception e) {
            System.out.println("Warning : FinalFrame controller into BasicFrame is null ");
        }
    }

    @Override
    public void numberOfFallenPinsDuringCurrentRoll(int numberOfFallenPinsDuringCurrentRoll) {

        if (numberOfRollsToListen > 0) {
            try {
                //test if input does not exceed the max value allowed for frame.
                Utils.checkValue(numberOfFallenPinsDuringCurrentRoll, pinsKnockedDown, MAX_SUM);

                // update number of pins knocked down during frame
                pinsKnockedDown += numberOfFallenPinsDuringCurrentRoll;

                // update counter of roll to listen
                numberOfRollsToListen--;

                //activate next input given that this is the final frame, the condition is to activate input until frame
                // does not need to listen any next roll.
                activateInputOnGUI(numberOfRollsToListen > 0 ? currentRoll + 1 : -1);

                // update type of frame
                if (type == FrameType.REGULAR && pinsKnockedDown == 15) { //detect strike
                    if (currentRoll == 0) { // strike
                        setAsStrike(); // deactivates all input of the frame
                    } else {
                        setAsSpare(); // deactivates all input of the frame
                    }
                }


                // restores number of pins knocked down according to the management of specific behavior following strike and spare
                restoresPinsKnockedDown();

                //compute score
                updateScore();

                // process spare and strike due to specific behavior during final frame
                handleStrikeAndSpare(numberOfFallenPinsDuringCurrentRoll);

                //update current Roll
                currentRoll++;

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
     * restores the total number of pins knocked down during the frame.
     */
    private void restoresPinsKnockedDown() {
        pinsKnockedDown += extraPinsKnockedDown;
    }

    /**
     * Activate the frame for input
     *
     * @param indexOfInput index of input to activate
     */
    private void activateInputOnGUI(int indexOfInput) {
        if (cFinalFrame != null) {
            cFinalFrame.activateInput(indexOfInput);
        }
    }

    /**
     * According to the specific behavior of the final frame, this method allow us to handle this specific case.
     *
     * @param numberOfFallenPinsDuringCurrentRoll number of pins fallen during curring roll
     */
    private void handleStrikeAndSpare(int numberOfFallenPinsDuringCurrentRoll) {
        if (pinsKnockedDown - extraPinsKnockedDown == 15) {
            if (numberOfFallenPinsDuringCurrentRoll != 15) { //
                displaySpare(currentRoll);
            } else {
                displayStrike(currentRoll);
            }
            //if pins == 15 then we have to reset it to 0 to perform properly the Utils.checkValue
            extraPinsKnockedDown = pinsKnockedDown;
            pinsKnockedDown = 0;
        } else {
            pinsKnockedDown = pinsKnockedDown - extraPinsKnockedDown;
        }

    }

    /**
     * Set the frame as a spare.
     */
    private void setAsSpare() {
        type = FrameType.SPARE;
        numberOfRollsToListen = numberOfRollsToListenForASpare;
    }

    /**
     * Set the frame as a strike.
     */
    private void setAsStrike() {
        type = FrameType.STRIKE;
        numberOfRollsToListen = numberOfRollsToListenForAStrike;
    }


    /**
     * This method allow us to display a strile into the GUI
     *
     * @param currentRoll the roll that is a strike
     */
    private void displayStrike(int currentRoll) {
        if (cFinalFrame != null) {
            cFinalFrame.isAStrike(currentRoll);
            if (numberOfRollsToListen > 0) {
                activateInputOnGUI(currentRoll + 1);
            } else {
                cFinalFrame.disableAllInput();
            }
        }
    }

    /**
     * This method allow us to display a spare into the GUI
     *
     * @param currentRoll the roll that is a spare
     */
    private void displaySpare(int currentRoll) {
        if (cFinalFrame != null) {
            cFinalFrame.isASpare(currentRoll);
            if (numberOfRollsToListen > 0) {
                activateInputOnGUI(currentRoll + 1);
            } else {
                cFinalFrame.disableAllInput();
            }
        }
    }

    /**
     * check when to unsubscribe
     */
    private void updateSubscriptionStatus() {
        if (numberOfRollsToListen == 0) {
            conductor.unsubscribe(this);
        }
    }

    @Override
    public boolean isCompleted() {
        return numberOfRollsToListen == 0;// no rolls to listen (end of final) => end of game

    }


    /**
     * This method keep the score up to date after each throw.
     */
    private void updateScore() {
        int oldScore = score;
        score = pinsKnockedDown;
        if (game != null) {
            // keep global score up to date
            // add the delta after-before
            game.updateScore(score - oldScore);
        }
        updateDisplay();
    }


    /**
     * Uodate the GUI after computing the score.
     */
    private void updateDisplay() {
        if (cFinalFrame != null) {
            cFinalFrame.setScore(String.valueOf(score)); // cleaner than score+""
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

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return currentRoll
     */
    public int getCurrentRoll() {
        return currentRoll;
    }

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return numberOfRollsToListen
     */
    public int getNumberOfRollsToListen() {
        return numberOfRollsToListen;
    }

    /**
     * Getter for Test Purpose to avoid use of reflexion
     *
     * @return extraPinsKnockedDown
     */
    public int getExtraPinsKnockedDown() {
        return extraPinsKnockedDown;
    }

    @Override
    public String toString() {
        return "FinalFrame{" +
                " type=" + type +
                ", pinsKnockedDown=" + pinsKnockedDown +
                ", score=" + score +
                ", numberOfRollsToListen=" + numberOfRollsToListen +
                ", is completed ? " + isCompleted() +
                ", GUI component= " + (cFinalFrame != null ? cFinalFrame.getTitle() : "null ") + '}';
    }
}
