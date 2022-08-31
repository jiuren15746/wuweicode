package datastructure.tree.radixtree;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.testng.collections.Maps;

/**
 * https://android.googlesource.com/platform/external/smali/+/android-5.1.1_r8/util/src/main/java/ds/tree/RadixTreeNode.java
 */
@Data
public class TreeNode {
    private String key;
    private Map<Character, TreeNode> children;
    /**
     * 从root走到该节点，是否组成一个单词. 如果为true，可以认为在children[0]处增加一个空的子节点
     */
    private boolean isWord = false;

    public TreeNode() {
        this.key = "";
        this.children = new HashMap<>();
    }

    public TreeNode(String value) {
        this.key = value;
        this.children = new HashMap<>();
    }

    public TreeNode(String value, Map<Character, TreeNode> children, boolean isWord) {
        this.key = value;
        this.children = children;
        this.isWord = isWord;
    }

    public void addChild(TreeNode child) {
        char routingChar = child.key.charAt(0);
        assertFalse(children.containsKey(routingChar));
        children.put(routingChar, child);
    }

    /**
     * 节点拆分。count表示保留的字符个数。
     * @param count
     */
    public void split(int count) {
        assertTrue(count > 0 && count < key.length());
        String keep = key.substring(0, count);
        String left = key.substring(count);
        TreeNode newNode = new TreeNode(left, this.children, this.isWord);
        this.key = keep;
        this.isWord = false;
        this.children = Maps.newHashMap();
        this.children.put(newNode.key.charAt(0), newNode);
    }

    /**
     * 该节点内容与str按逐个字符匹配。返回匹配的字符个数。
     * @param str
     * @return
     */
    public int matchString(String str) {
        int matchCount = 0;
        for (int i = 0; i < key.length() && i < str.length(); ++i) {
            if (key.charAt(i) == str.charAt(i)) {
                matchCount++;
            }
        }
        return matchCount;
    }


    public static void main(String[] args) {
        TreeNode node = new TreeNode("hello");

        String str = "alibaba";
        assertEquals(0, node.matchString(str));

        str = "haha";
        assertEquals(1, node.matchString(str));
    }


}
