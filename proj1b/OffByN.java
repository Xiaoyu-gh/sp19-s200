public class OffByN implements CharacterComparator {

    private int offByNumber;

    public OffByN(int N) {
        offByNumber = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        if (Math.abs((b - a)) == offByNumber) {
            return true;
        }
        return false;
    }

}
