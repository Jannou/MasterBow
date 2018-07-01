import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        args = new String[]{"b","10","10","10","10","10","10","10","10","10","10","10","10"};
        //args = new String[]{"b","1","8","10","1","5","8","1","7","3","2","7","3","7","7","0"};

        String optional = args[0].toUpperCase();
        if(optional.equals("BETA")||optional.equals("-B")||optional.equals("B")||optional.equals("-BETA")) {
            System.out.println("TOTAL SCORE = "+ BetaGame.getScore(Arrays.copyOfRange(args, 1, args.length)));
            //BetaGame.showFrame();
        }else{
            System.out.println(PrettyGame.buildGame(Utils.checkAndPreProcessInput(args)));
        }
    }
}
