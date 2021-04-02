package 递归_14;

/**
 * 斐波那契数
 */
public class _01_Fib {
    public static void main(String[] args) {
        _01_Fib fib = new _01_Fib();
        int n = 50;

        Common.Times.test("fib0",()->{
            System.out.println(fib.fib0(n));
        });
        Common.Times.test("fib1",()->{
            System.out.println(fib.fib1(n));
        });
        Common.Times.test("fib2",()->{
            System.out.println(fib.fib2(n));
        });
        Common.Times.test("fib3",()->{
            System.out.println(fib.fib3(n));
        });
        Common.Times.test("fib4",()->{
            System.out.println(fib.fib4(n));
        });
    }

    /**
     * 用递归的思想去解决斐波那契数列
     * @param n
     * @return 结果
     */
    int fib0(int n) {
        if (n <= 2) return 1;
        return fib0(n-1) + fib0(n-2);
    }

    /*
    * 对第一种方式的优化,由于第一种方式,在递归调用过程中,有很多次重复的计算,可以考虑每计算一次后,就把结果存入数组,之后就直接从数组中取
    * */
    int fib1(int n) {
        if (n <= 2) return 1;
        int[] array = new int[n+1];
        array[1] = array[2] = 1;
        return fib1(n,array);
    }
    int fib1(int n,int[] arr) {
        if (arr[n] == 0) {
            arr[n] = fib1(n-1,arr) + fib1(n-2,arr);
        }
        return arr[n];
    }

    /*
    * 针对上边的优化,进一步优化成为非递归调用
    * */
    int fib2(int n) {
        if (n <= 2) return 1;
        int[] array = new int[n+1];
        array[1] = array[2] = 1;
        for (int i = 3;i <= n; i++) {
            array[i] = array[i-1] + array[i-2];
        }
        return array[n];
    }

    /*
    * 针对上面的数组进行优化,可以发现我们数组用到的永远只有两个元素,所以可以使用滑动数组
    * */
    int fib3(int n) {
        if (n <= 2) return 1;
        int[] array = new int[2]; // 只开辟两个数组空间
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i % 2] = array[(i-1) % 2] + array[(i-2) % 2];
        }
        return array[n % 2];
    }

    /*
    * 取余是很耗费性能的,我们可以进行位运算
    *
    * 对于一个数取余,其实等价于和1进行&运算,可以看下边的推算
    *
    * 4 % 2 = 0  0b100 & 0b001 = 0
    * 3 % 2 = 1  0b011 & 0b001 = 1
    * 5 % 2 = 1  0b101 & 0b001 = 1
    * 6 % 2 = 0  0b110 & 0b001 = 0
    * */
    int fib4(int n) {
        if (n <= 2) return 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
        }
        return array[n & 1];
    }
}


