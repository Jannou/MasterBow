import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestPrettyGame {


    @Test
    void TestBuildGame() {
        // 12 strike on a row => 300
        int[] game = {10,10,10,10,10,10,10,10,10,10,10,10};
        int score = 300;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // 13 rolls => exit(1)                                                                OK
        game = new int[]{10,10,10,10,10,10,10,10,10,10,10,10,1};
        //Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // empty game => score = 0
        game = new int[]{};
        score = 0;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // 12 rolls without spare or strike => exit(1)                                          OK
        game = new int[]{10,10,10,10,10,10,10,10,10,8,1,10};
        //score = 0;
        //Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // 9 strike + spare + strike
        game = new int[]{10,10,10,10,10,10,10,10,10,9,1,10};
        score = 279;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // 10 spare + strike
        game = new int[]{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,10};
        score = 155;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // random completed game
        game = new int[]{4,2,6,0,1,5,9,1,0,6,10,8,1,4,3,1,0,1,8};
        score = 79;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // random completed game
        game = new int[]{1,1,5,5,2,7,9,0,0,8,6,4,3,2,7,1,1,3,1,1};
        score = 72;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // random completed game
        game = new int[]{10,3,2,6,1,10,5,5,8,0,10,4,0,4,5,7,3,10};
        score = 120;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // random uncompleted game (completed frame)
        game = new int[]{1,8,10,1,5,8,1,7,3,2,7,3,7,7,0};
        score = 85;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());

        // random uncompleted game (uncompleted frame)
        game = new int[]{6,2,1,5,6,2,4,1,0,4,6};
        score = 37;
        Assertions.assertEquals(score, PrettyGame.buildGame(game).getTotalScore());
    }
}