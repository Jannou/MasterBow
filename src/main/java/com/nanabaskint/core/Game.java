package com.nanabaskint.core;


import com.nanabaskint.core.frames.BasicFrame;
import com.nanabaskint.core.frames.FinalFrame;
import com.nanabaskint.core.frames.Frame;
import com.nanabaskint.gui.controllers.CBoard;

public class Game {


    /*Size of the conductor stack (based on the worst case : 4 consecutive strike, first strike have to listen the 3 next roll
     that can all be strikes. )*/
    private final int sizeOfConductorsStack = 4;


    // the conductor between the game and the frame.
    private final Conductor conductor;
    //current cboard
    private final CBoard cBoard;
    // number of frames
    private final int numberOfFrames;
    //all the frames composing a game.
    private Frame[] frames;
    //current frame
    private int currentFrame = 0;
    // current score of the game.
    private int score = 0;


    /**
     * Launch a new game.
     *
     * @param _numberOfFrames the number of frames which compose the game.
     */
    public Game(int _numberOfFrames, CBoard _cBoard) {
        if (_numberOfFrames < 1)
            throw new IndexOutOfBoundsException("\n\n\t\t\tNUMBER OF FRAMES HAVE TO BE GREATER THAN 0.\n");
        numberOfFrames = _numberOfFrames;
        conductor = new Conductor(sizeOfConductorsStack);
        cBoard = _cBoard;
    }

    /**
     * init game to zero.
     */
    public void init() {
        // empty the conductor
        conductor.clear();
        score = 0;

        try {
            cBoard.setScore(score);
        } catch (Exception e) {
            System.out.println("board controller into game is null, no GUI paired");
        }

        frames = new Frame[numberOfFrames];
        currentFrame = 0;
        frames[0] = new BasicFrame(this, conductor, cBoard != null ? cBoard.getCBasicFrame(currentFrame) : null);
    }


    /**
     * give the index of the current frame.
     *
     * @return the index of the current frame.
     */
    private int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * check if current frame is completed or not and create new frames when needed.
     * manage basic and final frame
     */
    private void framesManager() {

        if (frames[getCurrentFrame()].isCompleted()) {
            currentFrame++;
            // create basic or final frame according to the number of frames which compose the current game
            if (currentFrame < numberOfFrames) {
                frames[currentFrame] = currentFrame == frames.length - 1 ?
                        new FinalFrame(this, conductor, cBoard != null ? cBoard.getCFinalFrame() : null) :
                        new BasicFrame(this, conductor, cBoard != null ? cBoard.getCBasicFrame(currentFrame) : null);
            }

        }

    }

    /**
     * donne le résultat du lancer pour ce joueur
     *
     * @param quilles donne le nombre de quilles abattues par ce lancer
     */
    public void lancer(int quilles) {
        // conductor inform all the registered frames.
        conductor.informAll(quilles);
        framesManager();
    }

    /**
     * get the number of frames composing the game.
     *
     * @return the number of frames that compose the game
     */
    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    /**
     * Update the global score
     *
     * @param delta the delta to add to the current global score
     */
    public void updateScore(int delta) {
        score += delta;
        if (cBoard != null) {
            cBoard.setScore(score);
        }
    }

    /**
     * create an alert
     *
     * @param message the message of the alert
     */
    public void alert(String message) {
        if (cBoard != null) {
            cBoard.alerte(message);
        }
    }

    /**
     * Getter for Test Purpose to avail use of reflexion
     *
     * @return game's conductor
     */
    public Conductor getConductor() {
        return conductor;
    }

    /**
     * Getter for Test Purpose to avail use of reflexion
     *
     * @return game's frames
     */
    public Frame[] getFrames() {
        return frames;
    }

    /**
     * Getter for Test Purpose to avail use of reflexion
     *
     * @return game's controller
     */
    public CBoard getcBoard() {
        return cBoard;
    }

    /**
     * Getter for Test Purpose to avail use of reflexion
     *
     * @return game's controller
     */
    public int getScore() {
        return score;
    }
}
