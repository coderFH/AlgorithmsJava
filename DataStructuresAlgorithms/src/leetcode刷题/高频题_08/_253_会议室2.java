package leetcode刷题.高频题_08;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
* https://leetcode-cn.com/problems/meeting-rooms-ii/
* */
public class _253_会议室2 {
    /*
    * 解法1: 利用最小堆的思想,把会议的结束时间入堆,因为是最小堆
    * 所以顶部的数值一定是最早结束的会议
    * 每遍历到一个会议,就拿当前会议的开始时间和最小堆中的最早结束时间做比较
    * 如果时间比他大,则不需要新开一个会议室,直接移除对顶值就行,否则说明需要新开会议室,把结束时间入堆
    * 同时要记得把该会议的结束时间入最小堆
    * */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        //按照会议的开始时间，从小到大排序  nlogn
        Arrays.sort(intervals,(m1,m2)->m1[0]-m2[0]);

        // 创建一个最小堆（存放每一个会议的结束时间）
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        // 添加0号会议的结束时间
        heap.add(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= heap.peek()) { // 如果当前会议的开始时间比最小堆中会议的结束大,说明不需要新开一个会议室
                // 说明有会议室空出来
                heap.remove();
            }
            // 将i号会议的结束时间加入堆中
            heap.add(intervals[i][1]);
        }
        return heap.size();
    }

    /**/
    public int minMeetingRooms1(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        // 存放所有会议的开始时间
        int[] begins = new int[intervals.length];
        // 存放所有会议的结束时间
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            begins[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        // 排序
        Arrays.sort(begins);
        Arrays.sort(ends);

        int room = 0, endIdx = 0;
        for (int begin : begins) {
            if (begin >= ends[endIdx]) { // 能重复利用会议室
                endIdx++;
            } else { // 需要新开一个会议室
                room++;
            }
        }
        return room;
    }
}
