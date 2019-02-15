public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        if ((b - a) == 1) {
            return true;
        }
        return false;
    }
}
