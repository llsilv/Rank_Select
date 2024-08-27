

package part2;

import java.util.Random;
import java.io.FileWriter;
import java.util.function.IntToDoubleFunction;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class part2exp {

    private static RankSelectNaive rsN;
    private static RankSelectLookup rsL;
    private static RankSelectSpaceEfficient rsSE1;
    private static RankSelectSpaceEfficient rsSE2;
    private static RankSelectSpaceEfficient rsSE5;

    public static void main(String[] args) {
        //buildTestData(); // this is used to create the testData for the tests

        String filePathRank = "app/src/main/java/expResultsRANK.txt";
        String filePathSelect = "app/src/main/java/expResultsSELECT.txt";

        int jMax = 268435456; // 2^28

        // for rank:
        try {
            FileOutputStream fOutS = new FileOutputStream(filePathRank);
            PrintStream pS = new PrintStream(fOutS);
            System.setOut(pS);

            for (int j = 64; j < jMax; j *= 2) {
                final int finalJ = j;
                int[] inpArray = new int[j];
                for (int k = 0; k < j - 1; k++) {
                    if (k % 2 == 0)
                        inpArray[k] = 1;
                }

                rsN = new RankSelectNaive(inpArray);
                rsL = new RankSelectLookup(inpArray);
                rsSE1 = new RankSelectSpaceEfficient(inpArray, 1);
                rsSE2 = new RankSelectSpaceEfficient(inpArray, 8);
                rsSE5 = new RankSelectSpaceEfficient(inpArray, 16);

                Mark7("rsN " + " n = " + j, i -> {
                    rsN.rank(finalJ - 1);
                    return 0;
                });
                Mark7("rsL " + " n = " + j, i -> {
                    rsL.rank(finalJ - 1);
                    return 0;
                });
                Mark7("rsSE1 " + " n = " + j, i -> {
                    rsSE1.rank(finalJ - 1);
                    return 0;
                });
                Mark7("rsSE8 " + " n = " + j, i -> {
                    rsSE2.rank(finalJ - 1);
                    return 0;
                });
                Mark7("rsSE16 " + " n = " + j, i -> {
                    rsSE5.rank(finalJ - 1);
                    return 0;
                });
            }

        } catch (

        Exception e) {
            e.printStackTrace();
        }
        // for select:
        try {
            FileOutputStream fOutS = new FileOutputStream(filePathSelect);
            PrintStream pS = new PrintStream(fOutS);
            System.setOut(pS);

            for (int j = 64; j < jMax; j *= 2) {
                final int finalJ = j;
                int[] inpArray = new int[j];
                for (int k = 0; k < j - 1; k++) {
                    if (k % 2 == 0)
                        inpArray[k] = 1;
                }

                rsN = new RankSelectNaive(inpArray);
                rsL = new RankSelectLookup(inpArray);
                rsSE1 = new RankSelectSpaceEfficient(inpArray, 1);
                rsSE2 = new RankSelectSpaceEfficient(inpArray, 8);
                rsSE5 = new RankSelectSpaceEfficient(inpArray, 16);

                Mark7("rsN " + " n = " + j, i -> {
                    rsN.select(finalJ - 8);
                    return 0;
                });
                Mark7("rsL " + " n = " + j, i -> {
                    rsL.select(finalJ - 8);
                    return 0;
                });
                Mark7("rsSE1 " + " n = " + j, i -> {
                    rsSE1.select(finalJ - 8);
                    return 0;
                });
                Mark7("rsSE8 " + " n = " + j, i -> {
                    rsSE2.select(finalJ - 8);
                    return 0;
                });
                Mark7("rsSE16 " + " n = " + j, i -> {
                    rsSE5.select(finalJ - 8);
                    return 0;
                });
            }

        } catch (

        Exception e) {
            e.printStackTrace();
        }

    }

    public static void buildTestData() {
        String filePath = "app/src/main/java/testData.Text";
        Random rng = new Random(42);
        int n = 10000;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.valueOf(n));
            writer.write(System.lineSeparator());

            for (int i = 0; i < n; i++) {
                writer.write(String.valueOf(rng.nextInt()));
                writer.write(System.lineSeparator());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Code made by Peter Sestoft for the PPCP course

    public static double Mark7(String msg, IntToDoubleFunction f) {
        int n = 10, count = 1, totalCount = 0;
        double dummy = 0.0, runningTime = 0.0, st = 0.0, sst = 0.0;
        do {
            count *= 2;
            st = sst = 0.0;
            for (int j = 0; j < n; j++) {
                Timer t = new Timer();
                for (int i = 0; i < count; i++)
                    dummy += f.applyAsDouble(i);
                runningTime = t.check();
                double time = runningTime * 1e9 / count;
                st += time;
                sst += time * time;
                totalCount += count;
            }
        } while (runningTime < 0.1 && count < Integer.MAX_VALUE / 2);// changed this
        double mean = st / n, sdev = Math.sqrt((sst - mean * mean * n) / (n - 1));
        System.out.printf("%-25s %15.1f %10.2f %10d%n", msg, mean, sdev, count);// remved ns
        return dummy / totalCount;
    }

}

// part of Peter Sestoft Banchmarking code
class Timer {
    private long start, spent = 0;

    public Timer() {
        play();
    }

    public double check() {
        return (System.nanoTime() - start + spent) / 1e9;
    }

    public void pause() {
        spent += System.nanoTime() - start;
    }

    public void play() {
        start = System.nanoTime();
    }
}

