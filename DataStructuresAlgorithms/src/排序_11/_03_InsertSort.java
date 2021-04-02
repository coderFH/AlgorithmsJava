package 排序_11;

public class _03_InsertSort {
    /*
    * 正常的插入排序,跟逆序对的数量有关,如果完全是一个逆序的数组,那么他就会每次都比较后交换,时间复杂度也是最高的
    * 这也提出了一个优化思路,不用每次都交换
    * array[cur] < array[cur - 1] 保证了稳定性
    * */
    static void InsertSort(int[] array) {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            while (cur > 0 && array[cur] < array[cur - 1]) {
                int tmp = array[cur];
                array[cur] = array[cur-1];
                array[cur-1] = tmp;
                cur--;
            }
        }
    }

    /*
    * 优化:比而不换
    *   发现前边的元素比当前值大,前边元素后移,一直找到一个比他小的,然后把该元素赋值过去就行了
    * */
    static void InsertSort1(int[] array) {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            int element = array[cur];
            while (cur > 0 && element < array[cur - 1]) {
                array[cur] = array[cur - 1];
                cur--;
            }
            array[cur] = element;
        }
    }

    /*
     * 优化:利用二分查找优化
     * */
    static void InsertSort2(int[] array) {
        for (int begin = 1; begin < array.length; begin++) {
           int element = array[begin];
           int insertIndex = search(array,begin);
            for (int i = begin; i > insertIndex ; i--) {
                array[i] = array[i - 1];
            }
            array[insertIndex] = element;
        }
    }

    static int search(int[] array ,int index) {
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (array[index] < array[mid]) {
                end = mid;
            } else {
                begin = mid + 1; // 注意,是=的情况也走else,因为为了保证排序的稳定性
            }
        }
        return begin;
    }

    public static void main(String[] args) {
        int[] array = {1,2,3,4,2,5,4,3};
        InsertSort1(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+ "_");
        }
    }
}
