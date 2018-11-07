public class HuffmanNode {
    int freq;
    char c;

    HuffmanNode left;
    HuffmanNode right;

    public boolean isLeaf(){
        return (left == null || right == null);
    }
}
