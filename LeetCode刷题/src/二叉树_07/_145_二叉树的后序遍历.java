package 二叉树_07;
/*
* https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
* */

import java.util.ArrayList;
import java.util.List;

public class _145_二叉树的后序遍历 extends _00_baseTree {

    /*
     * 1.递归实现
     * */
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

    /*
     * 2.迭代实现
     * */
}
