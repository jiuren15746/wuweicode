public class Test {

    /**
     * One Away: There are three types of edits that can be performed on strings: insert a character,
     * remove a character, or replace a character. Given two strings, write a function to check if they are
     * one edit (or zero edits) away.
     * EXAMPLE
     * pale, ple true
     * pales, pale -> true
     * pale, bale -> true
     * pale, bae -> false
     */

    // 是否a插入一个字符，可以变成b
    static public boolean checkInsert(String a, String b) {

        System.out.println("checkInsert:" + a + ", " + b);

        boolean mismatchHappenFlag = false;

        for (int i = 0; i < a.length(); ) {
            char cha = a.charAt(i);
            char chb = b.charAt(i);
            if (mismatchHappenFlag) {
                chb = b.charAt(i + 1);
            }

            if (cha == chb) {
                ++i;
                continue;
            } else if (mismatchHappenFlag == false) {
                mismatchHappenFlag = true;
            } else {
                return false;
            }
        }
        return true;
    }

    // 是否a替换一个字符，可以变成b
    static public boolean checkReplace(String a, String b) {
        // todo
        return true;
    }


    static public void main(String[] args) {
        String str1 = "pale";
        String str2 = "ple";

        boolean checkResult = false;

        if (str1.length() + 1 == str2.length()) {
            checkResult = checkInsert(str1, str2);
        } else if (str1.length() - 1 == str2.length()) {
            checkResult = checkInsert(str2, str1);
        } else if (str1.length() == str2.length()) {
            checkResult = checkReplace(str1, str2);
        }

        System.out.println(checkResult);
    }

}
