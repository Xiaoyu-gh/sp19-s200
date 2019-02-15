public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        int size = word.length();
        Deque<Character> charList = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            charList.addLast(word.charAt(i));
        }
        return charList;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> charList = wordToDeque(word);

        if (word.length() == 0) {
            return true;
        }

        boolean pal = true;
        int i = 0;
        int j = word.length() - 1;
        while (pal && (i < (word.length()) / 2)) {
            if (charList.get(i) != charList.get(j)) {
                pal = false;
            }
            i++;
            j--;
        }
        return pal;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> charList = wordToDeque(word);

        if (word.length() == 0) {
            return true;
        }

        boolean pal = true;
        int i = 0;
        int j = word.length() - 1;
        while (pal && (i < (word.length()) / 2)) {
            if (!cc.equalChars(charList.get(i), charList.get(j))) {
                pal = false;
            }
            i++;
            j--;
        }
        return pal;

    }
}
