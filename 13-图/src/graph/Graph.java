package graph;

import java.util.List;
import java.util.Set;

public abstract class Graph<V,E> {
    // 权重管理者
    protected WeightManager<E> weightManager;

    /*
    * 无参的构造函数
    * */
    public Graph(){}

    /*
    * 传有权重管理对象的构造函数
    * */
    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }

    /*
    * 顶点的个数
    * */
    public abstract int verticesSize();

    /*
    * 边的个数
    * */
    public abstract int edgesSize();

    /*
    * 添加一个顶点
    * */
    public abstract void addVertex(V v);

    /*
    * 添加一个边
    * */
    public abstract void addEdge(V from,V to);

    /*
    * 添加一个带有权重的边
    * */
    public abstract void addEdge(V from,V to,E weight);

    /*
    * 删除一个顶点
    * */
    public abstract void removeVertex(V v);

    /*
    * 删除一个边
    * */
    public abstract void removeEdge(V from, V to);

    /*
    * 广度优先搜索
    * */
    public abstract void bfs(V begin, VertexVisitor<V> visitor);

    /*
     * 深度优先搜索
     * */
    public abstract void dfs(V begin); // 递归实现---知道即可
    public abstract void dfs(V begin, VertexVisitor<V> visitor); // 迭代实现

    /*
    * 拓扑排序
    * */
    public abstract List<V> topologicalSort();

    /**
     * 最小生成树
     * 最小生成树的前提是有权的无向图
     * @return 返回边信息
     */
    public abstract Set<EdgeInfo<V,E>> mst();

    /*
    * 遍历接口
    * */
    public interface VertexVisitor<V> {
        boolean visit(V v);
    }

    /**
     * 最小生成树 使用的边的信息类
     */
    public static class EdgeInfo<V,E> {
        private V from;   // 边的起点
        private V to;     // 边的终点
        private E weight; // 边的权重
        public EdgeInfo(V from,V to,E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public void setFrom(V from) {
            this.from = from;
        }
        public V getFrom() {
            return from;
        }

        public void setTo(V to) {
            this.to = to;
        }
        public V getTo() {
            return to;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }
        public E getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

    /*
    * 边的权重管理者
    * */
    public interface WeightManager<E> {
        int compare(E w1,E w2); //权重比较
        E add(E w1,E w2);       // 权重相加
        E zero();               // 权重清零
    }
}
