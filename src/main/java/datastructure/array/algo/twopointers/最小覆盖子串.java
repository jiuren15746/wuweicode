package datastructure.array.algo.twopointers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class 最小覆盖子串 {

    /**
     * 从str1中找到能覆盖str2所有字符的最小子串，不考虑字符顺序。
     * 如果str2中某个字符重复N次，str1中该字符也要重复N次。
     * @param str1
     * @param str2
     * @return
     */
    static public String solution(String str1, String str2) {

        Map<Character, AtomicInteger> str1Chars = getChars(str1);
        Map<Character, AtomicInteger> str2Chars = getChars(str2);

        if (!isCover(str1Chars, str2Chars)) {
            return null;
        }

        int i = 0;
        int j = str1.length() - 1;
        Map<Character, AtomicInteger> windowChars = str1Chars;


        for (;;) {
            // i循环向右，直到找到一个在str2的字符
            for (;;) {
                if (!str2Chars.containsKey(str1.charAt(i))) {
                    i++;
                } else {
                    break;
                }
            }

            // j向左，
            while (j > i) {
                char jChar = str1.charAt(j);
                if (!str2Chars.containsKey(jChar)
                        || isCountGreater(windowChars, jChar, str2Chars)) {
                    j--;
                    windowChars.get(jChar).decrementAndGet();
                } else {
                    break;
                }
            }

            System.out.println("substring: " + str1.substring(i, j+1));
            return str1.substring(i, j+1);
        }
    }


    static private Map<Character, AtomicInteger> getChars(String str) {
        Map<Character, AtomicInteger> chars = new HashMap<>();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (chars.containsKey(ch)) {
                chars.get(ch).incrementAndGet();
            } else {
                chars.put(ch, new AtomicInteger(1));
            }
        }
        return chars;
    }

    static private boolean isCover(Map<Character, AtomicInteger> windowChars,
                                   Map<Character, AtomicInteger> str2Chars) {
        for (Character ch : str2Chars.keySet()) {
            int count = str2Chars.get(ch).get();
            Integer windowCharCount = windowChars.get(ch).get();
            if (null == windowCharCount || windowCharCount < count) {
                return false;
            }
        }
        return true;
    }

    // 窗口中的字符ch，是否与str2中ch的个数相同。
    static private boolean isCountEquals(Map<Character, AtomicInteger> windowChars,
                                         char ch,
                                         Map<Character, AtomicInteger> str2Chars) {
        return windowChars.get(ch).get() == str2Chars.get(ch).get();
    }

    // 返回是否成立：窗口中的ch个数 > str2中ch个数
    static private boolean isCountGreater(Map<Character, AtomicInteger> windowChars,
                                          char ch,
                                          Map<Character, AtomicInteger> str2Chars) {
        return windowChars.get(ch).get() > str2Chars.get(ch).get();
    }



    static public void main(String[] args) {

        String str1 = "fffCffeeAABBeCeCeee";
        String str2 = "AABBCC";

        System.out.println(solution(str1, str2));
    }
}
