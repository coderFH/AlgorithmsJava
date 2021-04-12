package leetcode刷题.动态规划_05;

/*
* https://leetcode-cn.com/problems/longest-palindromic-substring/
* */
public class _5_最长回文子串 {
    /*
    * 解法1: 动态规划
    * */
    public String longestPalindrome(String s) {
        if (s == null) return null;
        char[] cs = s.toCharArray();
        if (cs.length <= 1) return s;
        int maxLen = 1; // 最长回文子串的长度（至少是1）
        int begin = 0;  // 最长回文子串的开始索引
        boolean[][] dp = new boolean[cs.length][cs.length];
        // 从下到上（i由大到小）
        for (int i = cs.length - 1; i >=0 ; i--) {
            // 从左到右（j由小到大）
            for (int j = i; j <= cs.length - 1 ; j++) {
                int len = j - i + 1;
                dp[i][j] = (cs[i]== cs[j]) && (len <= 2 || dp[i+1][j-1]);
                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    begin = i;
                }
            }
        }
        return new String(cs,begin,maxLen);
    }

    /*
     * 解法2: 扩展中心法
     * */
    public String longestPalindrome1(String s) {
        if (s == null) return null;
        char[] cs = s.toCharArray();
        if (cs.length <= 1) return s;
        int maxLen = 1; // 最长回文子串的长度（至少是1）
        int begin = 0;  // 最长回文子串的开始索引
        for (int i = cs.length - 2; i >= 1 ; i--) {
            // 以字符为中心向左右扩展
            int len1 = palindromeLength(cs,i-1,i+1);
            // 以字符右边的间隙为中心向左右扩展
            int len2 = palindromeLength(cs,i,i+1);
            len1 = Math.max(len1,len2);
            if (len1 > maxLen) {
                maxLen = len1;
                /*
                * 这里有一个小公式,正常情况下知道你的扩展点起点,知道了最大的长度
                * i - 最大长度 / 2 就能求到起点的位置
                * 但由于有可能是以间隙为中心向左右扩展,就容易导致整个公式不适用
                * 所以统一做处理,让 最大长度-1  之后  再 除 2
                * 即 (maxLen - 1) / 2
                * 所以求起始点的公式也就是 i - ((maxLen - 1) / 2)
                 * */
                begin = i - ((maxLen - 1) >> 1);
            }
        }
        // 单独处理0号位置右边的间隙
        if (cs[0] == cs[1] && maxLen < 2) {
            // cs[0, 1]就是最长的回文子串
            begin = 0;
            maxLen = 2;
        }
        return new String(cs,begin,maxLen);
    }

    private int palindromeLength(char[] cs, int l, int r) {
        while (l >= 0 && r < cs.length && cs[l] == cs[r]) { // l和r在正常范围内,并且两个元素相等,就向外扩散
            l--;
            r++;
        }
        return r - l - 1;
    }

    /*
     * 解法3: 基于扩展中心的优化
     * */
    public String longestPalindrome2(String s) {
        if (s == null) return null;
        char[] cs = s.toCharArray();
        if (cs.length <= 1) return s;
        int maxLen = 1; // 最长回文子串的长度（至少是1）
        int begin = 0;  // 最长回文子串的开始索引
        int i = 0;
        while (i < cs.length) {
            int l = i - 1;
            int r = i;// 找到右边第一个不等于cs[i]的位置
            while (++r < cs.length && cs[r] == cs[i]);

            i = r; // r会成为新的i

            // 从l向左，从r向右扩展
            while (l >= 0 && r < cs.length && cs[l] == cs[r]){
                l--;
                r++;
            }

            // 扩展结束后，cs[l + 1, r)就是刚才找到的最大回文子串
            // ++l后，l就是刚才找到的最大回文子串的开始索引
            int len = r - ++l;
            if (len > maxLen) {
                maxLen = len;
                begin = l;
            }
        }
        return new String(cs,begin,maxLen);
    }
    /*
     * 解法4: 马拉车算法
     * */
    public String longestPalindrome3(String s) {
        return "abc";
    }
}
