package part2;

public class RankSelectNaive implements RankSelectInterface {
    private int[] naiveArray;

    public RankSelectNaive(int[] inp) {
        this.naiveArray = inp.clone();
    }

    @Override
    public int rank(int i) {
        
        int counter = 0;
        for (int k = 0; k < i; k++) {
            if (naiveArray[k] == 1)
                counter++;
        }
        return counter;
    }

    @Override
    public int select(int r) {
        int counter = 0;
        for (int i = 0; i < naiveArray.length; i++) {
            counter += naiveArray[i];
            if (counter == r)
                return i + 1;
        }
        return Integer.MIN_VALUE;
    }

}
