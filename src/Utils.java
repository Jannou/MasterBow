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
    static Integer[] preProc(String[] args){
        Integer[] result = new Integer[args.length];
        try{
            for(int index = 0 ; index<args.length;index++){
                int tmp= Integer.valueOf(args[index]);
                if(-1<tmp && tmp<11){
                    result[index]= tmp;
                }else{
                    throw new PinOutOfBound(tmp);
                }
            }
        } catch (Exception e){
            System.out.println(e+"\n"+ INPUT_HELP);
            System.exit(1);
        }
        return result;
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
