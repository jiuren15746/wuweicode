package datastructure.tree.radixtree.impl;

import static datastructure.tree.radixtree.impl.RadixTreeImpl.*;

import static org.testng.Assert.assertEquals;

import datastructure.tree.radixtree.RadixTree;
import datastructure.tree.radixtree.TreeNode;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;



public class RadixTreeImplTest {

    @Test
    public void testInsertAndSearch() throws Exception {
        List<String> lines = IOUtils.readLines(new InputStreamReader(RadixTreeImplTest.class.getResourceAsStream("/words.txt")));

        RadixTreeImpl<String> tree = new RadixTreeImpl<>();
        for (String line : lines) {
            line = line.trim();
            if (line.length() > 0) {
                tree.insert(line, line);
            }
        }

        // 查找 the
        String word = "the";
        SearchResult<String> result = tree.search(word);
        assertEquals(result.result, SR_MATCH);
        assertEquals(result.path.size(), 4);
        TreeNode<String> lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "e");
        assertEquals(lastNode.getValue(), word);
        assertEquals(lastNode.getChildren().size(), 2);

        // 查找 there
        word = "there";
        result = tree.search(word);
        assertEquals(result.result, SR_MATCH);
        assertEquals(result.path.size(), 5);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "re");
        assertEquals(lastNode.getValue(), word);
        assertEquals(lastNode.getChildren().size(), 0);

        // 查找 ther
        word = "ther";
        result = tree.search("ther");
        assertEquals(result.result, SR_SUBSTR);
        assertEquals(result.path.size(), 5);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "re");
        assertEquals(lastNode.getValue(), "there");
        assertEquals(lastNode.getChildren().size(), 0);

        // 查找 think
        word = "think";
        result = tree.search(word);
        assertEquals(result.result, SR_PREFFIX);
        assertEquals(result.path.size(), 4);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "is");
        assertEquals(lastNode.getValue(), "this");
        assertEquals(lastNode.getChildren().size(), 0);

        // 查找 too
        word = "too";
        result = tree.search(word);
        assertEquals(result.result, SR_SUPER);
        assertEquals(result.path.size(), 3);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "o");
        assertEquals(lastNode.getValue(), "to");
        assertEquals(lastNode.getChildren().size(), 0);
    }
}
