package leetcode刷题.二叉树_07;

/*
* https://leetcode-cn.com/problems/largest-bst-subtree/
* */
public class _333_最大BST子树 extends _00_baseTree {
    public int largestBSTSubtree(TreeNode root) {
        return (root == null) ? 0 : getInfo(root).size;
    }

    /*
    * 返回以root为根节点的二叉树的最大BST子树信息
    * */
    private Info getInfo(TreeNode root) {
        if (root == null) return null;
        Info li = getInfo(root.left);  // 左子树的最大BST子树信息
        Info ri = getInfo(root.right); // 右子树的最大BST子树信息

        /*
        有4种情况，以root为根节点的二叉树就是一棵BST，最大BST子树就是其本身
        ① 以root为根节点的树就是一个bst
        li != null && ri != null
        && li.root == root.left && root.val > li.max
        && ri.root == root.right && root.val < ri.min

        ② 以root.left为根节点的树是一个bst
        li != null && ri == null
        && li.root == root.left && root.val > li.max

        ③ 以root.right为根节点的树是一个bst
        li == null && ri != null
        && ri.root == root.right && root.val < ri.min

        ④ 没有左右子树
        li == null && ri == null
         */
        int leftBstSize = -1,rightBstSize = -1,max = root.val,min = root.val;
        if (li == null) {
            leftBstSize = 0;
        } else if(li.root == root.left && root.val > li.max) {
            leftBstSize = li.size;
            min = li.min;
        }

        if (ri == null) {
            rightBstSize = 0;
        } else if(ri.root == root.right && root.val < ri.min) {
            rightBstSize = ri.size;
            max = ri.max;
        }
        if (leftBstSize >=0 && rightBstSize >=0) { // 如果能进if内,所以当前的这个root就是一个bst
            return new Info(root,1 + leftBstSize + rightBstSize,max,min);
        }

        // 能走到这里,说明以root为根节点的二叉树不是BST
        // 返回最大bst子树的节点数量较多的那个Info
        if (li != null && ri != null) return (li.size > ri.size) ? li : ri;

        // 最后,左子树或者右子树,有一个会为null,返回不为null的那个info
        return (li != null) ? li : ri;
    }

    private static class Info {
        public TreeNode root; // 根节点
        public int size;      // 节点总数
        public int max;       // 最大值
        public int min;       // 最小值
        public Info(TreeNode root,int size,int max,int min) {
            this.root = root;
            this.size = size;
            this.max = max;
            this.min = min;
        }
    }
}
