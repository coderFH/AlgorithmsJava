package leetcode刷题.高频题_08;

import java.util.Arrays;

/*
* https://leetcode-cn.com/problems/meeting-rooms/
* */
public class _252_会议室 {
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return true;

        Arrays.sort(intervals,(m1,m2)->m1[0] - m2[0]);

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i-1][1]) return false;
        }

        return true;
    }
}
