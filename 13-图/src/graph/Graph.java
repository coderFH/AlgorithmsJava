package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    * 拓扑排序-按照一个的依赖进行排序
    * */
    public abstract List<V> topologicalSort();

    /**
     * 最小生成树
     * 最小生成树的前提是有权的无向图
     * @return 返回边信息
     */
    public abstract Set<EdgeInfo<V,E>> mst();

    /**
     *
     * 最短路径(从一个点出发,到其他任意一个点的最短路径),单源路径算法
     * @param 起点
     * @return 字典  key:最终要到达点的顶点值   value: 起点到顶点的权重值
     * {B=10.0, C=50.0, D=30.0, E=60.0}
     * 即 A 到 E 的最短路径值是 60 具体看Dijkstra-执行过程的ppt
     */
    public abstract Map<V,E> shortestPath(V begin);

    /*
    * 最短路径---- 单源路径算法
    * 上边那个求最短路径的方法不够全面,只有起点到达某个顶点的权重信息,没有路径信息
    * 该方法会即返回路径信息,也返回权值信息
    * */
    public abstract Map<V,PathInfo<V,E>> shortestDetailPath(V begin);

    /*
    * 最短路径(任意顶点到其他顶点的最短路径),即多源路径算法
    * floyd算法
    * */
    public abstract Map<V, Map<V, PathInfo<V, E>>> shortestPath();

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

    /*
    * 最短路径需要的边的信息以及权重
    * eg: A到E的最短路径信息以及权重 A->D->E 60
    * */
    public static class PathInfo<V, E> {
        protected E weight; // 该路径的权值
        protected List<EdgeInfo<V, E>> edgeInfos = new LinkedList<>(); // 该路径包含哪些边

        public PathInfo() {}
        public PathInfo(E weight) {
            this.weight = weight;
        }

        public E getWeight() {
            return weight;
        }
        public void setWeight(E weight) {
            this.weight = weight;
        }

        public List<EdgeInfo<V, E>> getEdgeInfos() {
            return edgeInfos;
        }
        public void setEdgeInfos(List<EdgeInfo<V, E>> edgeInfos) {
            this.edgeInfos = edgeInfos;
        }

        @Override
        public String toString() {
            return "PathInfo [weight=" + weight + ", edgeInfos=" + edgeInfos + "]";
        }
    }
}
