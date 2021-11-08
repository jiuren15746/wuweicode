package datastructure.array.algo.twopointers;

import java.util.HashMap;
import java.util.Map;

/**
 * 难度：中等
 * 使用滑动窗口。
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/solution/hua-dong-chuang-kou-by-powcai/
 */
public class 无重复字符的最长子串 {

    static public String solution(String str) {
        String maxSubstr = "";
        int i = 0;
        Map<Character, Integer> windowChars = new HashMap<>();

        // 右指针一个字符一个字符向右移
        for (int j = 0; j < str.length(); j++) {
            // 判断左指针是否需要向右移动.
            // 发现重复字符。左指针右移到firstIdx+1，同时清除窗口字符
            Integer firstIdx = windowChars.get(str.charAt(j));
            if (firstIdx != null) {
                while (i < firstIdx + 1) {
                    windowChars.remove(str.charAt(i));
                    i++;
                }
            }
            windowChars.put(str.charAt(j), j);

            // 窗口内的字符串长度，如果是最长子串，保存下来
            if (j - i + 1 > maxSubstr.length()) {
                maxSubstr = str.substring(i, j+1);
            }
        }
        return maxSubstr;
    }

//    static public String solution(String s) {
//        if (s.length() == 0) return "";
//        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
//        String maxSubstr = "";
//        int left = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (map.containsKey(s.charAt(i))) {
//                left = Math.max(left, map.get(s.charAt(i)) + 1);
//            }
//            map.put(s.charAt(i), i);
//            if (i - left + 1 > maxSubstr.length()) {
//                maxSubstr = s.substring(left, i);
//            }
//        }
//        return maxSubstr;
//    }


}
