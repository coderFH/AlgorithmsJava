package union;

/**
 * Quick Union - 基于rank的优化 - 路径分裂(Path Spliting)
 *
 */
public class _07_UnionFind_QU_R_PS extends _05_UnionFind_QU_R {

	public _07_UnionFind_QU_R_PS(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) {
		rangeCheck(v);
		while (v != parents[v]) {
			int p = parents[v];
			parents[v] = parents[parents[v]];
			v = p;
		} 
		return v;
	}
}
