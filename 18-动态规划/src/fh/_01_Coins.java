/*
322. 零钱兑换
给定不同面额的硬币 coins 和一个总金额 amount。
编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。

示例 1:

输入: coins = [1, 2, 5], amount = 11
输出: 3
解释: 11 = 5 + 5 + 1
示例 2:

输入: coins = [2], amount = 3
输出: -1

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/coin-change
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */

package fh;

public class _01_Coins {
    // 假设有25 20 5 1 的硬币,要找给客户41分的零钱,如何办到硬币个数最少?
    public static void main(String[] args) {
        System.out.println("暴力法: " + coins(6));
        System.out.println("记忆化搜索: " + coins2(6));
        System.out.println("迭代: " + coins3(6));
        System.out.println("挑选的具体面值: " + coins4(6));
        System.out.println("通用: " + coins5(6,new int[] {1,5,25,20}));
    }

    /*
     * 方法1: 暴力递归
     * 解题思路,既然coins(n)是凑够n分硬币,需要最少的硬币数
     * 那么:  coins(n-25) 就是凑够n-25分硬币需要的最少硬币数
     *       coins(n-20) 就是凑够n-20分硬币需要的最少硬币数
     *       coins(n-5)  就是凑够n-5 分硬币需要的最少硬币数
     *       coins(n-1)  就是凑够n-1 分硬币需要的最少硬币数
     * 依次递归之后,其实就是取出这四种情况下,需要凑够的最少硬币数的基础上在+1
     * */
    static int coins(int n) {
        if (n < 1) return Integer.MAX_VALUE;
        if (n == 1 || n == 5 || n == 20 || n==25) return 1;
        int min1 = Math.min(coins(n-25),coins(n-20));
        int min2 = Math.min(coins(n-5),coins(n-1));
        return Math.min(min1,min2) + 1;
    }

    /*
     * 方式2: 记忆化搜索
     * 由于方式1,会有大量的重复计算,所以可以对已经计算过的进行保存
     * */
    static int coins2(int n) {
        if (n < 1) return Integer.MAX_VALUE;
        int[] dp = new int[n + 1];
        int[] faces = {1,5,20,25};
        for (int face : faces) {
            if (n < face) break;
            dp[face] = 1;
        }
        return coins22(n,dp);
    }
    static int coins22(int n,int[] dp) {
        if (n < 1) return Integer.MAX_VALUE;
        if (dp[n] == 0) {
            int min1 = Math.min(coins22(n-25,dp),coins22(n-20,dp));
            int min2 = Math.min(coins22(n-5,dp),coins22(n-1,dp));
            dp[n] = Math.min(min1,min2) + 1;
        }
        return dp[n];
    }

    /*
     * 方式3:递推
     * 上边两种方式,都是递归实现,需要单独的开辟栈空间,可以考虑使用递推实现
     * */
    static int coins3(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n+1];
        for (int i = 1; i <= n; i++) {
            int min = dp[i-1];
            if (i >= 5) min = Math.min(dp[i-5],min);
            if (i >= 20) min = Math.min(dp[i-20],min);
            if (i >= 25) min = Math.min(dp[i-25],min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /*
     * 输出找零钱的具体方案(具体是用了哪些面值的硬币)
     * */
    static int coins4(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n+1];
        //faces[i]是凑够i分时最后那枚硬币的面值
        int[] faces = new int[dp.length];
        for (int i = 1; i <=n; i++) {
            int min = dp[i-1];
            faces[i] = 1;
            if (i>=5 && dp[i-5] < min) {
                min = dp[i-5];
                faces[i] = 5;
            }
            if (i >=20 && dp[i-20] <min) {
                min = dp[i-20];
                faces[i] = 20;
            }
            if (i>25 && dp[i-25] <min) {
                min = dp[i-25];
                faces[i] = 25;
            }
            dp[i] = min + 1;
            print(faces,i);
        }
        return dp[n];
    }

    static void print(int[] faces,int n) {
        System.out.println("[" + n + "] = ");
        while (n > 0) {
            System.out.println(faces[n] + "");
            n -= faces[n];
        }
        System.out.println();
    }

    /*
     * 通用实现
     * */
    static int coins5(int n,int[] faces) {
        if (n < 1 || faces == null || faces.length == 0) return -1;
        int[] dp = new int[n+1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces) {
                if (i < face) continue;
                int v = dp[i - face];
                if (v< 0 || v >= min) continue;
                min = v;
            }
            if (min == Integer.MAX_VALUE) {
                dp[i] = -1;
            } else {
                dp[i] = min + 1;
            }
        }
        return dp[n];
    }
}
