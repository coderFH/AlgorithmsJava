package fh;

public class _01_BubbleSort {
    // 1. 正常的冒泡排序
    /*
    * 内层for循环是两两比较的逻辑,每经过一次for循环,最后的元素一定是最大值
    * 外层for循环控制比较的趟数,因为经过一次内层的for循环之后,最大值已经跑到了最后,即后边是有序的了,所以要end--
    * end>0的逻辑的end最小可以为1,此时内层比较的就是array[0]与array[1]进行比较
    * 内层for循环的条件是begin<=end,要包含最后一个元素,因为外层的end是array.length - 1
    * 关于稳定性?
    *   array[begin] < array[begin - 1]
    *   冒泡排序是稳定性排序,比较的值在 < 的时候,才发生交换,这样=的情况下是不交换的,保证稳定性
    *   但如果条件写成了 <= 就不稳定了
    * */
    static void BubbleSort(int[] array) {
        for (int end = array.length - 1; end > 0;end--) {
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                }
            }
        }
    }

    // 2. 对正常的冒泡排序进行优化
    /*
    * 优化的技术点:
    * 如果在某一次的内层循环中,都没有发生交换,表明此时其实已经有序了,就没必要继续for循环
    * */
    static void BubbleSort1(int[] array) {
        for (int end = array.length - 1; end > 0;end--) {
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    // 3. 进一步优化
    /*
    * 优化的技术点:
    * 如果在一个数组中,后边已经是有序的,只是前边部分元素无序,我们end的值其实就没有一定从最后一个元素开始
    * */
    static void BubbleSort2(int[] array) {
        for (int end = array.length - 1; end > 0;end--) {
            //sortIndex的初始值在数组完全有序的时候有用
            int sortIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sortIndex = begin; // 记录最后交换的位置下标,之后再遍历,他后边的就是有序的,就没有必要再遍历
                }
            }
            end = sortIndex;
        }
    }

    public static void main(String[] args) {
        int[] array = {9,8,7,6,3,4,2,1};
        BubbleSort2(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+ "_");
        }
    }
}
