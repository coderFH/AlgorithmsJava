package 贪心_16;

import java.util.Arrays;

public class _02_CoinChange {
    public static void main(String[] args) {
        System.out.println("-----");
        coinChange1();
        System.out.println("-----");
        coinChange2();
        System.out.println("-----");
        coinChange3(new Integer[] {25, 20, 5, 1}, 41);
    }

    /*
    *  假设有25分.10分,5分,1分的硬币,先要找给客户41分的零钱,如何办到硬币个数最少?
    *  25 10 5 1 各一枚
    * */
    static void coinChange1() {
        int[] faces = {25,5,10,1};
        Arrays.sort(faces);
        int money = 41,coins = 0;
        for (int i = faces.length - 1; i >= 0 ; i--) {
            if (money < faces[i]) {
                continue;
            }
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
            i = faces.length;
        }
        System.out.println(coins);
    }

    /*
    * 另一种解决思路
    * */
    static void coinChange2() {
        int[] faces = {25,5,10,1};
        int money = 41;
        Arrays.sort(faces);
        int coins = 0, idx = faces.length - 1;
        while (idx >= 0) {
            while (money >= faces[idx]){
                System.out.println(faces[idx]);
                money -= faces[idx];
                coins++;
            }
            idx--;
        }
        System.out.println(coins);
    }

    /*
    * 假设有25分,20分,5分,1分的硬币,先要找给客户41分的零钱,如何办到硬币个数最少?
    * 按照贪心策略 最终结果会是 25分1枚,5分3枚,1枚1分 共5枚
    * 但实际最优解是 20分2枚 1枚1分 共3枚
    * 可见,贪心策略并不会保证出现最优解,只注重眼前利益最大化,看不到长远未来
    * 这类问题可以用以后的动态规划去解决
    * */
    static void coinChange3(Integer[] faces, int money) {
        Arrays.sort(faces,(Integer f1,Integer f2) -> f2 - f1);
        int coins = 0, i = 0;
        while (i < faces.length) {
            if (money < faces[i]) {
                i++;
                continue;
            }
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
        }
        System.out.println(coins);
    }



}
