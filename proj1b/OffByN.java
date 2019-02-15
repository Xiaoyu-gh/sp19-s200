public class OffByN implements CharacterComparator{

    public int offByNumber;

    public OffByN(int N) {
        offByNumber = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        if ((b - a) == offByNumber) {
            return true;
        }
        return false;
    }

}
