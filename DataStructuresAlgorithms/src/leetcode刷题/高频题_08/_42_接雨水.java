package leetcode刷题.高频题_08;

import 分治_17.Main;

import java.util.Stack;

/*
*   https://leetcode-cn.com/problems/trapping-rain-water/
* */
public class _42_接雨水 {
    /*
    * 解法1:求出每一个柱子左边的最大值和右边的最大值,然后取较小的那个
    * 然后用取到的值减去当前高度,就能求得当前柱子能放多少水
    * */
    public int trap(int[] height) {
        if (height.length < 3) return 0;
        int water = 0;

        int lastIdx = height.length - 2;
        int[] rightMaxes = new int[height.length];
        for (int i = lastIdx; i >= 1; i--) {
            rightMaxes[i] = Math.max(rightMaxes[i + 1],height[i+1]);
        }

        // 遍历每一根柱子，看看每一根柱子上能放多少水
        int leftMax = 0;
        for (int i = 1; i <= lastIdx; i++) {
            leftMax = Math.max(leftMax, height[i - 1]);
            // 求出左边最大、右边最大中的较小者
            int minH = Math.min(leftMax,rightMaxes[i]);
            if (minH <= height[i]) continue;    // 说明这根柱子不能放水
            water = water + (minH - height[i]); // 说明这根柱子能放水
        }
        return water;
    }

    /*
    * 解法2:头尾两个指针
    * 其实假设l的值一直是小于r的,即一直走l++
    * lowerMax其实就是某个柱子左边的最大值(并且lowerMax一定是小于他右边的最大值的,因为你是l++过来的)
    * 所以某个柱子能接的雨水,其实就是lowerMax-当前柱子的高度
    * 先单方向理解吧,单方向理解通了,双方向的就可以理解了
    * */
    public int trap1(int[] height) {
        if (height == null || height.length == 0) return 0;
        if (height.length < 3) return 0;
        int water = 0;
        int lowerMax = 0;
        int l = 0;
        int r = height.length - 1;
        while (l < r) {
            int lower = height[l] <= height[r] ? height[l++] : height[r--];
            lowerMax = Math.max(lowerMax,lower);
            water = water + (lowerMax - lower);
        }
        return water;
    }

    /*
    * 解法3:使用栈
    * */
    public int trap2(int[] height) {
        if (height == null || height.length == 0) return 0;
        if (height.length < 3) return 0;
        int water = 0,i = 0;
        Stack<Integer> stack = new Stack<>();
        while (i < height.length) {
            // 只有栈不为空,并且当前柱子高度 高于 栈顶柱子高度,才会形成低洼
            while (!stack.isEmpty() && height[i] > height[stack.peek()]){
                int preH = height[stack.pop()]; // 拿到当前栈顶的高度
                if (stack.isEmpty()) break; // 如果pop后,栈是空了,也是没法存水的
                int distance = i - stack.peek() - 1;
                // 虽然height[i]是大于preH的,但是也要看当前preh左边的高度,取最小的值
                int h = Math.min(height[i] - preH,height[stack.peek()] - preH);
                water = water + (h * distance);
            }
            stack.push(i);
            i++;
        }
        return water;
    }
}
