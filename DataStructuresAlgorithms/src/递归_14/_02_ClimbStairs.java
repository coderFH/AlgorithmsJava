package 递归_14;
/*
* 走台阶,每次只能走1个台阶或者2个台阶,当有n个台阶时,共有几种走法
* */
public class _02_ClimbStairs {
    int climbStairs(int n) {
        if (n <= 2) return n; // 如果n是1,就有1中走法,如果n是2,则有两种走法(一个走1阶,或一次走2阶)
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    int climbStairs1(int n) {
        if (n <= 2) return n;
        int first = 1;
        int second = 2;
        for (int i = 3;i <= n;i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }

    public static void main(String[] args) {
        int t = 4;
        _02_ClimbStairs cs = new _02_ClimbStairs();
        int count = cs.climbStairs(t);
        int count1 = cs.climbStairs1(t);
        System.out.println("当有" + t + "台阶时,共有" + count + " ~ " + count1 + "走法");
    }
}
