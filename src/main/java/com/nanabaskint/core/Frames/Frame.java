package com.nanabaskint.core.Frames;

/**
 * A frame.
 */
public interface Frame {

    //RULES
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


    /**
     * This method is design to inform the frame how many pins fallen during the current roll.
     *
     * @param numberOfFallenPinsDuringCurrentRoll the number of fallen pins during the current roll
     * @return 0 if OK 1 otherwise
     */
    void numberOfFallenPinsDuringCurrentRoll(int numberOfFallenPinsDuringCurrentRoll);


    /**
     * this methode is used to check if a frame is completed or not.
     *
     * @return true if the frame is completed, false otherwise.
     */
    boolean isCompleted();
}
