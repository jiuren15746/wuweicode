package datastructure.array.algo.twopointers;

import java.util.HashMap;
import java.util.Map;

/**
 * 难度：中等
 * 使用滑动窗口。
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/solution/hua-dong-chuang-kou-by-powcai/
 *
 * 思路：
 *   指针j向右移，step=1。每次移动后都要判断是否有重复字符。
 *   如果有重复字符，指针i向右移到重复字符位置的右边。右指针不动。
 *
 * 时间复杂度：O(N)
 *
 */
public class 无重复字符的最长子串 {

    static public String solution(String str) {
        String maxSubstr = "";
        int i = 0;
        // 使用Map保存字符穿线的位置
        Map<Character, Integer> windowChars = new HashMap<>();

        // 右指针一个字符一个字符向右移
        for (int j = 0; j < str.length(); j++) {
            // 查找是否是重复字符。如果是，左指针右移到firstIdx+1，同时清除窗口字符
            Integer firstIdx = windowChars.put(str.charAt(j), j);

            if (firstIdx != null && firstIdx >= i) {
                // 发现重复字符，窗口收缩，左指针向右移动
                i = firstIdx + 1;
            } else {
                // 窗口扩张，保存窗口长度
                if (j-i+1 > maxSubstr.length()) {
                    maxSubstr = str.substring(i, j + 1);
                }
            }
        }
        return maxSubstr;
    }

    // 只返回长度  "nfpdmpi"
    static public int solution2(String str) {
        int maxLength = 0;
        int i = 0;
        // 使用Map保存字符穿线的位置
        Map<Character, Integer> windowChars = new HashMap<>();

        // 右指针一个字符一个字符向右移。每前进一步，都要判断是否存在重复字符。
        for (int j = 0; j < str.length(); j++) {
            Integer firstIdx = windowChars.put(str.charAt(j), j);
            if (firstIdx != null && firstIdx >= i) {
                // 发现重复字符，窗口收缩，左指针向右移动
                i = firstIdx + 1;
            } else {
                // 窗口扩张，保存窗口长度
                maxLength = Math.max(maxLength, j-i+1);
            }
        }
        return maxLength;
    }

}
