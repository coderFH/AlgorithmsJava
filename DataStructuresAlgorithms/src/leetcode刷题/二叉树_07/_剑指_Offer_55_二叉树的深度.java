package leetcode刷题.二叉树_07;

import java.util.LinkedList;
import java.util.Queue;

/*
* 给定一个二叉树，找出其最大深度。
*
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class _剑指_Offer_55_二叉树的深度 extends _00_baseTree {

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        int levelSize = 1; // 存放每一层节点的数量
        int height = 0;    // 记录树的高度
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            levelSize -= 1;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) { // 意味着即将要访问下一层
                height++;
                levelSize = queue.size();
            }
        }
        return height;
    }

    public int maxDepth1(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth1(root.left);
        int right = maxDepth1(root.right);
        return Math.max(left,right) + 1;
    }
}
