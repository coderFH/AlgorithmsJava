package 栈队列_03;

import java.util.Deque;
import java.util.LinkedList;

/*
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/sliding-window-maximum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class _24_239_滑动窗口最大值 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || nums == null || k < 1) return new int[0];
        if (k == 1) return nums;

        Deque<Integer> deque = new LinkedList<>();
        int[] maxes = new int[nums.length - k + 1];

        for (int ri = 0; ri <= nums.length - 1 ; ri++) {
            while (!deque.isEmpty() && nums[ri] >= nums[deque.peekLast()] ) {
                deque.removeLast();
            }

            deque.addLast(ri);

            int li = ri - k + 1;
            if (li < 0) continue;

            // 检查对头的合法性
            if (deque.peekFirst() < li) {
                // 队头不合法（失效，不在滑动窗口索引范围内）
                deque.removeFirst();
            }

            maxes[li] = nums[deque.peekFirst()];
        }
        return maxes;
    }
}
