package com.nanabaskint.core.frames;

import com.nanabaskint.core.Conductor;
import com.nanabaskint.core.Game;
import com.nanabaskint.core.Utils;
import com.nanabaskint.gui.controllers.CBasicFrame;

/**
 * basic frame is composed by 3 rolls. totals pins = 15.
 */
public class BasicFrame extends Frame {

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
        setConductor(_conductor);

        try {
            getConductor().subscribe(this);
        } catch (NullPointerException e) {
            System.out.println("Warning : Conductor into BasicFrame is null, frame can not subscribe to conductor ! ");
        }

        setcFrame(_cBasicFrame);
        setGame(_game);


        try {
            getcFrame().activateInput(0);
        } catch (Exception e) {
            System.out.println("Warning : BasicFrame controller into BasicFrame is null ");
        }
    }

    @Override
    public void numberOfFallenPinsDuringCurrentRoll(int numberOfFallenPinsDuringCurrentRoll) {

        if (getNumberOfRollsToListen() > 0) {
            try {
                //test if input does not exceed the max value allowed for frame.
                // but we don't have to perform this check if the frame is a spare or a strike
                // because the next frame already do it.
                if (getType() == FrameType.REGULAR) {
                    Utils.checkValue(numberOfFallenPinsDuringCurrentRoll, getPinsKnockedDown(), MAX_SUM);
                }

                // update number of pins knocked down
                setPinsKnockedDown(getPinsKnockedDown() + numberOfFallenPinsDuringCurrentRoll);

                // update counter of roll to listen
                setNumberOfRollsToListen(getNumberOfRollsToListen() - 1);

                // does not need to listen any next roll.
                activateInputOnGUI(getNumberOfRollsToListen() > 0 ? getCurrentRoll() + 1 : -1);

                             // update type of frame
                if (getType() == FrameType.REGULAR && getPinsKnockedDown() == 15) { //detect strike
                    if (getCurrentRoll() == 0) { // strike
                        setAsStrike(); // deactivates all input of the frame
                    } else {
                        setAsSpare(); // deactivates all input of the frame
                    }
                    //reset this counter to keep up to date the number of pins knocked down during next rolls.
                    setPinsKnockedDown(MAX_SUM);
                }

                //update current Roll
                setCurrentRoll(getCurrentRoll() + 1);

                //compute score
                updateScore();

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


    @Override
    public boolean isCompleted() {
        return super.isCompleted() // no rolls to listen (end of regular) => new frame needed
                || getType() != FrameType.REGULAR;// if not regular then it's a spare or a strike
        // whatever, frame is completed => new frame needed
        // due to probability and lazy eval, it's better to test in this order (for non profesional player)
    }

}
