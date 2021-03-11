package 链表_02;

/*
* https://leetcode-cn.com/problems/palindrome-linked-list/
* */
public class _12_234_回文链表 extends _00_baseList {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        if (head.next.next == null) return head.val == head.next.val;

        ListNode midNode = mideNode(head);
        ListNode newHead = reversal(midNode.next);

        while (newHead != null) {
            if (head.val != newHead.val) return false;
            head = head.next;
            newHead = newHead.next;
        }
        return true;
    }

    public ListNode mideNode(ListNode head) {
        ListNode sp = head;
        ListNode fp = head;
        while (fp != null && fp.next != null) {
            sp = sp.next;
            fp = fp.next.next;
        }
        return sp;
    }

    public  ListNode reversal(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }
        return newHead;
    }
}
