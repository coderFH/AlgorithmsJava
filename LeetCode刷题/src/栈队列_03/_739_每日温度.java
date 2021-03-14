import java.util.Stack;

/*
* https://leetcode-cn.com/problems/daily-temperatures/
* */
public class _739_每日温度 {
    /*
    * 解法1: 使用栈的方式去找右边第一个比我大的值
    * */
    public int[] dailyTemperatures_stack(int[] T) {
        if (T == null || T.length == 0) return null;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[T.length];

        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                res[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            stack.push(i);
        }
        return res;
    }

    public int[] dailyTemperatures(int[] T) {
        if (T == null || T.length == 0) return null;
        int[] res = new int[T.length];
        return res;
    }

    public static void main(String[] args) {
        _739_每日温度 o = new _739_每日温度();
        int[] nums = {73, 74, 75, 71, 69, 72, 76, 73};
        o.dailyTemperatures(nums);
    }
}
