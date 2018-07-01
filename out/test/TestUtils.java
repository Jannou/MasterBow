import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUtils {
    private String[] sample1 = {"r"};
    private String[] sample2 = {"15"};
    private String[] sample3 = {"1",""};
    private String[] sample4 = {"4","2","6","0","1","5","9","1","0","6","10","8","1","4","3","1","0","1","8"};



    @Test
    void TestcheckAndPreProcessInput() {
//        Assertions.assertTrue(Utils.checkAndPreProcessInput(sample1)==null); // => system.Exit(1) ok
//        Assertions.assertTrue(Utils.checkAndPreProcessInput(sample2)==null); // => system.Exit(1) ok
//        Assertions.assertTrue(Utils.checkAndPreProcessInput(sample3)==null); // => system.Exit(1) ok
        int[] sample4Process = Utils.checkAndPreProcessInput(sample4);
        int[] exceptedValue = new int[]{4, 2, 6, 0, 1, 5, 9, 1, 0, 6, 10, 8, 1, 4, 3, 1, 0, 1, 8};
        assertEquals(exceptedValue.length,sample4Process.length );
        for(int i = 0;i<exceptedValue.length;i++){
            Assertions.assertEquals(exceptedValue[i],sample4Process[i]);
        }
    }
}