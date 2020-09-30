package com.zsj.basic.leetcode.lc;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;

import java.util.*;

/**
 * @author ZSJ
 * @date 2020-09-14 16:24
 * @description
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
public class TreeSolution {


    //输出树的中序遍历
    //递归
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root,res);
        return res;
    }
    public void inorder(TreeNode node,List<Integer> res){
        if(node == null){
            return;
        }
        inorder(node.left,res);
        res.add(node.val);
        inorder(node.right,res);
    }
    //借助栈来操作
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> skt = new Stack<>();
        while (root != null || !skt.isEmpty() ){
            while (root != null){
                skt.push(root);
                root = root.left;
            }
            root = skt.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }


    //BFS  遍历树  队列  先进先出
    public void BFS(TreeNode node){
        if(node == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList();
        queue.add(node);
        while (queue.size() > 0){
            TreeNode poll = queue.poll();
            System.out.print(poll.val+" ");
            if(poll.left != null){
                queue.add(poll.left);
            }
            if(poll.right != null){
                queue.add(poll.right);
            }
        }
    }
    //DSF stack  先进后出
    public void DFS(TreeNode node){
        Stack<TreeNode> stack = new Stack<>();
        stack.add(node);
        while (stack.size() >0){
            TreeNode pop = stack.pop();
            System.out.print(pop.val+" ");
            if(pop.right != null){
                stack.push(pop.right);
            }
            if(pop.left != null){
                stack.push(pop.left);
            }
        }
    }
    //翻转二叉树
    //1、递归 翻转
    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
    //2、BSF 翻转
    public void BFSinvertTree(TreeNode node){
        if(node == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList();
        queue.add(node);
        while (queue.size() > 0){
            TreeNode poll = queue.poll();
            //先交换子节点
            //先交换子节点
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
            if(poll.left != null){
                queue.add(poll.left);
            }
            if(poll.right != null){
                queue.add(poll.right);
            }
        }
    }
    //DSF 翻转
    public TreeNode DFSinvertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            //先交换子节点
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return root;
    }


//                  4
//                /   \
//               2     7
//              / \   / \
//             1   3 6   9
    @Test
    public  void main() {

        TreeNode node = new TreeNode(4);
        node.left = new TreeNode(2);
        node.right = new TreeNode(7);

        node.left.left = new TreeNode(1);
        node.left.right = new TreeNode(3);

        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(9);

        BFS(node);
        System.out.println();
        DFS(node);
    }

}
