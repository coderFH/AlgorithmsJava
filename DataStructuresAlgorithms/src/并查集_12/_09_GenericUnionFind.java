package 并查集_12;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class _09_GenericUnionFind<V> {
	private Map<V,Node<V>> nodes = new HashMap<>();
	
	// 对 对象 元素进行初始化
	public void makeSet(V v) {
		if (nodes.containsKey(v)) return;
		nodes.put(v, new Node<>(v));
	}
	
	// 查找元素的根节点
	public V find(V v) {
		Node<V> node = findNode(v);
		return node == null ? null : node.value;
	}
	
	// 合并两个元素
	public void union(V v1, V v2) {
		Node<V> p1 = findNode(v1);
		Node<V> p2 = findNode(v2);
		if (p1 == null || p2 == null)return;
		if (Objects.equals(p1.value, p2.value)) return;
		
		if (p1.rank < p2.rank) {
			p1.parent = p2;
		} else if (p1.rank > p2.rank) {
			p2.parent = p1;
		} else {
			p1.parent = p2;
			p2.rank += 1;
		}
	}
	
	// 两个元素是否是在同一集合
	public boolean isSame(V v1,V v2) {
		return Objects.equals(find(v1), find(v2));
	}
	
	// 找出V的根节点
	private Node<V> findNode(V v) {
		Node<V> node = nodes.get(v);
		if (node == null) return null;
		while (!Objects.equals(node.value, node.parent.value)) {
			node.parent = node.parent.parent;
			node = node.parent;
		}
		return node;
	}
	
	// 内部类,表示一个节点
	private static class Node<V> {
		V value;
		Node<V> parent = this;
		int rank = 1;
		public Node(V value) {
			this.value = value;
		}
	}
}
