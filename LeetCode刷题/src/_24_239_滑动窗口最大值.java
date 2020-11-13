import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/*
* 给定一个数组 nums，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k个数字。滑动窗口每次只向右移动一位。

返回滑动窗口中的最大值。

进阶：

你能在线性时间复杂度内解决此题吗？

示例:

输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
输出: [3,3,5,5,6,7]
解释:

  滑动窗口的位置                最大值
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7

提示：

1 <= nums.length <= 10^5
-10^4<= nums[i]<= 10^4
1 <= k<= nums.length


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

    public int[] maxSlidingWindow1(int[] nums, int k) {
        
    }

}
