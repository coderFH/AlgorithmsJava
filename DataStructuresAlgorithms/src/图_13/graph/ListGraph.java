package 图_13.graph;

import 图_13.fh.MinHeap;
import 图_13.fh.UnionFind;
import java.util.*;

public class ListGraph<V,E> extends Graph<V,E> {
    // 存放所有定点的map,key是改点的value值,value是顶点对象
    private Map<V,Vertex<V,E>> vertices = new HashMap<>();

    // 存放该图的所有边
    private Set<Edge<V,E>> edges = new HashSet<>();

    // 边的比较器
    private Comparator<Edge<V,E>> edgeComparator = (Edge<V,E> e1,Edge<V,E> e2) -> {
        return weightManager.compare(e1.weight,e2.weight);
    };

    /*
    * 构造函数
    * */
    public ListGraph() {}
    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

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

    //---------- begin ------
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
    //---------- end ------

    @Override
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>(); // 用于存放遍历元素的数据
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        Map<Vertex<V,E>,Integer> ins = new HashMap<>();
        // 初始化(将度为0的节点都放入队列)
        vertices.forEach((V v,Vertex<V,E> vertex)->{
            int in = vertex.inEdges.size();
            if (in == 0) { // 如果入度是0,就入队
                queue.offer(vertex);
            } else { // 否则 就加入map中,key是顶点 value是该顶点入度的个数
                ins.put(vertex,in);
            }
        });
        while (!queue.isEmpty()) {
            Vertex<V,E> vertex = queue.poll(); // 出队
            list.add(vertex.value); // 取到的值放入到列表中

            for (Edge<V,E> edge : vertex.outEdges) { // 然后遍历这个节点的出度
                int toIn = ins.get(edge.to) - 1; // 通过边找到to的顶点,然后to顶点的所有入度个数-1
                if (toIn == 0) {// 当发现入度减为0了,就入队
                    queue.offer(edge.to);
                } else {
                    ins.put(edge.to,toIn);// 否则更新map,更新顶点的入度的个数
                }
            }
        }
        return list;
    }

    //---------- begin ------
    @Override
    public Set<EdgeInfo<V, E>> mst() {
        // 在这里可以决定是调用prim算法还是kruskal算法,去生成最小生成树
        return Math.random() > 0.5 ? prim() : kruskal();
    }
    /*
    * 最小生成树的prim算法
    * */
    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V,E>> it = vertices.values().iterator();// 从vertices的字典中取出所有的value值,也就是顶点,然后 通过迭代器拿出一个顶点
        if (!it.hasNext()) return null;// 如果没有next,直接返回
        Vertex<V,E> vertex = it.next();//  取出一个顶点

        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>(); // 存放顶点信息
        Set<Vertex<V,E>> addedVertices = new HashSet<>(); // 记录分割过的顶点
        addedVertices.add(vertex); // 分割过,就加入addedVertices

        MinHeap<Edge<V,E>> heap = new MinHeap<>(vertex.outEdges,edgeComparator);// 利用最小堆,找出权重最小的边

        int verticesSize = vertices.size();
        while (!heap.isEmpty() && addedVertices.size() < verticesSize) {// 最小堆不为空 && 记录分割顶点的个数 < 该图的顶点个数
            Edge<V,E> edge = heap.remove();// 从二叉堆中移除最小权值的这条边
            if (addedVertices.contains(edge.to)) continue;;
            edgeInfos.add(edge.info());
            addedVertices.add(edge.to);   // 标记已经分割
            heap.addAll(edge.to.outEdges);// 再把该边对应的to顶点的outEdges建堆
        }
        return edgeInfos;
    }
    /*
    * 最小生成树的kruskal算法
    * */
    private Set<EdgeInfo<V, E>> kruskal() {
        int edgeSize = vertices.size() -1;
        if (edgeSize == -1) return null; // 没有任何一个点,就返回
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);// 利用最小堆,找出权重最小的边
        UnionFind<Vertex<V, E>> uf = new UnionFind<>();

        vertices.forEach((V v,Vertex<V,E> vertex)->{
            uf.makeSet(vertex);
        });

        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V,E> edge = heap.remove();
            if (uf.isSame(edge.from,edge.to)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.from,edge.to);
        }
        return edgeInfos;
    }
    //---------- end ------

    //---------- begin ------
    @Override
    public Map<V, E> shortestPath(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V,E> selectPaths = new HashMap<>(); //  返回信息的map,key是顶点的value值,value是边的权值
        Map<Vertex<V,E>,E> paths = new HashMap<>(); // 临时的map,key是顶点节点,value是边的权值
        // 初始化paths
        for (Edge<V,E> edge : beginVertex.outEdges) {
            paths.put(edge.to,edge.weight);
        }

        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V,E>,E> minEntry = getMinPath(paths);
            // minVertex离开桌面
            Vertex<V,E> minVertex = minEntry.getKey(); // 拿到最小的顶点
            selectPaths.put(minVertex.value,minEntry.getValue()); // 也就是最小的顶点离开桌面,放入到selectPaths中
            paths.remove(minVertex);
            // 对它的minVertex的outEdges进行松弛操作
            for (Edge<V,E> edge : minVertex.outEdges) {
                // 如果edge.to已经离开桌面,就没必要进行松弛操作
                if (selectPaths.containsKey(edge.to.value)) continue;
                // 新的可选择的最短路径: beginVertex到edge.from的最短路径 + edge.weihgt
                E newWeight = weightManager.add(minEntry.getValue(),edge.weight);
                // 以前的最短路径: beginVertex 到 edge.to的最短路径
                E oldWeight = paths.get(edge.to);
                if (oldWeight == null || weightManager.compare(newWeight,oldWeight) < 0) {
                    paths.put(edge.to,newWeight);
                }
            }
        }
        selectPaths.remove(begin); // 无向图的情况下,防止B起来的时候,再把A扔进去
        return  selectPaths;
    }
    /**
     * 从paths中挑一个最小的路径出来
     * @param paths
     * @return
     */
    private Map.Entry<Vertex<V,E>,E> getMinPath(Map<Vertex<V,E>,E> paths) {
        Iterator<Map.Entry<Vertex<V,E>,E>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V,E>,E> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V,E>,E> entry = it.next();
            if (weightManager.compare(entry.getValue(),minEntry.getValue()) < 0) { // 权值进行比较
                minEntry = entry;
            }
        }
        return minEntry;
    }
    //---------- end ------

    @Override
    public Map<V, PathInfo<V, E>> shortestDetailPath(V begin) {
//        return dijkstra(begin);
        return bellmanFord(begin);
    }

    //---------- begin ------
    /**
     * 求最短路径---dijkstra算法(前提:不能存在负权边)
     * @param begin
     * @return
     */
    private Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V, PathInfo<V,E>> selectPaths = new HashMap<>();
        Map<Vertex<V,E>,PathInfo<V,E>> paths = new HashMap<>();
        paths.put(beginVertex,new PathInfo<>(weightManager.zero()));

        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V,E>,PathInfo<V,E>> minEntry = getMinPathDetail(paths);
            // minVertex离开桌面
            Vertex<V,E> minVertex = minEntry.getKey();
            PathInfo<V,E> minPath = minEntry.getValue();
            selectPaths.put(minVertex.value,minPath);
            paths.remove(minVertex);
            // 对它的minVertex的outEdges进行松弛操作
            for (Edge<V,E> edge : minVertex.outEdges) {
                // 如果edge.to已经离开桌面,就没必要进行松弛操作
                if (selectPaths.containsKey(edge.to.value)) continue;
                relaxForDijkstra(edge,minPath,paths);
            }
        }
        selectPaths.remove(begin);
        return selectPaths;
    }
    /**
     * 松弛
     * @param edge 需要进行松弛的边
     * @param fromPath edge的from的最短路径信息
     * @param paths 存放着其他点(对于dijkstra来说,就是没有离开桌面的点)的最短路径信息
     */
    private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        // 新的可选择的最路径: beginVertex到edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(fromPath.weight,edge.weight);

        // 以前的最短路径: beginVertex到edge.to的最短路径
        PathInfo<V,E> oldPath = paths.get(edge.to);
        if (oldPath != null && weightManager.compare(newWeight,oldPath.weight) >= 0) return;

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to,oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }
    private Map.Entry<Vertex<V, E>, PathInfo<V, E>> getMinPathDetail(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        Iterator<Map.Entry<Vertex<V,E>,PathInfo<V,E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V,E>,PathInfo<V,E>> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V,E>,PathInfo<V,E>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight,minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }
    //---------- end ------

    //---------- begin ------
    /**
     * 求最短路径---bellman-ford算法(存在负权边)
     * @param begin
     * @return
     */
    private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));

        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) { // v - 1 次
            for (Edge<V, E> edge : edges) {
                PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
                if (fromPath == null) continue;
                relax(edge, fromPath, selectedPaths);
            }
        }
        for (Edge<V, E> edge : edges) {
            PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
            if (fromPath == null) continue;
            if (relax(edge, fromPath, selectedPaths)) {
                System.out.println("有负权环");
                return null;
            }
        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }
    private boolean relax(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
        // 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(fromPath.weight, edge.weight);
        // 以前的最短路径：beginVertex到edge.to的最短路径
        PathInfo<V, E> oldPath = paths.get(edge.to.value);
        if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to.value, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());

        return true;
    }
    //---------- end ------


    //---------- begin ------
    @Override
    public  Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
        // 初始化
        for (Edge<V, E> edge : edges) {
            Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
            if (map == null) {
                map = new HashMap<>();
                paths.put(edge.from.value, map);
            }

            PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.to.value, pathInfo);
        }

        vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
            vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
                vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;

                    // v1 -> v2
                    PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
                    if (path12 == null) return;

                    // v2 -> v3
                    PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
                    if (path23 == null) return;

                    // v1 -> v3
                    PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);

                    E newWeight = weightManager.add(path12.weight, path23.weight);
                    if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;

                    if (path13 == null) {
                        path13 = new PathInfo<V, E>();
                        paths.get(v1).put(v3, path13);
                    } else {
                        path13.edgeInfos.clear();
                    }

                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });

        return paths;
    }
    private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> map = paths.get(from);
        return map == null ? null : map.get(to);
    }
    //---------- end ------


    /*
    * 打印图的信息
    * */
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
    private static class Vertex<V,E> {
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

        /*
        * 返回边的信息
        * */
        EdgeInfo<V,E> info() {
            return new EdgeInfo<>(from.value,to.value,weight);
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
