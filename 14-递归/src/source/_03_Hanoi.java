package source;

public class _03_Hanoi {
    /**
     * 将n个碟子从p1挪动到p3
     * @param n  盘子个数
     * @param p1 1号柱子
     * @param p2 2号柱子
     * @param p3 3号柱子
     */
    void hanoi(int n,String p1,String p2,String p3) {
        if (n == 1) {
            move(n,p1,p3);
            return;
        }
        //其实相当于把n-1个盘子看成一个整体,可以理解为n和n-1两个盘子,最上边那个盘子移动到2号柱子,
        //因为hanoi函数的作用是从1号挪到3号,所以调用2,3的顺序,就实现了把最顶层的那个移到2号柱子
        hanoi(n - 1,p1,p3,p2);
        move(n,p1,p3);  // 移动最底部的那个n到3号柱子
        hanoi(n - 1,p2,p1,p3); // 最后 把n-1 这个盘子 从2号柱子挪到3号柱子
    }

    /*
    * 将 no 号盘子 从 from移动到 to
    * */
    void move(int no,String from,String to) {
        System.out.println("将" + no + "号盘从" + from + "移动到" + to);
    }

    public static void main(String[] args) {
        new _03_Hanoi().hanoi(3,"A","B","C");
    }
}
