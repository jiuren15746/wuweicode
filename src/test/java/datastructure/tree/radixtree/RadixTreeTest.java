package datastructure.tree.radixtree;

import static datastructure.tree.radixtree.RadixTree.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import datastructure.tree.radixtree.RadixTree.SearchResult;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;



public class RadixTreeTest {

    @Test
    public void testInsertAndSearch() throws Exception {
        List<String> lines = IOUtils.readLines(new InputStreamReader(RadixTreeTest.class.getResourceAsStream("/words.txt")));

        RadixTree tree = new RadixTree();
        for (String line : lines) {
            line = line.trim();
            if (line.length() > 0) {
                tree.insert(line);
            }
        }

        // 查找 the
        SearchResult result = tree.search("the");
        assertEquals(result.result, SR_MATCH);
        assertEquals(result.path.size(), 4);
        TreeNode lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "e");
        assertTrue(lastNode.isWord());
        assertEquals(lastNode.getChildren().size(), 2);

        // 查找 there
        result = tree.search("there");
        assertEquals(result.result, SR_MATCH);
        assertEquals(result.path.size(), 5);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "re");
        assertTrue(lastNode.isWord());
        assertEquals(lastNode.getChildren().size(), 0);

        // 查找 think
        result = tree.search("think");
        assertEquals(result.result, SR_PREFFIX);
        assertEquals(result.path.size(), 4);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "is");
        assertTrue(lastNode.isWord());
        assertEquals(lastNode.getChildren().size(), 0);

        // 查找 too
        result = tree.search("too");
        assertEquals(result.result, SR_SUPER);
        assertEquals(result.path.size(), 3);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "o");
        assertTrue(lastNode.isWord());
        assertEquals(lastNode.getChildren().size(), 0);

        // 查找 ther
        result = tree.search("ther");
        assertEquals(result.result, SR_SUBSTR);
        assertEquals(result.path.size(), 5);
        lastNode = result.path.get(result.path.size() - 1);
        assertEquals(lastNode.getKey(), "re");
        assertTrue(lastNode.isWord());
        assertEquals(lastNode.getChildren().size(), 0);
    }
}
