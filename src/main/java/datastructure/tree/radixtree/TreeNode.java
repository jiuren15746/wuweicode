package datastructure.tree.radixtree;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

/**
 * https://android.googlesource.com/platform/external/smali/+/android-5.1.1_r8/util/src/main/java/ds/tree/RadixTreeNode.java
 */
@Data
public class TreeNode {
    private String content;
    private Map<Character, TreeNode> children;
    /**
     * 从root走到该节点，是否组成一个单词. 如果为true，可以认为在children[0]处增加一个空的子节点
     */
    private boolean isWord = false;

    public TreeNode() {
        this.content = "";
        this.children = new HashMap<>();
    }

    public TreeNode(String value) {
        this.content = value;
        this.children = new HashMap<>();
    }

    public TreeNode(String value, Map<Character, TreeNode> children, boolean isWord) {
        this.content = value;
        this.children = children;
        this.isWord = isWord;
    }

    public void addChild(TreeNode child) {
        char routingChar = child.content.charAt(0);
        assertFalse(children.containsKey(routingChar));
        children.put(routingChar, child);
    }

    /**
     * 节点拆分。count表示保留的字符个数。
     * @param count
     */
    public void split(int count) {
        assertTrue(count > 0 && count < content.length());
        String keep = content.substring(0, count);
        String left = content.substring(count);
        TreeNode newNode = new TreeNode(left, this.children, this.isWord);
        this.content = keep;
        this.isWord = false;
        this.children = Maps.newHashMap();
        this.children.put(newNode.content.charAt(0), newNode);
    }

    /**
     * 该节点内容与str按逐个字符匹配。返回匹配的字符个数。
     * @param str
     * @return
     */
    public int matchString(String str) {
        int matchCount = 0;
        for (int i = 0; i < content.length() && i < str.length(); ++i) {
            if (content.charAt(i) == str.charAt(i)) {
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
