package part2;

public class RankSelectLookup implements RankSelectInterface {
    private int[] LookUpArray;

    public RankSelectLookup(int[] inp) {
        this.LookUpArray = new int[inp.length];
        precompute(inp);

    }

    public void precompute(int[] inp) {
        int counter = 0;
        for (int i = 0; i < inp.length; i++) {
            counter += inp[i];
            LookUpArray[i] = counter;
        }
    }

    @Override
    public int rank(int i) {
        if(i < 1) return 0;
        return LookUpArray[i - 1];
    }

    @Override
    public int select(int r) {
        int mins = 0;
        int max = LookUpArray.length - 1;

        while (mins <= max) {
            int m = (mins + max) / 2;
            if (rank(m) < r) {
                mins = m + 1;
            } else {
                max = m - 1;
            }
        }

        if (rank(mins) != r) {
            return Integer.MIN_VALUE;
        }
        return mins;
    }

}