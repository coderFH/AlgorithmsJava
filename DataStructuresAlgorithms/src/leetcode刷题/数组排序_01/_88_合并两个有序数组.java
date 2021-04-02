package leetcode刷题.数组排序_01;

/*
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/merge-sorted-array
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*/
public class _88_合并两个有序数组 {
    /*
     * 思路:
     * 先定义两个index指向数组元素有效数组的最后,然后分别的往前依次比较
     * 既然是有序数组,就从后往前遍历,这样处理合并会更方便 注意nums1的下标越界情况
     * */
    static public void merge(int[] nums1, int m, int[] nums2, int n) {
        int firstInx = m - 1;
        int secondIndx = n - 1;
        int lastIdx = nums1.length - 1;
        while (secondIndx >= 0) {
            if (firstInx >= 0 && nums1[firstInx] > nums2[secondIndx]) {
                nums1[lastIdx--] = nums1[firstInx--];
            } else {
                nums1[lastIdx--] = nums2[secondIndx--];
            }
        }
    }

    public static void main(String[] args) {
        int[] num1 = new int[]{1,2,3,0,0,0};
        int[] num2 = new int[]{2,5,6};
        merge(num1,3,num2,3);
    }
}
