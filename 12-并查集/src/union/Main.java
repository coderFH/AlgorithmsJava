package union;

import tool.Asserts;
import tool.Times;

public class Main {
    static final int count = 100000;

    public static void main(String[] args) {
//        testTime(new _02_UnionFind_QF(count));
//        testTime(new _03_UnionFind_QU(count));
//        testTime(new _04_UnionFind_QU_S(count));
//        testTime(new _05_UnionFind_QU_R(count));
//        testTime(new _06_UnionFind_QU_R_PC(count));
//        testTime(new _07_UnionFind_QU_R_PS(count));
        testTime(new _08_UnionFind_QU_R_PH(count));

//        testGenericUnion(new _09_GenericUnionFind<Integer>());
    }

    // 测试只能放整数的并查集
    static void testTime(_01_UnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6,7);

        uf.union(8,10);
        uf.union(9,10);
        uf.union(9,11);

        Asserts.test(!uf.isSame(2,7));

        uf.union(4,6);

        Asserts.test(uf.isSame(2,7));

        // 测试各个并查集的效率
        Times.test(uf.getClass().getSimpleName(),()->{
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random() * count),(int)(Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random() * count),(int)(Math.random() * count));
            }
        });
    }

    // 测试通用并查集
    static void testGenericUnion(_09_GenericUnionFind<Integer> uf) {
        for (int i = 0; i < count; i++) {
            uf.makeSet(i);
        }
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);

        Asserts.test(!uf.isSame(2, 7));

        uf.union(4, 6);

        Asserts.test(uf.isSame(2, 7));

        Times.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }
        });
    }
}
