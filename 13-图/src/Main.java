import graph.Graph;
import graph.ListGraph;

public class Main {
    public static void main(String[] args) {
//		test1();
		testDfs();
		System.out.println("------");
		testDfs1();
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
	}

	/**
	 * 利用Data构造复杂的数据 -----有向图
	 */
	private static Graph<Object, Double> directedGraph(Object[][] data) {
		Graph<Object, Double> graph = new ListGraph<>();
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
		Graph<Object, Double> graph = new ListGraph<>();
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
}
