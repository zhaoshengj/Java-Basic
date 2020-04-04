package com.zsj.basic.leetcode.msbd;

import org.junit.Test;

/**
 *  Java 面试算法宝典 （链表）
 */
public class msdb_list {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    //1.1 如何实现链表的逆序
    //递归
    public ListNode revert(ListNode node){
        if(node == null){
            return node;
        }
        if(node.next == null){
            return node;
        }
        ListNode revert = revert(node.next);
        node.next.next = node;
        node.next = null;
        return revert;
    }
    //双指针(两个指针移动)
    public ListNode revert1(ListNode node){
        if(node == null){
            return node;
        }
        ListNode cur = null;
        ListNode pre = node;
       while (pre != null){
           ListNode next = pre.next;
           pre.next = cur;
           cur = pre;
           pre = next;

       }
        return cur;
    }

    //1.2 如何从无序链表中移除重复项
    //顺序删除
    public ListNode  delete(ListNode node){
        if(node == null || node.next == null) return node;
        ListNode outerCur = node.next;
        ListNode innerCur = null;
        ListNode innerPre = null;
        for(;outerCur!= null;outerCur = outerCur.next){
            for(innerCur = outerCur.next,innerPre = outerCur;innerCur!=null;){

                if(outerCur.val == innerCur.val){
                    innerPre.next = innerCur.next;
                    innerCur = innerCur.next;
                }else {
                    innerPre = innerCur;
                    innerCur = innerCur.next;
                }

            }

        }

        return node;
    }


   // 1.3 如何计算两个单链表所代表的数之和
    public ListNode add(ListNode a,ListNode b){
        if( a == null) return b;
        if(b == null) return a;


        int s = 0; //进位
        ListNode node = new ListNode(0); //和链表
        ListNode cur = node;
        while (a != null && b != null){
            int sum = (a.val + b.val + s) % 10; //和
            s = (a.val + b.val+s)/10;//进位

            cur.next = new ListNode(sum);
            cur = cur.next;
            a = a.next;
            b = b.next;
        }
        if(a != null){
            while (a != null){
                int sum = (a.val + s) % 10;
                s = (a.val+s)/10;
                cur.next = new ListNode(sum);
                cur = cur.next;
                a = a.next;
            }
        }
        if(b != null){
            while (b != null){
                int sum = (b.val + s) % 10;
                s = (b.val+s)/10;
                cur.next = new ListNode(sum);
                cur = cur.next;
                b = b.next;
            }
        }
        if(s > 0){
            cur.next = new ListNode(s);
        }
        return node.next;
    }
    //1.4 如何对链表进行重新排序
    public ListNode reorderList(ListNode node){
        if(node == null || node.next == null){
            return node;
        }
        //找出中间节点
        ListNode middle = middle(node);
        //反转
        ListNode next = middle.next;
        middle.next = null;
        ListNode revert = revertLNode(next);

        ListNode merge = merge(node, revert);
        return merge;

    }
    //中间节点
    public ListNode middle(ListNode node){
        if(node == null || node.next == null){
            return node;
        }
        ListNode slow = node;
        ListNode fast = node;
        while (fast !=null &&fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    //反转链表
    public ListNode revertLNode(ListNode node){
        if(node == null || node.next == null){
            return node;
        }
        ListNode cur = null;
        ListNode pre = node;
        while (pre != null){
            ListNode next = pre.next;
            pre.next = cur;
            cur = pre;
            pre = next;
        }
        return cur;
    }
    //合并两个链表
    public ListNode merge(ListNode a,ListNode b){
        if(a == null)return b;
        if(b==null) return a;

        ListNode node = a;
        ListNode aTmp;
        ListNode bTmp;
        while ( a.next !=null && b !=null){
            //取出两个节点
            aTmp = a.next;
            bTmp = b.next;

            //交换值
            a.next = b;
            b.next = aTmp;

            //移动位置
            b = bTmp;
            a = aTmp;
        }

        return node;
    }

    //1.5 如何找出单链表中的倒数第k个元素
    public ListNode k(ListNode head,int k){
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null){
            if(k > 0){
                fast = fast.next;
                k--;
            }else {
                fast = fast.next;
                slow = slow.next;
            }
        }
        return slow;
    }
    //1.6 如何检测一个较大的单链表是否有环
    public Boolean s(ListNode head){
        if(head == null || head.next == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && slow != null){
            fast = fast.next.next;
            if(fast == null){
                return false;
            }
            slow = slow.next;
            if(fast == slow){
                return true;
            }
        }
        return false;
    }
    //1.7 如何把链表相邻元素翻转
    public ListNode revertNext(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode node = new ListNode(0);
        node.next = head;

        ListNode pre = node;
        ListNode cur = node.next;
        while (cur != null && cur.next != null){
            ListNode tmp = cur.next.next;
            pre.next = cur.next;

            cur.next.next = cur;
            cur.next = tmp;

            pre = cur;
            cur = tmp;

        }
        return node.next;
    }
    //1.8 如何把链表以K个结点为一组进行翻转

    //1.9 如何合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1,ListNode l2){
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if(l1.val <= l2.val){
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        }
        l2.next = mergeTwoLists(l1,l2.next);
            return l2;
    }
    public ListNode mergeTwoLists1(ListNode l1,ListNode l2){
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode node = new ListNode(0);
        ListNode tmp = node;
        while (l1 != null && l2 != null){
            if(l1.val > l2.val){
                tmp.next = l2;
                l2 = l2.next;
            }else {
                tmp.next = l1;
                l1 = l1.next;
            }
            tmp = tmp.next;
        }
        tmp.next = l1 == null ? l2:l1;
        return node.next;
    }

    //1.10 如何在只给定单链表中某个结点的指针的情况下删除该结点
    public ListNode deleteK(ListNode head){
        if(head == null){
            return head;
        }



        return head;
    }



    @Test
    public void runTest(){
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(3);
        node1.next.next = new ListNode(5);

        ListNode node2 = new ListNode(2);
        node2.next = new ListNode(4);
        node2.next.next = new ListNode(6);

        ListNode revert = mergeTwoLists1(node1,node2);

        while (revert != null){
            System.out.println(revert.val);
            revert = revert.next;
        }

    }
}
