package fh;

public class _04_MergeSort {
    private static int[] leftArray;

    static void mergeSort(int[] array) {
        leftArray = new int[array.length >> 1];
        sort(array,0,array.length);
    }

    static void sort(int[] array,int begin,int end) {
        if (end - begin < 2) return;

        int mid = (begin + end) >> 1;
        sort(array,begin,mid);
        sort(array,mid,end);
        merge(array,begin,mid,end);
    }

    static void merge(int[] array,int begin,int mid,int end) {
        int li = 0,le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;

        // 备份左边数组
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin + i];
        }

        while (li < le) { // 如果左边还没有结束
            if (ri < re && array[ri] < leftArray[li]) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = leftArray[li++];
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {9,8,7,6,3,4,2,1};
        mergeSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+ "_");
        }
    }
}
