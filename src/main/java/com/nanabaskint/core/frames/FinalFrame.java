package com.nanabaskint.core.frames;


import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.core.Utils;
import com.nanabaskint.gui.controllers.CFrame;

/**
 * The final frame of a game. it might be composed by 3,4 or 5 rolls.
 */
public class FinalFrame extends Frame {

    public int getExtraPinsKnockedDown() {
        return extraPinsKnockedDown;
    }


    /**
     * A frame of our African bowling. A frame is a component of a game, it have to subcribe to a conductor,
     * and have to be paired with a GUI component called cFinalFrame.
     *
     * @param _game        the game to which the frame below.
     * @param _conductor   the conductor for this frame.
     * @param _cFinalFrame the GUI controller paired with this frame.
     */
    public FinalFrame(Game _game, Conductor _conductor, CFrame _cFinalFrame) {
        setConductor(_conductor);
        //subscribe new frame to the conductor
        try {
            getConductor().subscribe(this);
        } catch (NullPointerException e) {
            System.out.println("Warning : Conductor into FinalFrame is null, frame can not subscribe to conductor ! ");
        }
        setcFrame(_cFinalFrame);
        setGame(_game);

        try {
            getcFrame().activateInput(0);
        } catch (Exception e) {
            System.out.println("Warning : FinalFrame controller into FinalFrame is null ");
        }
    }

    @Override
    public void numberOfFallenPinsDuringCurrentRoll(int numberOfFallenPinsDuringCurrentRoll) {

        if (getNumberOfRollsToListen() > 0) {
            try {
                //test if input does not exceed the max value allowed for frame.
                Utils.checkValue(numberOfFallenPinsDuringCurrentRoll, getPinsKnockedDown(), MAX_SUM);

                // update number of pins knocked down during frame
                setPinsKnockedDown(getPinsKnockedDown() + numberOfFallenPinsDuringCurrentRoll);

                // update counter of roll to listen
                setNumberOfRollsToListen(getNumberOfRollsToListen() - 1);

                //activate next input given that this is the final frame, the condition is to activate input until frame
                // does not need to listen any next roll.
                activateInputOnGUI(getNumberOfRollsToListen() > 0 ? getCurrentRoll() + 1 : -1);

                // update type of frame
                if (getType() == FrameType.REGULAR && getPinsKnockedDown() == 15) { //detect strike
                    if (getCurrentRoll() == 0) { // strike
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
                setCurrentRoll(getCurrentRoll() + 1);

                // update conductor subscription
                updateSubscriptionStatus();

            } catch (Utils.PinOutOfBound pinOutOfBound) {
                if (getGame() != null) {
                    getGame().alert(pinOutOfBound.getMessage());
                    activateInputOnGUI(getCurrentRoll());
                }
            }
        }
    }

    /**
     * restores the total number of pins knocked down during the frame.
     */
    private void restoresPinsKnockedDown() {
        setPinsKnockedDown(getPinsKnockedDown() + extraPinsKnockedDown);
    }


    /**
     * According to the specific behavior of the final frame, this method allow us to handle this specific case.
     *
     * @param numberOfFallenPinsDuringCurrentRoll number of pins fallen during curring roll
     */
    private void handleStrikeAndSpare(int numberOfFallenPinsDuringCurrentRoll) {
        if (getPinsKnockedDown() - extraPinsKnockedDown == 15) {
            if (numberOfFallenPinsDuringCurrentRoll != 15) {
                displaySpare(getCurrentRoll());
            } else {
                displayStrike(getCurrentRoll());
            }
            //if pins == 15 then we have to reset it to 0 to perform properly the Utils.checkValue
            extraPinsKnockedDown = getPinsKnockedDown();
            setPinsKnockedDown(0);
        } else {
            setPinsKnockedDown(getPinsKnockedDown() - extraPinsKnockedDown);
        }

    }


    /**
     * This method allow us to display a strike into the GUI
     *
     * @param currentRoll the roll that is a strike
     */
    private void displayStrike(int currentRoll) {
        if (getcFrame() != null) {
            getcFrame().isAStrike(currentRoll);
            if (getNumberOfRollsToListen() > 0) {
                activateInputOnGUI(currentRoll + 1);
            } else {
                getcFrame().disableAllInput();
            }
        }
    }

    /**
     * This method allow us to display a spare into the GUI
     *
     * @param currentRoll the roll that is a spare
     */
    private void displaySpare(int currentRoll) {
        if (getcFrame() != null) {
            getcFrame().isASpare(currentRoll);
            if (getNumberOfRollsToListen() > 0) {
                activateInputOnGUI(currentRoll + 1);
            } else {
                getcFrame().disableAllInput();
            }
        }
    }


    // testPurpose
    private int extraPinsKnockedDown = 0;
}
