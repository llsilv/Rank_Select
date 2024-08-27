package part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RSTests {
    private static RankSelectNaive rsN;
    private static RankSelectLookup rsL;
    private static RankSelectSpaceEfficient rsSE1;
    private static RankSelectSpaceEfficient rsSE2;
    private static RankSelectSpaceEfficient rsSE5;

    @Test
    public void TestSelectNoHits() {
        int[] intArray = new int[64];
        for (int i = 0; i < intArray.length - 1; i++) {
            intArray[i] = 0;
        }

        rsN = new RankSelectNaive(intArray);
        rsL = new RankSelectLookup(intArray);
        rsSE1 = new RankSelectSpaceEfficient(intArray, 1);
        rsSE2 = new RankSelectSpaceEfficient(intArray, 2);
        rsSE5 = new RankSelectSpaceEfficient(intArray, 5);

        assertEquals(Integer.MIN_VALUE, rsN.select(5));
        assertEquals(Integer.MIN_VALUE, rsL.select(5));
        assertEquals(Integer.MIN_VALUE, rsSE1.select(5));
        assertEquals(Integer.MIN_VALUE, rsSE2.select(5));
        assertEquals(Integer.MIN_VALUE, rsSE5.select(5));
    }

    @Test
    public void TestSelectLargeArray() {
        int[] intArray = new int[1048576];
        intArray[200000] = 1;

        rsN = new RankSelectNaive(intArray);
        rsL = new RankSelectLookup(intArray);
        rsSE1 = new RankSelectSpaceEfficient(intArray, 1);
        rsSE2 = new RankSelectSpaceEfficient(intArray, 2);
        rsSE5 = new RankSelectSpaceEfficient(intArray, 5);

        assertEquals(200001, rsN.select(1));
        assertEquals(200001, rsL.select(1));
        assertEquals(200001, rsSE1.select(1));
        assertEquals(200001, rsSE2.select(1));
        assertEquals(200001, rsSE5.select(1));
    }

    @Test
    public void TestSelectParaIsLargerThanLastOneBit() {
        int[] intArray = new int[64];
        intArray[5] = 1;

        rsN = new RankSelectNaive(intArray);
        rsL = new RankSelectLookup(intArray);
        rsSE1 = new RankSelectSpaceEfficient(intArray, 1);
        rsSE2 = new RankSelectSpaceEfficient(intArray, 2);
        rsSE5 = new RankSelectSpaceEfficient(intArray, 5);

        assertEquals(6, rsN.select(1));
        assertEquals(6, rsL.select(1));
        assertEquals(6, rsSE1.select(1));
        assertEquals(6, rsSE2.select(1));
        assertEquals(6, rsSE5.select(1));
    }

    @Test
    public void TestKattisLikeForRankAndSelect() {

        String filePath = "C:/Users/Luis/Desktop/ITU/3rdSemester/ApAl/Final Assignment/ApAl-final-assignment/part2/app/src/main/java/testData.Text";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lines = Integer.parseInt(reader.readLine());
            ArrayList<Integer> helperList = new ArrayList<>(); 

            for (int i = 0; i < lines; i++) {
                String line = reader.readLine();
                int value = Integer.parseInt(line);

                for (int j = 31; j >= 0; j--) {
                    helperList.add((value & (1 << j)) != 0 ? 1 : 0);
                }

                int[] intArray = arrayListToIntArray(helperList);
                if (i > 1) {
                RankSelectInterface rsN = new RankSelectNaive(intArray);
                RankSelectLookup rsL = new RankSelectLookup(intArray);
                RankSelectSpaceEfficient rsSE1 = new RankSelectSpaceEfficient(intArray, 1);
                RankSelectSpaceEfficient rsSE2 = new RankSelectSpaceEfficient(intArray, 2);
                RankSelectSpaceEfficient rsSE5 = new RankSelectSpaceEfficient(intArray, 5);
                Random rng = new Random(42);
                int key1 = rng.nextInt(intArray.length - 23) + 1;
                int key2 = rsN.rank(intArray.length - 4);
                int answer1 = rsN.rank(key1);
                int answer2 = rsN.select(key2);

                assertEquals(answer1, rsL.rank(key1));
                assertEquals(answer1, rsSE1.rank(key1));
                assertEquals(answer1, rsSE2.rank(key1));
                assertEquals(answer1, rsSE5.rank(key1));

                assertEquals(answer2, rsL.select(key2));
                assertEquals(answer2, rsSE1.select(key2));
                assertEquals(answer2, rsSE2.select(key2));
                assertEquals(answer2, rsSE5.select(key2));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] arrayListToIntArray(ArrayList<Integer> arrayList) {
        int[] arr = new int[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            arr[i] = arrayList.get(i);
        }

        return arr;
    }

}
