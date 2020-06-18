package com.nanabaskint.core;

/**
 * This Class provide a useful function to check the given input
 */
public class Utils {
    private static final int MIN = 0;
    private static final int MAX = 15;

    /**
     * error msg
     */
    private static final String INPUT_HELP = "Inputs have to be an integer between " + MIN + " and " + MAX + "\n";

    /**
     * This method return an integer on basis of a string
     * iff the string represents
     * an integer >-1 and <11 otherwise the method returns null.
     *
     * @param input a String representing a integer
     * @return an integer valuated the string.
     */
    public static int checkAndConvert(String input) throws WrongInputFormat {
        return preProcess(input);
    }


    /**
     * This method check the input
     * iff strings represents integers >-1 and <15 otherwise the method returns null.
     *
     * @param arg String representing a integer.
     */
    private static int preProcess(String arg) throws WrongInputFormat {
        int tmp;
        try {
            tmp = Integer.valueOf(arg);
            if (tmp < MIN || tmp > MAX)
                throw new PinOutOfBound(tmp, MAX);
        } catch (Exception e) {
            throw new WrongInputFormat(arg);

        }
        return tmp;
    }


    public static int checkValue(int input, int pinsKnockedDown, int MAX) throws PinOutOfBound {

        if (MAX - pinsKnockedDown < input) {
            throw new PinOutOfBound(input, MAX - pinsKnockedDown);
        } else {
            return 0;
        }
    }


    ////////////////////////////////////////////////////////////////////


    /**
     * Custom exception to catch input out of [0-10]
     */
    public static class PinOutOfBound extends Exception {
        PinOutOfBound(int value, int max) {
            super("Input can not be greater than " + max + " for this roll.\nGiven input : " + value);
        }
    }

    /**
     * Custom exception to catch input out of [0-10]
     */
    public static class WrongInputFormat extends Exception {
        WrongInputFormat(String value) {
            super(INPUT_HELP + "Given input : " + value);
        }
    }
    /**
     * error msg
     */

    /**
     * This method return an array of integers on basis of an array of strings
     * iff the array of strings represents
     * only integers >-1 and <11 otherwise the method returns null.
     * @param args the array of Strings.
     * @return an array of integers or an exception.
     */
  /*  public static int[] checkAndPreProcessInput(String[] args){
        int[] result = new int[args.length];
        for(int index = 0 ; index<args.length;index++){
            result[index]= preProcess(args[index]);
        }
        return result;
    }
*/
    /*
     */
/**
 * This method check the input
 * iff the array of strings represents
 * only integers >-1 and <11 otherwise the method returns null.
 * @param arg String representing a integer.
 *//*

    static int preProcess(String arg) {
        int tmp=-5;
        try{
            tmp= Integer.valueOf(arg);
            if(tmp<0||tmp>10)
                throw new PinOutOfBound(tmp);
        } catch (Exception e){
            System.out.println(e+"\n"+ INPUT_HELP);
            System.exit(1);
        }
        return tmp;
    }
*/
}
