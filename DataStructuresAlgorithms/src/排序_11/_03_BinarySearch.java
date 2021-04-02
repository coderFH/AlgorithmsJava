package 排序_11;

public class _03_BinarySearch {
    /*
    * 注意begin和end的技巧:
    *   begin 从0开始没问题
    *   end   从最后一个元素的下标加+1开始,也就是array.length
    *   这样 我们求元素有多少个 直接 end - begin 就行
    *   0 1 2 3 4 5 6 7  mid = (8+0) / 2 = 4  整个数组的取值范围是[begin,end)即[0,8)
    *   0 1 2 3   5 6 7  所以如果是左边 就是 [begin,mid)即[0 - 4)      即 0,1,2,3
    *                    所以如果是右边 就是 [mid+1,end)即[4+1 - 8)    即 5,6,7
    *   2 4 6 8 10 12 14  length = 7 mid = (7+0) / 2 = 3
    *   2 4 6   10 12 14  所以如果是左边 就是  [0 - 3)      即 2 4 6
    *                     所以如果是右边 就是  [3+1 - 7)    即 10 12 14
    *
     * */
    static int Search(int[] array,int v) {
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v > array[mid]){
                begin = mid + 1;
            } else if (v < array[mid]) {
                end = mid;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {0,1,2,3,4,5,6,7,8,9,10};
        System.out.println(Search(array,1));
        System.out.println(Search(array,2));
        System.out.println(Search(array,5));
        System.out.println(Search(array,8));
        System.out.println(Search(array,10));
    }
}
