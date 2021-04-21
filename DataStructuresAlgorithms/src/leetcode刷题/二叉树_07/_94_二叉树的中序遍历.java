package leetcode刷题.二叉树_07;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
* https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
* */
public class _94_二叉树的中序遍历 extends _00_baseTree {

    /*
    * 1.递归实现
    * */
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

    /*
    * 2.迭代实现
    * */
    public List<Integer> inorderTraversal1(TreeNode root) {
        if (root == null) return new ArrayList<>();

        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else if (stack.isEmpty()) {
                break;
            } else {
                node = stack.pop();
                res.add(node.val); // 访问该节点
                node = node.right; // 处理右节点
            }
        }
        return res;
    }

    /*
    * 3.Morris遍历,可以实现空间复杂度O(1)
    * */
    public List<Integer> inorderTraversal2(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<Integer>();

        TreeNode node = root;
        while (node != null) {
            if (node.left != null) {
                // 找前驱节点
                TreeNode pred = node.left;
                while (pred.right != null && pred.right != node) { // 一路往右走,找前驱
                    pred = pred.right;
                }

                //能到这里,就找到了当前节点的一个前驱节点了
                if (pred.right == null) {
                    pred.right = node;
                    node = node.left;
                } else { // 走到这里,其实已经把当前节点的前驱节点的右子树指向了当前节点,需要打破指向关系,维护树的结构
                    res.add(node.val);
                    pred.right = null;
                    node = node.right;
                }
            } else {
                res.add(node.val);
                node = node.right;
            }
        }
        return res;
    }
}
