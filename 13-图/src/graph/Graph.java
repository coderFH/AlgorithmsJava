package graph;

public abstract class Graph<V,E> {
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
    public abstract void dfs(V begin); // 递归实现
    public abstract void dfs(V begin, VertexVisitor<V> visitor);


    /*
    * 遍历接口
    * */
    public interface VertexVisitor<V> {
        boolean visit(V v);
    }
}
