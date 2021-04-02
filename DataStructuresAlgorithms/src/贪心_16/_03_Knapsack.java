package 贪心_16;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class _03_Knapsack {
    public static class Article {
        public int weight;
        public int value;
        public  double valueDensity;

        public Article(int weight,int value) {
            this.weight = weight;
            this.value = value;
            valueDensity = value * 1.0 / weight;
        }

        @Override
        public String toString() {
            return "Article{" +
                    "weight=" + weight +
                    ", value=" + value +
                    ", valueDensity=" + valueDensity +
                    '}';
        }
    }

    public static void main(String[] args) {
        select("价值主导",(Article a1, Article a2) -> {
            return a2.value - a1.value;
        });

        select("重量主导",(Article a1, Article a2) -> {
            return a1.value - a2.value;
        });

        select("价值密度主导",(Article a1, Article a2) -> {
            return Double.compare(a2.valueDensity,a1.valueDensity);
        });
    }

    static void select(String title, Comparator<Article> cmp) {
        Article[] articles = new Article[] {
                new Article(35, 10), new Article(30, 40),
                new Article(60, 30), new Article(50, 50),
                new Article(40, 35), new Article(10, 40),
                new Article(25, 30)
        };
        Arrays.sort(articles,cmp);

        int capacity = 150,weight = 0,value = 0;
        List<Article> selectedArticles = new LinkedList<>();
        for (int i = 0; i < articles.length && weight < capacity; i++) {
            int newWeight = weight + articles[i].weight;
            if (newWeight <= capacity) {
                weight = newWeight;
                value += articles[i].value;
                selectedArticles.add(articles[i]);
            }
        }

        System.out.println("【" + title + "】");
        System.out.println("总价值：" + value);
        for (int i = 0; i < selectedArticles.size(); i++) {
            System.out.println(selectedArticles.get(i));
        }
        System.out.println("-----------------------------");
    }
}

