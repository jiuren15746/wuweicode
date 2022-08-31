package datastructure.tree.radixtree;

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
public class TreeNode<V> {
    private String key;
    private V value;
    private Map<Character, TreeNode<V>> children;

    public TreeNode() {
        this.key = "";
        this.children = new HashMap<>();
    }

    public TreeNode(String key, V value) {
        this.key = key;
        this.value = value;
        this.children = new HashMap<>();
    }

    public void addChild(TreeNode<V> child) {
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
        // 拆分出的新节点继承了原节点的value和children
        TreeNode<V> newNode = new TreeNode(key.substring(count), this.value);
        newNode.children = this.children;

        this.key = key.substring(0, count);
        this.value = null;
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

}
