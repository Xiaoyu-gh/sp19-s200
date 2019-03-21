import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/**@source: SandraHui @ Thur 9-11 am Lab
 *
 */

public class MyTrieSet implements TrieSet61B {

    private Node root;

    private class Node {
        char value;
        boolean atEnd;
        HashMap<Character, Node> kids;
        Node(char c) {
            this.value = c;
            this.atEnd = false;
            this.kids = new HashMap<>();
        }
    }

    public MyTrieSet() {
        this.root = new Node('\0');
    }


    @Override
    public void clear() {
        this.root = new Node('\0');

    }

    @Override
    public boolean contains(String key) {
        if(key == null || key.length() == 0) {
            return true;
        }

        Node currNode = root;
        for(int i = 0; i < key.length(); i++) {
            char curr = key.charAt(i);
            // if the current node has this letter, then go to that node and
            // don't create new node
            if(!currNode.kids.containsKey(curr)) {
                return false;
            }
            currNode = currNode.kids.get(curr);
        }
        return currNode.atEnd;
    }

    @Override
    public void add(String key) {
        if(key == null || key.length() == 0) {
            return;
        }
        Node currNode = root;
        for(int i = 0; i < key.length(); i++) {
            char curr = key.charAt(i);
            // if the current node has this letter, then go to that node and
            // don't create new node
            if(!currNode.kids.containsKey(curr)) {
                Node newNode = new Node(curr);
                currNode.kids.put(curr, newNode);
            }
            currNode = currNode.kids.get(curr);
        }
        currNode.atEnd = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        LinkedList<String> results = new LinkedList<>();
        //traverse the trie until reaching the end;
        if (prefix == null || prefix.length()== 0) {
            return results;
        }

        Node currNode = root;
        for(int i = 0; i < prefix.length(); i++) {
            char curr = prefix.charAt(i);
            // if the current node has this letter, then go to that node and
            // don't create new node
            if(!currNode.kids.containsKey(curr)) {
                return results;
            }
            currNode = currNode.kids.get(curr);
        }

        prefixHelper(currNode, prefix, results);
        return results;
    }

    private void prefixHelper(Node curr, String prefix, LinkedList<String> results) {
        if(curr.atEnd) {
            results.add(prefix);
        }
        for(Character c: curr.kids.keySet()) {
            Node kid = curr.kids.get(c);
            prefixHelper(kid, prefix + c, results);
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException("Do not support!");
    }
}
