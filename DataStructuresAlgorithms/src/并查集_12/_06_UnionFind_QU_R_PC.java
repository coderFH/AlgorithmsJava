package 并查集_12;

/**
 * Quick Union - 基于rank的优化 - 路径压缩(Path Compression)
 *
 */
public class _06_UnionFind_QU_R_PC extends _05_UnionFind_QU_R {

	public _06_UnionFind_QU_R_PC(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) { //3
		rangeCheck(v);
		if (parents[v] != v) {
			parents[v] = find(parents[v]);
		}
		return parents[v];
	}
}
