package 二叉树_07;

/*
* https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
* */
import java.util.ArrayList;
import java.util.List;

public class _144_二叉树的前序遍历 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preorder(root,res);
        return res;
    }

    public void preorder(TreeNode root,List<Integer> res){
        if (root == null) return;
        res.add(root.val);
        preorder(root.left,res);
        preorder(root.right,res);
    }
}
