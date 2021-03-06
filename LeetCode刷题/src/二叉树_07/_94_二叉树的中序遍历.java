package 二叉树_07;

import java.util.ArrayList;
import java.util.List;

/*
* https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
* */
public class _94_二叉树的中序遍历 {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root,res);
        return res;
    }

    public void inorder(TreeNode root, List<Integer> res){
        if (root == null) return;
        inorder(root.left,res);
        res.add(root.val);
        inorder(root.right,res);
    }
}
