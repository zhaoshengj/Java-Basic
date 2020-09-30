package com.zsj.basic.leetcode.jzof;

import com.sun.imageio.plugins.common.I18N;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

/**
 * @author ZSJ
 * @date 2020-09-02 09:46
 * @description 剑指offer  中等难度
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
public class JZOF_MED {

    //构建 二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null){
            return null;
        }
        return bulidPreIn(preorder,0,preorder.length-1,inorder,0,inorder.length-1);
    }
    //先序，中序   构建二叉树
    TreeNode bulidPreIn(int[] preorder,int preStart,int preEnd,int[] inorder,int inStart,int inEnd){
        if(preStart > preEnd || inStart > inEnd){
            return null;
        }
        TreeNode node = new TreeNode(preorder[preStart]);
        for(int i =0;i<inorder.length;i++){
            if(preorder[preStart] == inorder[i]){
                node.left = bulidPreIn(preorder,preStart+1,preStart+(i-inStart),
                        inorder,inStart,i-1);
                node.right = bulidPreIn(preorder,preStart+(i-inStart)+1,preEnd,
                        inorder,i+1,inEnd);
            }
        }
        return node;
    }

    /**
     *  后续  中序  构建二叉树
     */
    TreeNode bulidPostIn(int[] postorder,int postStart,int postEnd,int[] inorder,int inStart,int inEnd){
        if(postStart > postEnd || inStart > inEnd){
            return null;
        }
        TreeNode node = new TreeNode(postorder[postEnd]);
        for(int i =0;i<inorder.length;i++){
            if(postorder[postEnd] == inorder[i]){
                node.left = bulidPostIn(postorder,postStart,postStart+(i-inStart)-1,
                        inorder,inStart,i-1);
                node.right = bulidPostIn(postorder,postStart+(i-inStart),postEnd-1,
                        inorder,i+1,inEnd);
            }
        }
        return node;
    }

    //剑指 Offer 07. 重建二叉树 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     class Solution {
        //利用原理,先序遍历的第一个节点就是根。在中序遍历中通过根 区分哪些是左子树的，哪些是右子树的
        //左右子树，递归
        HashMap<Integer, Integer> map = new HashMap<>();//标记中序遍历
        int[] preorder;//保留的先序遍历
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            this.preorder = preorder;
            //终须的数组放入 map中
            for (int i = 0; i < inorder.length; i++) {
                map.put(inorder[i], i);
            }
            return recursive(0,0,inorder.length-1);
        }
        /**
         * @param pre_root_idx  先序遍历的索引
         * @param in_left_idx  中序遍历的索引
         * @param in_right_idx 中序遍历的索引
         */
        public TreeNode recursive(int pre_root_idx, int in_left_idx, int in_right_idx) {
            //相等就是自己
            if (in_left_idx > in_right_idx) {
                return null;
            }
            //root_idx是在先序里面的
            TreeNode root = new TreeNode(preorder[pre_root_idx]);
            // 有了先序的,再根据先序的，在中序中获 当前根的索引
            // 小于idx 左子树   大于idx 右子树
            int idx = map.get(preorder[pre_root_idx]);
            //左子树的根节点就是 左子树的(前序遍历）第一个，就是+1,左边边界就是left，右边边界是中间区分的idx-1
            root.left = recursive(pre_root_idx + 1, in_left_idx, idx - 1);
            //由根节点在中序遍历的idx 区分成2段,idx 就是根
            //右子树的根，就是右子树（前序遍历）的第一个,就是当前根节点 加上左子树的数量
            // pre_root_idx 当前的根  左子树的长度 = 左子树的左边-右边 (idx-1 - in_left_idx +1) 。最后+1就是右子树的根了
            root.right = recursive(pre_root_idx + (idx-1 - in_left_idx +1)  + 1, idx + 1, in_right_idx);
            return root;
        }
    }

    //剑指 Offer 12. 矩阵中的路径
//    [["a","b","c","e"],
//     ["s","f","c","s"],
//     ["a","d","e","e"]]
    public boolean exist(char[][] board, String word) {
       char[] chars = word.toCharArray();
       for(int h = 0; h<board.length;h++){
           for(int l=0;l<board[0].length;l++){
                if(existDSF(board,h,l,chars,0)){
                    return true;
                }
           }
       }
       return false;
    }
    boolean existDSF(char[][] board, int h, int l, char[] chars, int k){
        if(h>=board.length||h<0 || l>=board[0].length||l<0 || board[h][l] != chars[k]){
            return false;
        }
        if(k==chars.length-1){
            return true;
        }
        char tmp = board[h][l];
        board[h][l] = '*';
        boolean res = existDSF(board,h,l-1,chars,k+1) || existDSF(board,h,l+1,chars,k+1)
                   || existDSF(board,h-1,l,chars,k+1) || existDSF(board,h+1,l,chars,k+1);
        board[h][l] = tmp;
        return res;
    }

    //剑指 Offer 13. 机器人的运动范围
    //DFS
    class Solution1 {
        int m,n,k;
        boolean[][] sign;
        public int movingCount(int m, int n, int k) {
            this.k=k;
            this.m=m;
            this.n=n;
            sign = new boolean[m][n];
            return movingDSF(0,0,0,0);
        }

        int movingDSF(int i,int j,int s1,int s2){
            if(i>=m ||j>=n||s1+s2>k||sign[i][j]){
                return 0;
            }
            sign[i][j] = true;
            return 1+movingDSF(i,j+1,s1,(j+1)%10!=0?j+1:j-8)+movingDSF(i+1,j,(i+1)%10!=0?i+1:i-8,s2);

        }

    }
    //BFS
    public int movingCount(int m, int n, int k) {
        boolean[][] sign = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0,0,0});
        int size = 0;
        while (queue.size() >0){
           int[] poll = queue.poll();
           int h = poll[0],l=poll[1],s1=poll[2],s2=poll[3];
           if(h >=m||l>=n || s1+s2 >k || sign[h][l]){
               continue;
           }
           sign[h][l] = true;
           size++;
           queue.add(new int[]{h+1,l,(h+1)%10!=0?s1+1:s1-8,s2});
           queue.add(new int[]{h,l+1,s1,(l+1)%10!=0?s2+1:s2-8});
        }
        return size;
    }

    //剑指 Offer 14- I. 剪绳子
    public int cuttingRope1(int n) {
        if(n <= 3){
            return n-1;
        }
        int s=n/3,m=n%3;
        if(m == 0){
            return (int) Math.pow(3,s);
        }
        if(m==1){
            return (int) Math.pow(3,s-1)*4;
        }
        return (int) Math.pow(3,s)*2;
    }
    //动态规划法  dp[i] = max(dp[i], (max(j, dp[j])) * (max(i - j, dp[i - j])));
    public int cuttingRope2(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i],
                        (Math.max(j, dp[j])) * (Math.max(i - j, dp[i - j])));
            }
        }
        return dp[n];
    }
    //剑指 Offer 14- II. 剪绳子 II
    public int cuttingRope(int n) {
       if(n<=3){
           return n-1;
       }
       int s = n/3,b=n%3,p=1000000007;
       long res = 1;
       for(int i = 1;i<s;i++){
           res = res*3%p;
       }
       if(b == 0){
           return (int) (res*3%p);
       }
       if(b == 1){
           return (int) (res*4%p);
       }
       return (int) (res*6%p);
    }
    //剑指 Offer 26. 树的子结构
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(A == null || B == null){
            return false;
        }
        if(A.val == B.val && helper(A.right,B.right) && helper(A.left,B.left)){
            return  true;
        }
        return isSubStructure(A.left,B) || isSubStructure(A.right,B);
    }
    boolean helper(TreeNode A,TreeNode B){
        if(B == null){
            return true;
        }
        if(A == null || A.val != B.val){
            return false;
        }
        return helper(A.left,B.left) && helper(A.right,B.right);
    }

    //剑指 Offer 45. 把数组排成最小的数
    // x+y>y+x 则 y则在x前面  + 是拼接
    // x = "6",y="301" 则x+y > y+x y在x的前面
    public String minNumber(int[] nums) {
        String[] str = new String[nums.length];
        for(int i=0;i<nums.length;i++){
            str[i] = String.valueOf(nums[i]);
        }
        for(int i=0;i<nums.length;i++){
            for(int j=i;j<nums.length;j++){
                if(Long.parseLong(str[i] +str[j]) > Long.parseLong(str[j]+str[i])){
                    String tmp = str[j];
                    str[j] = str[i];
                    str[i] = tmp;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for(int i=0;i<nums.length;i++){
            res.append(str[i]);
        }
        return res.toString();

    }
    //剑指 Offer 46. 把数字翻译成字符串
    public int translateNum(int num) {
        if(num < 10){
            return 1;
        }
        if(num%100 < 26 && num%100 > 9){
            return translateNum(num/100)+translateNum(num/10);
        }else {
            return translateNum(num/10);
        }

    }
    public int translateNum1(int num) {
        String src = String.valueOf(num);
        int p = 0, q = 0, r = 1;
        for(int i=0;i<src.length();++i){
            p =q;q=r;r=0;r=q;
            if(i==0){
                continue;
            }
            if(src.substring(i-1,i+1).compareTo("25") <= 0 && src.substring(i-1,i+1).compareTo("10") >=0){
                r=r+p;
            }
        }
        return r;
    }

    //滑动数组
    public int test(){
        int p =0,q=0,r=1;
        for(int i=0;i<45;i++){
            p=q;
            q=r;
            r=0;
            r=q;
            r=r+p;
        }
        return r;
    }

    @Test
    public void main() {

    }

}
