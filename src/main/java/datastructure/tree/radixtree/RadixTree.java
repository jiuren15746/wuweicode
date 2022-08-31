package datastructure.tree.radixtree;

import java.util.List;
import org.testng.collections.Lists;

public class RadixTree {

    /**
     * 在最后一个节点，str和节点内容完全相同.
     */
    protected static final int SR_MATCH = 0;
    /**
     * 在最后一个节点，str是节点内容的子串
     */
    protected static final int SR_SUBSTR = 1;
    /**
     * 在最后一个节点，str和节点内容有公共前缀
     */
    protected static final int SR_PREFFIX = 2;
    /**
     * 在最后一个节点，str是节点内容的超串，但是被吃掉剩余的部分没有路由
     */
    protected static final int SR_SUPER = 3;


    private TreeNode root = new TreeNode();
    //========

    public static class SearchResult {
        List<TreeNode> path = Lists.newArrayList();
        // 最后一个节点的匹配结果，参考上面常量
        int result;
        // 最后一个节点匹配字符数
        int matchCount;
        // 所有节点匹配字符数总和
        int totalMatchCount;
    }

    public SearchResult search(String word) {
        SearchResult result = new SearchResult();
        search(root, word, result);
        return result;
    }

    /**
     * 从上到下逐个节点匹配。内部使用递归。
     */
    private void search(TreeNode node, String str, SearchResult result) {
        final int matchCount = node.matchString(str);
        result.path.add(node);
        result.matchCount = matchCount;
        result.totalMatchCount += matchCount;

        // 完全相等，结束
        if (matchCount == node.getContent().length() && matchCount == str.length()) {
            result.result = SR_MATCH;
        }
        // 节点内容是str的子串，向下走
        else if (matchCount == node.getContent().length()) {
            // str被吃掉一部分
            String newStr = str.substring(matchCount);
            // Routing
            TreeNode subNode = node.getChildren().get(newStr.charAt(0));
            if (null == subNode) {
                // 路由不到子节点
                result.result = SR_SUPER;
            } else {
                // 递归调用子节点的search方法
                search(subNode, newStr, result);
            }
        }
        // str是节点内容的子串, 返回
        else if (matchCount == str.length()) {
            result.result = SR_SUBSTR;
        }
        // str和节点内容有公共前缀，返回
        else {
            result.result = SR_PREFFIX;
        }
    }

    public void insert(String word) {
        // search
        SearchResult result = search(word);

        TreeNode lastNode = result.path.get(result.path.size() - 1);
        int resultCase = result.result;
        int matchCount = result.matchCount;
        int totalMatchCount = result.totalMatchCount;

        if (SR_MATCH == resultCase) {
            lastNode.setWord(true);
        }
        // word是最后节点内容的子串. 节点拆分. 剩余部分作为子节点，节点children下移作为新建子节点的children
        else if (SR_SUBSTR == resultCase) {
            lastNode.split(matchCount);
            lastNode.setWord(true);
        }
        // word和最后节点内容有公共前缀. 节点拆分，并增加一个新的子节点。
        else if (SR_PREFFIX == resultCase) {
            lastNode.split(matchCount);
            TreeNode wordNode = new TreeNode(word.substring(totalMatchCount));
            wordNode.setWord(true);
            lastNode.addChild(wordNode);
        }
        // word是节点内容的超串，但是被吃掉剩余的部分没有路由. word截断剩余部分作为子节点。
        else if (SR_SUPER == resultCase) {
            TreeNode wordNode = new TreeNode(word.substring(totalMatchCount));
            wordNode.setWord(true);
            lastNode.addChild(wordNode);
        }
    }

}
