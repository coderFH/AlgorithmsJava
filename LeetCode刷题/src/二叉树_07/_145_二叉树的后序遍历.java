package 二叉树_07;
/*
* https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
* */

import java.util.ArrayList;
import java.util.List;

public class _145_二叉树的后序遍历 {
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

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        postorder(root,res);
        return res;
    }

    public void postorder(TreeNode root, List<Integer> res){
        if (root == null) return;
        postorder(root.left,res);
        postorder(root.right,res);
        res.add(root.val);
    }
}
