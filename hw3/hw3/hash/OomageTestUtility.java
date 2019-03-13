package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        int[] bucket = new int[M];
        int n = oomages.size();
        for (int i = 0; i < n; i++) {
            int bucketNum = (oomages.get(i).hashCode() & 0x7FFFFFFF) % M;
            bucket[bucketNum]++;
            if ((bucket[bucketNum] > n / 2.5) && (bucket[bucketNum] < n / 50)) {
                return false;
            }
        }
        return true;
    }
}
