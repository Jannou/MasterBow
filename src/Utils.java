/**
 * This Class provide a useful function to check the given input
 *
 */
class Utils {

    /**
     * error msg
     */
    private static final String INPUT_HELP = "Inputs have to be integers strictly greater than -1 and strictly smaller than 11";

    /**
     * This method return an array of integers on basis of an array of strings
     * iff the array of strings represents
     * only integers >-1 and <11 otherwise the method returns null.
     * @param args the array of Strings.
     * @return an array of integers or an exception.
     */
    static Integer[] checkAndPreProcessInput(String[] args){
        Integer[] result = new Integer[args.length];
        for(int index = 0 ; index<args.length;index++){
            preProcess(args[index], result, index);
        }
        return result;
    }

    /**
     * This method check the input
     * iff the array of strings represents
     * only integers >-1 and <11 otherwise the method returns null.
     * @param arg String representing a integer.
     * @param result array to store the integer corresponding to the String.
     * @param index index of the String into the array of Strings.
     */
    private static void preProcess(String arg, Integer[] result, int index) {
        try{
            int tmp= Integer.valueOf(arg);
            if(-1<tmp && tmp<11){
                result[index]= tmp;
            }else{
                throw new PinOutOfBound(tmp);
            }
        } catch (Exception e){
            System.out.println(e+"\n"+ INPUT_HELP);
            System.exit(1);
        }
    }


    ////////////////////////////////////////////////////////////////////





    /**
     * Custom exception to catch input out of [0-10]
     */
    public static class PinOutOfBound extends Exception{
        PinOutOfBound(int value){
            super(" For input : "+value);
        }
    }
}
