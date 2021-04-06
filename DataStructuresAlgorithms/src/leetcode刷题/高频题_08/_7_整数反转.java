package leetcode刷题.高频题_08;

/*
* https://leetcode-cn.com/problems/reverse-integer/
* */
public class _7_整数反转 {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            if (result > Integer.MAX_VALUE) return 0;
            if (result < Integer.MIN_VALUE) return 0;
            x = x / 10;
        }
        return result;
    }

    public static void main(String[] args) {
        _7_整数反转 o = new _7_整数反转();
        System.out.println(o.reverse(-345));
    }
}
