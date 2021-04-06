package leetcode刷题.高频题_08;

/*
* https://leetcode-cn.com/problems/container-with-most-water/
* */
public class _11_盛最多水的容器 {
    public int maxArea(int[] height) {
        /*
        * 思路: 暴力法,两重for循环,计算出没种情况下面积.然后取最大值
        * 然后这里的解题思路是:
        * 双指针,分别指向首元素和尾元素,然后取高度矮的那个,然后求出距离
        * 高度*距离后能求出一个面积
        * 然后后来依次比较
        * */
        if (height == null || height.length == 0) return 0;

        int water = 0;
        int l = 0;
        int r = height.length - 1;

        while (l < r) {
            int distance = r - l;
            if (height[l] < height[r]) {
                water = Math.max(water,height[l] * distance);
                l++;
            } else {
                water = Math.max(water,height[r] * distance);
                r--;
            }
        }
        return water;
    }

    /*
    * 优化点:当height[l]的值,已经明显比我之前minH还小,就没必要计算了,直接++
    * 同理,height[r]也是这样
    * */
    public int maxArea1(int[] height) {
        if (height == null || height.length == 0) return 0;

        int water = 0;
        int l = 0;
        int r = height.length - 1;

        while (l < r) {
            int distance = r - l;
            int minH = Math.min(height[r],height[l]);
            water = Math.max(water,minH * distance);
            while (l < r && height[l] <= minH) l++;
            while (l < r && height[r] <= minH) r--;
        }
        return water;
    }
}
