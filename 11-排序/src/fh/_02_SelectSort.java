package fh;

public class _02_SelectSort {
    /*
     * 选择排序的思路?
     *      每次遍历都取出最大值,然后和最后一位进行交换,这样一遍循环之后,最大值就在最后的位置了
     * 关于稳定性?
     *      选择排序是不稳定的排序,无法通过 <= 或者 < 这种条件,类似于冒泡排序去控制稳定性
     *      因为例如 1,2,10,3,10,3 这种数组
     *      第一次取出最大值10,然后和最后一位交换 就是1,2,3,3,10,10
     *      在这一步后边的3就跑到了第一个3的前边了,这样已经不稳定了
     * */
    static void SelectSort(int[] array) {
        for (int end = array.length - 1; end > 0;end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (array[maxIndex] < array[begin]) {
                   maxIndex = begin;
                }
            }
            int tmp = array[end];
            array[end] = array[maxIndex];
            array[maxIndex] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] array = {9,8,7,6,3,4,2,1};
        SelectSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+ "_");
        }
    }
}
