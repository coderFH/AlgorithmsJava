package 数组排序_01;

import java.util.HashMap;

public class 数组中重复次数最多的数 {
    public static int repetMore(int[] nums) {
        if (nums == null) return 0;
        if (nums.length == 1) return nums[0];
        HashMap<Integer,Integer> map = new HashMap<>();
        int val = nums[0];
        map.put(nums[0],1);
        for (int i = 1; i < nums.length; i++) {
            int key = nums[i];

        }
        return val;
    }

    public static void main(String[] args) {
        数组中重复次数最多的数 o = new 数组中重复次数最多的数();
        o.repetMore(new int[]{1,1,2,3,3,3,3,3,6,7,1,2,3,2,2,2,2,2,2});
    }
}
