package 图_13.fh;

import 图_13.graph.Graph;
import 图_13.graph.ListGraph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
	// 创建边权值管理者
	static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
		@Override
		public int compare(Double w1, Double w2) {
			return w1.compareTo(w2);
		}

		@Override
		public Double add(Double w1, Double w2) {
			return w1 + w2;
		}

		@Override
		public Double zero() {
			return 0.0;
		}
	};

    public static void main(String[] args) {
		testShortPathFloyd();
    }

    /*
    * 自己构造的简单数据 -----测试无向图
    * */
    static void test() {
    	ListGraph<String, Integer> graph = new ListGraph<>();
		graph.addEdge("V0", "V1");
		graph.addEdge("V1", "V0");

		graph.addEdge("V0", "V2");
		graph.addEdge("V2", "V0");

		graph.addEdge("V0", "V3");
		graph.addEdge("V3", "V0");

		graph.addEdge("V1", "V2");
		graph.addEdge("V2", "V1");

		graph.addEdge("V2", "V3");
		graph.addEdge("V3", "V2");

		graph.print();
	}

	/*
	* 自己构造的简单数据 -----测试有向图
	* */
	static void  test1() {
		ListGraph<String,Integer> graph = new ListGraph<>();
		graph.addEdge("V1", "V0", 9);
		graph.addEdge("V1", "V2", 3);
		graph.addEdge("V2", "V0", 2);
		graph.addEdge("V2", "V3", 5);
		graph.addEdge("V3", "V4", 1);
		graph.addEdge("V0", "V4", 6);

//		graph.removeEdge("V0","V4");
//		graph.removeVertex("V0");
//		graph.print();

		// 广度优先遍历
		graph.bfs("V1", new Graph.VertexVisitor<String>() {
			@Override
			public boolean visit(String s) {
				System.out.println(s);
				return false;
			}
		});

		// 如果接口里只有一个方法,Java允许这么写,类似于匿名函数(lambda表达式)
		graph.bfs("V1",(String s)->{
			System.out.println(s);
			return false;
		});
	}

	/**
	 * 利用Data构造复杂的数据 -----有向图
	 */
	private static Graph<Object, Double> directedGraph(Object[][] data) {
		Graph<Object, Double> graph = new ListGraph<>(weightManager);
		for (Object[] edge : data) {
			if (edge.length == 1) { // 添加一个点(是孤独的)
				graph.addVertex(edge[0]);
			} else if (edge.length == 2) { // 添加一个有起点到终点的边
				graph.addEdge(edge[0], edge[1]);
			} else if (edge.length == 3) { // 添加带有权值的边
				// 拿到权值的值后,不管你是整形还是double,先转成字符串然后转成double
				double weight = Double.parseDouble(edge[2].toString());
				graph.addEdge(edge[0], edge[1], weight);
			}
		}
		return graph;
	}

	/**
	 * 利用Data构造复杂的数据 -----无向图
	 */
	private static Graph<Object, Double> undirectedGraph(Object[][] data) {
		Graph<Object, Double> graph = new ListGraph<>(weightManager);
		for (Object[] edge : data) {
			if (edge.length == 1) {
				graph.addVertex(edge[0]);
			} else if (edge.length == 2) {
				graph.addEdge(edge[0], edge[1]);
				graph.addEdge(edge[1], edge[0]);
			} else if (edge.length == 3) {
				double weight = Double.parseDouble(edge[2].toString());
				graph.addEdge(edge[0], edge[1], weight);
				graph.addEdge(edge[1], edge[0], weight);
			}
		}
		return graph;
	}

	/*
	* 广度优先搜索测试
	* */
	static void testBfs() {
		Graph<Object, Double> graph = directedGraph(Data.BFS_02);
		graph.bfs(0, (Object v) -> {
			System.out.println(v);
			return false;
		});
	}

	/*
	* 深度优先搜索测试 -递归实现
	* */
	static void testDfs() {
		Graph<Object, Double> graph = undirectedGraph(Data.DFS_01);
		graph.dfs(1);

		Graph<Object, Double> graph1 = directedGraph(Data.DFS_02);
		graph1.dfs("a");
	}

	/*
	 * 深度优先搜索测试 -迭代实现
	 * */
	static void testDfs1() {
		Graph<Object, Double> graph = undirectedGraph(Data.DFS_01);
		graph.dfs(1, new Graph.VertexVisitor<Object>() {
			@Override
			public boolean visit(Object o) {
				System.out.println(o);
				return false;
			}
		});

		Graph<Object, Double> graph1 = directedGraph(Data.DFS_02);
		graph1.dfs("a", new Graph.VertexVisitor<Object>() {
			@Override
			public boolean visit(Object o) {
				System.out.println(o);
				return false;
			}
		});
	}

	/*
	* 测试拓扑排序
	* */
	static void testTopo() {
		Graph<Object, Double> graph = directedGraph(Data.TOPO);
		List<Object> list = graph.topologicalSort();
		System.out.println(list);
	}

	/*
	 * 测试最小生成树
	 * */
	static void testmst() {
		Graph<Object, Double> graph = undirectedGraph(Data.MST_01);
		Set<Graph.EdgeInfo<Object, Double>> infos = graph.mst();
		for (Graph.EdgeInfo<Object, Double> info : infos) {
			System.out.println(info);
		}

		System.out.println("------");

		Graph<Object, Double> graph1 = undirectedGraph(Data.MST_02);
		Set<Graph.EdgeInfo<Object, Double>> infos1 = graph1.mst();
		for (Graph.EdgeInfo<Object, Double> info : infos1) {
			System.out.println(info);
		}
	}

	/*
	* 测试最短路径
	* */
	static void testShortPath() {
//		Graph<Object, Double> graph = undirectedGraph(Data.SP);
		Graph<Object, Double> graph = directedGraph(Data.SP);
		Map<Object,Double> sp = graph.shortestPath("A");
		System.out.println(sp);
	}

	/*
	 * 测试最短路径,使用打印路径信息的版本
	 * */
	static void testShortPathDetail() {
		Graph<Object, Double> graph = directedGraph(Data.SP);
//		Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
		Map<Object, Graph.PathInfo<Object,Double>> sp = graph.shortestDetailPath("A");
		if (sp == null) return;
		sp.forEach((Object v, Graph.PathInfo<Object,Double> path) -> {
			System.out.println(v + " - " + path);
		});
	}

	/*
	 * 测试最短路径,floyd算法,多源最短路径算法
	 * */
	static void testShortPathFloyd() {
//		Graph<Object, Double> graph = directedGraph(Data.SP);
		Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
		Map<Object, Map<Object, Graph.PathInfo<Object,Double>>> sp = graph.shortestPath();
		if (sp == null) return;
		sp.forEach((Object from, Map<Object, Graph.PathInfo<Object,Double>> paths) -> {
			System.out.println(from + " ------ ");
			paths.forEach((Object to, Graph.PathInfo<Object,Double> path) ->{
				System.out.println(to + "-" + path);
			});
		});
	}
}
