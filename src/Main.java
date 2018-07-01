import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        String optional = args[0].toUpperCase();
        if(optional.equals("BETA")||optional.equals("-B")||optional.equals("B")||optional.equals("-BETA")) {
            System.out.println("TOTAL SCORE = "+ BetaGame.getScore(args));
        }else{
            System.out.println(PrettyGame.buildGame(Utils.checkAndPreProcessInput(Arrays.copyOfRange(args, 1, args.length))));
        }
    }
}
