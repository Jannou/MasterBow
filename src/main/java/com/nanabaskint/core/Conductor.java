package com.nanabaskint.core;

import com.nanabaskint.core.frames.Frame;

import java.util.ArrayList;

/**
 * This class is a conductor for the input and the frames.
 * It was design to forward the data to all the frames which are listeners.
 */
public class Conductor {


    private final ArrayList<Frame> framesToInform;
    private final int sizeOfStack;
    private ArrayList<Frame> framesToDelete;

    /**
     * Construct a new Conductor for the given size of stack
     * this is the number of frame that can simultaneously listen the number of pins knocked down during current roll.
     *
     * @param _sizeOfStack the max number of frame that can simultaneously listen the same input.
     */
    public Conductor(int _sizeOfStack) {
        sizeOfStack = _sizeOfStack;
        framesToInform = new ArrayList<>(4);
        framesToDelete = new ArrayList<>(4);
    }

    /**
     * This method is used when a frame need to subscribe to the conductor.
     *
     * @param frame the frame which want to be informed by the conductor.
     */
    public void subscribe(Frame frame) {
        framesToInform.add(frame);
    }

    /**
     * This method is used when a frame want to unsubscribe from the conductor
     *
     * @param frame the frame which want to unsubscibe from the conductor.
     */
    public void unsubscribe(Frame frame) {
        framesToDelete.add(frame);
    }

    /**
     * this method is used to bypass the concurrence problem.
     */
    public void performUnsubscription() {
        framesToInform.removeAll(framesToDelete);
        framesToDelete = new ArrayList<>(4);
    }

    /**
     * This method is designed to inform all the subscribed frame.
     * In that way, each Frame can compute her score herself.
     *
     * @param input the data to forward.
     */
    public void informAll(int input) {
        for (Frame frame : framesToInform) {
            frame.numberOfFallenPinsDuringCurrentRoll(input);
        }
        performUnsubscription();
    }

    /**
     * This method allow us to clear the stack of frame that are currently listen the input .
     */
    public void clear() {
        framesToInform.clear();
        framesToDelete.clear();
    }

    /**
     * Basic getter, test purpose to avoid reflextion.
     *
     * @return the current frameToInform ArrayList.
     */
    public ArrayList<Frame> getFramesToInform() {
        return framesToInform;
    }


}
