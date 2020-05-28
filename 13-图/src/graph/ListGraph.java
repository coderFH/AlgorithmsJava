package graph;

import java.util.*;

public class ListGraph<V,E> extends Graph<V,E> {
    // 存放所有定点的map,key是改点的value值,value是顶点对象
    private Map<V,Vertex<V,E>> vertices = new HashMap<>();

    // 存放该图的所有边
    private Set<Edge<V,E>> edges = new HashSet<>();

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v,new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from,to,null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        // 先判断from和to顶点是否存在
        // 看下from这个值,对应的顶点在不在
        Vertex<V,E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from,fromVertex);
        }
        // 看下to这个值,对应的顶点在不在
        Vertex<V,E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to,toVertex);
        }

        // 来到这里,说明两个顶点一定存在
        // 接下来添加边
        // 先看之前边存不存在(如何知道边是否存在? 看from这个顶点里outEdges里有没有这条边,或者看to顶点的inEdges有没有这条边)
        Edge<V,E> edge = new Edge<>(fromVertex,toVertex);
        edge.weight = weight; // 更新权重
//        if (fromVertex.outEdges.contains(edge)) { // 如果新建的这个边,在from顶点的outEdges存在(hash内部回去调用Edge类的equals方法)
//            fromVertex.outEdges.remove(edge);
//            toVertex.inEdges.remove(edge);
//        }
        // 上边的简化写法
        // 这里注意,这里删除的不是刚才新 new 的 edge,而是hashSet内部和这个edge相等的对象
        if (fromVertex.outEdges.remove(edge)) { // 如果 fromVertex.outEdges能删除成功,所有hashSet内部有相等的边
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

        // 添加新的edge
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertex(V v) {
        // 先判断顶点是否存在
        Vertex<V,E> vertex = vertices.get(v);
        if (vertex == null ) return;

        vertices.remove(v);

        // 之后就要删除该顶点下inEdges和outEdges里存的边
        // 并且需要删除该边的toVertex或者fromVertex中的inEdges和outEdges 所对应的边
        // 比如有一个v0->v4的图,要删除这个vo顶点,除了要清空v0的outEdges里的边,还要清除v4顶点中inEdges的边

        // Java使用迭代器的方式进行边遍历边删除(任何语言,边遍历边删除都是十分危险的操作,所以在java中使用迭代器的方式)
        for (Iterator<Edge<V,E>> iterator = vertex.outEdges.iterator();iterator.hasNext();) {
            Edge<V,E> edge = iterator.next();
            edge.to.inEdges.remove(edge); // 这一步就相当于删除上边例子中v4中inEdges的边
            //vertex.outEdges.remove(edge); // 然后从自己的顶点中删除 但是不能这么删,因为你在遍历这个集合,你还在同时删除,是很危险的
            iterator.remove(); //应该借助iterator进行删除
            edges.remove(edge);
        }

        for (Iterator<Edge<V,E>> iterator = vertex.inEdges.iterator();iterator.hasNext();) {
            Edge<V,E> edge = iterator.next();
            edge.from.outEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        // 先判断顶点是否存在
        Vertex<V,E> fromVertex = vertices.get(from);
        if (fromVertex == null ) return;

        Vertex<V,E> toVertex = vertices.get(to);
        if (toVertex == null) return;

        Edge<V,E> edge = new Edge<>(fromVertex,toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 用于存放遍历过的节点
        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        queue.offer(beginVertex); // 将最开始的节点放入队列
        visitedVertices.add(beginVertex); // 记录已经入队的元素

        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll(); // 出队
            if (visitor.visit(vertex.value)) return;

            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue; // 如果已经遍历过
                queue.offer(edge.to); // 入队
                visitedVertices.add(edge.to);
            }
        }
    }

    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        Stack<Vertex<V,E>> stack = new Stack<>();

        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(begin)) return;

        while (!stack.isEmpty()) {
            Vertex<V,E> vertex =  stack.pop();

            for (Edge<V,E> edge : vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;

                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                if (visitor.visit(edge.to.value)) return;
                break;
            }
        }
    }

    /*
    * 深度优先搜索递归实现---知道即可
    * */
    @Override
    public void dfs(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        dfs(beginVertex,new HashSet<>());
    }
    private void dfs(Vertex<V,E> vertex, Set<Vertex<V,E>> visitedVertices) {
        System.out.println(vertex.value); // 访问到一个元素
        visitedVertices.add(vertex);

        for (Edge<V,E> edge : vertex.outEdges) {
            if (visitedVertices.contains(edge.to)) continue;
            dfs(edge.to,visitedVertices);
        }
    }

    public void print() {
        System.out.println("[顶点]-------------------");
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
        });

        System.out.println("[边]-------------------");
        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }

    /*
    * 顶点
    * */
    private static class  Vertex<V,E> {
        V value;                                  // 定点的值
        Set<Edge<V,E>> inEdges = new HashSet<>(); // 从该顶点进去的边
        Set<Edge<V,E>> outEdges = new HashSet<>();// 从该顶点出去的边
        Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>)obj).value);
        }
        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    /*
    * 边
    * */
    private static class Edge<V,E> {
        Vertex<V,E> from;  // 从哪个定点出去
        Vertex<V,E> to;    // 到哪个定点
        E weight;          // 该边的权重

        Edge(Vertex<V,E> from, Vertex<V,E> to) {
            this.from = from;
            this.to = to;
        }

        // 因为在添加边的时候,会判断顶点的inEdges和outEdges里是否存在边,因为用的是hashmap,所以需要重写equals和hashCode方法
        @Override
        public boolean equals(Object obj) {
            Edge<V,E> edge = (Edge<V,E>)obj;
            return Objects.equals(from,edge.from) && Objects.equals(to,edge.to);// 起点和终点相同,就证明是同一条边
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
