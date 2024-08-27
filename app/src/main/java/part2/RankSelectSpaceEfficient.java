package part2;

public class RankSelectSpaceEfficient implements RankSelectInterface {
    private int[] bitVector;
    private int[] Rs;
    private int k;

    public RankSelectSpaceEfficient(int[] inp, int k) {
        this.k = k;
        this.bitVector = computeBitV(inp);
        this.Rs = computeRs(k, inp);

    }

    public int[] computeBitV(int[] inp) {
        int[] bV = new int[(int) Math.ceil((double) inp.length / 32)];

        for (int i = 0; i < inp.length; i++) {
            int setBit = 31 - (i % 32);
            bV[i / 32] |= (inp[i] & 1) << setBit;
        }
        return bV;
    }

    public int[] computeRs(int k, int[] inp) {
        int n = bitVector.length;
        int s = 32 * k;
        int blockNumber = (int) Math.ceil((double) n / s);
        int[] RsTmp = new int[blockNumber];
        int blockCounter = -1;
        int counterbitCount = 0;

        for (int i = 0; i < n; i++) {
            if (i % s == 0)
                blockCounter++;
            counterbitCount += Integer.bitCount(bitVector[i]);// this might be it
            RsTmp[blockCounter] = counterbitCount;
        }
        return RsTmp;
    }

    @Override
    public int rank(int i) {

        int RsBlock = i / (32 * 32 * k);
        int bitVecotrIndexStart = RsBlock * 32 * k;
        int remaining = i % (32);
        int bitVectorIndexEnd = i / 32;
        int rank1 = 0;
        int rank2 = 0;
        int rank3 = 0;
        int index = i /(32 * 32 * k);

        rank1 = index != 0 ? Rs[index -1] : 0;

        if (bitVecotrIndexStart < bitVectorIndexEnd) {
            for (int h = bitVecotrIndexStart; h < bitVectorIndexEnd; h++)
                rank2 += Integer.bitCount(bitVector[h]);
        }

        if (remaining != 0 && remaining < 32) {
            int mask = 0xFFFFFFFF << (32 - remaining);

            mask &= bitVector[bitVectorIndexEnd];
            rank3 += Integer.bitCount(mask);
        }

        return rank1 + rank2 + rank3;

    }

    @Override
    public int select(int r) {
        int mins = 0;
        int max = bitVector.length * 32 - 1;

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
