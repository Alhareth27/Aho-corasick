import java.util.*;

public class AhoCorasick {
    private TrieNode root;

    public AhoCorasick(List<String> patterns) {
        root = new TrieNode();
        buildTrie(patterns);
        buildFailLinks();
    }

    private void buildTrie(List<String> patterns) {
        for (String pattern : patterns) {
            TrieNode current = root;
            for (char c : pattern.toCharArray()) {
                current.children.putIfAbsent(c, new TrieNode());
                current = current.children.get(c);
            }
            current.outputs.add(pattern);
        }
    }

    private void buildFailLinks() {
        Queue<TrieNode> queue = new LinkedList<>();
        root.failLink = root;
        queue.add(root);

        while (!queue.isEmpty()) {
            TrieNode current = queue.remove();
            for (TrieNode child : current.children.values()) {
                TrieNode failLink = current.failLink;

                while (failLink != root && !failLink.children.containsKey(child)) {
                    failLink = failLink.failLink;
                }

                if (failLink.children.containsKey(child)) {
                    child.failLink = failLink.children.get(child);
                } else {
                    child.failLink = root;
                }

                queue.add(child);
            }
        }
    }

    public List<String> search(String text) {
        List<String> matches = new ArrayList<>();
        TrieNode current = root;

        for (char c : text.toCharArray()) {
            while (current != root && !current.children.containsKey(c)) {
                current = current.failLink;
            }

            if (current.children.containsKey(c)) {
                current = current.children.get(c);
                matches.addAll(current.outputs);
            }
        }

        return matches;
    }

    public static void main(String[] args) {
        List<String> patterns = Arrays.asList("he", "she", "his", "hers");
        AhoCorasick ahoCorasick = new AhoCorasick(patterns);

        String text = "oshehisher";
        List<String> matches = ahoCorasick.search(text);

        System.out.println("Text: " + text);
        System.out.println("Matches: " + matches);
    }
}
