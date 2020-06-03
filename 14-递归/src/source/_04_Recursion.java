package source;

import java.util.Stack;

/*
* 递归转非递归
* */
public class _04_Recursion {
    /*
    * 递归
    *   有可能导致栈溢出
    *   大量的重复计算,耗费性能
    * */
    void log(int n) {
        if (n < 1) return;
        log(n-1);
        int v  = n + 10;
        System.out.println(v);
    }

    /*
    * 递归转非递归
    *   自己维护一个栈,保存参数,局部变量
    *   但是空间复杂度依然没有得到优化
    * */
    void log1(int n) {
        Stack<Frame> frames = new Stack<>();
        while (n > 0) {
            frames.push(new Frame(n,n + 10));
            n--;
        }
        while (!frames.isEmpty()) {
            Frame frame = frames.pop();
            System.out.println(frame.v);
        }
    }
    static class Frame {
        int n;
        int v;
        Frame(int n,int v) {
            this.n = n;
            this.v = v;
        }
    }

    /*
    * 在某些时候,也可以重复使用一组相同的变量来保存每个栈帧的内容
    * */
    void log2(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i + 10);
        }
    }

    public static void main(String[] args) {
        _04_Recursion r = new _04_Recursion();
        r.log(4);
        System.out.println("-----");
        r.log1(4);
        System.out.println("-----");
        r.log2(4);
    }
}
