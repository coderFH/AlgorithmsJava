package leetcode刷题.二叉树_07;

/*
* https://leetcode-cn.com/problems/recover-binary-search-tree/
* */
public class _99_恢复二叉搜索树 extends _00_baseTree {
    private TreeNode prev;
    private TreeNode first;
    private TreeNode second;

    /*
    * 解法一:使用正常的递归进行中序遍历,然后发现逆序对之后,交换value的值
    * */
    public void recoverTree(TreeNode root) {
        infixOrder(root);
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }
    private void infixOrder(TreeNode node) {
        if (node == null) return;
        infixOrder(node.left);
        // 出现了逆序对
        if (prev != null && prev.val > node.val) {
            // 第二个错误节点:最后一个逆序对中较小的那个节点
            second = node;

            // 第一个错误节点:第一个逆序对中较大的那个节点
            if (first != null) return;
            first = prev;
        }
        prev = node;
        infixOrder(node.right);
    }

    /*
    * 解法二:使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用常数空间的解决方案
    * 采用Morris中序遍历
    * */
    public void recoverTree1(TreeNode root) {
        TreeNode node = root;
        while (node != null) {
            if (node.left != null) {
                TreeNode prev = node.left;
                while (prev.right != null && prev.right != node) {
                    prev = prev.right;
                }
                if (prev.right == null) {
                    prev.right = node;
                    node = node.left;
                } else { // pred.right == node
                    find(node);
                    prev.right = null;
                    node = node.right;
                }
            } else {
                find(node);
                node = node.right;
            }
        }
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }
    private void find(TreeNode node) {
        // 出现了逆序对
        if (prev != null && prev.val > node.val) {
            // 第二个错误节点:最后一个逆序对中较小的那个节点
            second = node;

            // 第一个错误节点:第一个逆序对中较大的那个节点
            if (first != null) return;
            first = prev;
        }
        prev = node;
    }
}
