package com.nanabaskint.core.frames;

import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.gui.controllers.CFrame;

/**
 * A frame.
 */
public abstract class Frame {


    //RULES must be extract to an configuration component
    /**
     * Number of rolls composing the frame.
     */
    int numberOfRollsComposingTheFrame = 3;
    /**
     * Number of rolls to listen after a spare in order to compute the score of the spare.
     */
    int numberOfRollsToListenForASpare = 2;
    /**
     * Number of rolls to listen after a strike in order to compute the score of the strike.
     */
    int numberOfRollsToListenForAStrike = 3;
    /**
     * Number of pins
     */
    int MAX_SUM = 15;


    // GUI component
    private CFrame cFrame;
    // the game
    private Game game;
    //conductor
    private Conductor conductor;
    // total of fallen pins during the frame
    private int pinsKnockedDown = 0;
    // current score of the frame
    private int score = 0;
    // type of the frame
    private FrameType type = FrameType.REGULAR;
    // index of the current roll
    private int currentRoll = 0;
    private int numberOfRollsToListen = numberOfRollsComposingTheFrame;


    /**
     * Set the frame as a spare.
     */
    protected void setAsSpare() {
        setType(FrameType.SPARE);
        if (getcFrame() != null) {
            getcFrame().isASpare(getCurrentRoll());
        }
        setNumberOfRollsToListen(numberOfRollsToListenForASpare);
    }

    /**
     * Set the frame as a strike.
     */
    protected void setAsStrike() {
        setType(FrameType.STRIKE);
        if (getcFrame() != null) {
            getcFrame().isAStrike(getCurrentRoll());
        }
        setNumberOfRollsToListen(numberOfRollsToListenForAStrike);
    }

    /**
     * This method keep the score up to date after each throw.
     */
    protected void updateScore() {
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
     * Activate the frame for input
     *
     * @param indexOfInput index of input to activate
     */
    protected void activateInputOnGUI(int indexOfInput) {
        if (getcFrame() != null) {
            getcFrame().activateInput(indexOfInput);
        }
    }

    /**
     * Update the GUI after computing the score.
     */
    private void updateDisplay() {

        try {
            cFrame.setScore(String.valueOf(score)); // cleaner than score+""
        } catch (Exception e) {
            System.out.println("Warning : There is no GUI to pair with " + this);
        }

    }

    /**
     * This method is design to inform the frame how many pins fallen during the current roll.
     *
     * @param numberOfFallenPinsDuringCurrentRoll the number of fallen pins during the current roll
     * @return 0 if OK 1 otherwise
     */
    public abstract void numberOfFallenPinsDuringCurrentRoll(int numberOfFallenPinsDuringCurrentRoll);


    public boolean isCompleted() {
        return getNumberOfRollsToListen() == 0;// no rolls to listen (end of final) => end of game
    }

    /**
     * check when to unsubscribe
     */
    protected void updateSubscriptionStatus() {
        if (getNumberOfRollsToListen() == 0 && getConductor() != null) {
            getConductor().unsubscribe(this);
        }
    }

    @Override
    public String toString() {
        return "FinalFrame{" +
                " type=" + getType() +
                ", pinsKnockedDown=" + getPinsKnockedDown() +
                ", score=" + getScore() +
                ", numberOfRollsToListen=" + getNumberOfRollsToListen() +
                ", is completed ? " + isCompleted() +
                ", GUI component= " + (getcFrame() != null ? getcFrame().getTitle() : "null ") + '}';
    }


    public int getNumberOfRollsComposingTheFrame() {
        return numberOfRollsComposingTheFrame;
    }

    public void setNumberOfRollsComposingTheFrame(int numberOfRollsComposingTheFrame) {
        this.numberOfRollsComposingTheFrame = numberOfRollsComposingTheFrame;
    }

    public int getNumberOfRollsToListenForASpare() {
        return numberOfRollsToListenForASpare;
    }

    public void setNumberOfRollsToListenForASpare(int numberOfRollsToListenForASpare) {
        this.numberOfRollsToListenForASpare = numberOfRollsToListenForASpare;
    }

    public int getNumberOfRollsToListenForAStrike() {
        return numberOfRollsToListenForAStrike;
    }

    public void setNumberOfRollsToListenForAStrike(int numberOfRollsToListenForAStrike) {
        this.numberOfRollsToListenForAStrike = numberOfRollsToListenForAStrike;
    }

    public int getMAX_SUM() {
        return MAX_SUM;
    }

    public void setMAX_SUM(int MAX_SUM) {
        this.MAX_SUM = MAX_SUM;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public int getPinsKnockedDown() {
        return pinsKnockedDown;
    }

    public void setPinsKnockedDown(int pinsKnockedDown) {
        this.pinsKnockedDown = pinsKnockedDown;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public FrameType getType() {
        return type;
    }

    public void setType(FrameType type) {
        this.type = type;
    }

    public int getCurrentRoll() {
        return currentRoll;
    }

    public void setCurrentRoll(int currentRoll) {
        this.currentRoll = currentRoll;
    }

    public int getNumberOfRollsToListen() {
        return numberOfRollsToListen;
    }

    public void setNumberOfRollsToListen(int numberOfRollsToListen) {
        this.numberOfRollsToListen = numberOfRollsToListen;
    }

    public CFrame getcFrame() {
        return cFrame;
    }

    public void setcFrame(CFrame cFrame) {
        this.cFrame = cFrame;
    }
}
