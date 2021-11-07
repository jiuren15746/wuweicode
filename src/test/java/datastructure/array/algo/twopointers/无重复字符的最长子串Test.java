package datastructure.array.algo.twopointers;

import org.testng.Assert;
import org.testng.annotations.Test;

import static datastructure.array.algo.twopointers.无重复字符的最长子串.solution;

public class 无重复字符的最长子串Test {
    @Test
    public void test() {
        String str = "abcdecabhijklmn";

        String maxSubstr = solution(str);

        Assert.assertEquals(maxSubstr, "decabhijklmn");
    }
}
