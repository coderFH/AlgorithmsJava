package 并查集_12;

/**
 * Quick Union - 基于rank的优化 - 路径减半(Path Halving)
 *
 */

public class _08_UnionFind_QU_R_PH extends _05_UnionFind_QU_R {

	public _08_UnionFind_QU_R_PH(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) {
		rangeCheck(v);
		while (v != parents[v]) {
			parents[v] = parents[parents[v]];
			v = parents[v];
		}
		return v;
	}
}
