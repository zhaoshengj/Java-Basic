package com.zsj.basic.leetcode.baselc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 初级算法
 */
public class baselc {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }
    public class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
   }

    //反转链表 (递归)
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode s = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return s;
    }
    //双指针
    public ListNode reverseList1(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode cur = null;
        ListNode pre = head;
        while (pre != null){
            ListNode node = pre.next;
            pre.next = cur;
            cur = pre;
            pre = node;
        }
        return cur;
    }

    //判断是否回文  (借助数组)
    public boolean isPalindrome(ListNode head) {
        List list = new ArrayList();
        while (head != null){
            list.add(head.val);
            head = head.next;
        }
        for(int i =0;i<=list.size()/2;i++){
            if((int)list.get(0) != (int)list.get(list.size()-1 - i)){
                return false;
            }
        }
        return true;

    }
    //判断 回文
    public boolean isPalindrome1(ListNode head) {
        if(head == null ){
            return true;
        }
        ListNode node = emdNode(head);
        ListNode revert = revert(node.next);

        ListNode fir = head;
        ListNode sec = revert;
        while (sec != null){
            if(fir.val != sec.val){
                return false;
            }
            fir = fir.next;
            sec = sec.next;
        }
        return true;
    }

    public ListNode revert(ListNode end){
        ListNode pre = null;
        ListNode cur = end;
        while (cur != null){
            ListNode node = cur.next;
            cur.next = pre;
            pre = cur;
            cur = node;
        }
        return pre;
    }
    public ListNode emdNode(ListNode node){
        ListNode fast = node;
        ListNode slow = node;
        while (fast.next != null && fast.next.next != null){
           fast = fast.next.next;
           slow = slow.next;
        }
        return slow;
    }


    public int maxDepth(TreeNode root) {
        if(root == null){
           return 0;
        }
        if(root.left == null && root.right == null){
            return  1;
        }
        int max = Math.max(maxDepth(root.left), maxDepth(root.right))+1;
        return max;
    }



    @Test
    public  void main() {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(2);
        node.next.next.next = new ListNode(2);
        node.next.next.next.next = new ListNode(1);
        /*
            ListNode listNode = reverseList1(node);
            while (listNode != null){
                System.out.println(listNode.val);
                listNode = listNode.next;
            }
        */
        boolean palindrome = isPalindrome1(node);
        System.out.println(palindrome);

    }
}
