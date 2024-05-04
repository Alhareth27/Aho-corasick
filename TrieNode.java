import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children;
    TrieNode failLink;
    List<String> outputs;

    public TrieNode() {
        children = new HashMap<>();
        failLink = null;
        outputs = new ArrayList<>();
    }
}