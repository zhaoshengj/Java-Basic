package com.zsj.basic.leetcode.lc;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class lc {

    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //4. 寻找两个正序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      int l = (nums1.length+nums2.length+1)/2;
      int r = (nums1.length+nums2.length+2)/2;
      return (findMedian(nums1,0,nums2,0,l)+findMedian(nums1,0,nums2,0,r))/2.0;
    }

    /**
     * 查询数组中的第k值
     * @param nums1 数组1
     * @param i  数组1起始坐标
     * @param nums2 数组2
     * @param j 数组2起始坐标
     * @param k  第k值
     * @return
     */
    public int findMedian(int[] nums1,int i,int[] nums2,int j,int k){
        if(i >= nums1.length){ return nums2[j+k-1];}
        if(j >= nums2.length){return nums1[i+k-1];}
        if(k == 1){
            return Math.min(nums1[i],nums2[j]);
        }
        int midNums1 = i+k/2-1 < nums1.length?nums1[i+k/2-1]:Integer.MAX_VALUE;
        int midNums2 = j+k/2-1 < nums2.length?nums2[j+k/2-1]:Integer.MAX_VALUE;
        if(midNums1 > midNums2){
            return findMedian(nums1,i,nums2,j+k/2,k-k/2);
        }else {
            return findMedian(nums1,i+k/2,nums2,j,k-k/2);
        }

    }



    //面试题 10.01. 合并排序的数组
    public void merge(int[] A, int m, int[] B, int n) {
        int cur = m+n;
        while (n > 0 && m>0){
            if(A[m-1] > B[n-1]){
                A[cur-1] = A[m-1];
                m --;
            }else {
                A[cur-1] = B[n-1];
                n --;
            }
            cur --;
        }
        if(n > 0){
            A[cur-1] = B[n-1];
            n--;
            cur--;
        }
        System.out.println(JSON.toJSONString(A));
    }



    //121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        int sum = 0;
        for(int  i= 0;i<prices.length;i++){
            for(int j=i+1;j<prices.length;j++){
                if(sum < prices[j] - prices[i]){
                    sum = prices[j] - prices[i];
                }
            }
        }
        return sum;

    }

    // 146. LRU缓存机制
    class LRUCache {

        LinkedHashMap hashMap;
        int capacity = 0;

        public LRUCache(int capacity) {
            capacity = capacity;
            hashMap = new LinkedHashMap(capacity);
        }

        public int get(int key) {
            if (hashMap.keySet().contains(key)) {
                int value = (int) hashMap.get(key);
                hashMap.remove(key);
                // 保证每次查询后，都在末尾
                hashMap.put(key, value);
                return value;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (hashMap.keySet().contains(key)) {
                hashMap.remove(key);
            } else if (hashMap.size() == capacity) {
                Iterator<Map.Entry<Integer, Integer>> iterator = hashMap.entrySet().iterator();
                iterator.next();
                iterator.remove();
            }
            hashMap.put(key, value);



        }
    }
    //226. 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return root;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;

    }

    //543. 二叉树的直径
    int res = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return res;
    }
    // 函数dfs的作用是：找到以root为根节点的二叉树的最大深度
    //深度优先搜索
    public int dfs(TreeNode root){
        if(root == null){ //访问到空节点了，返回0
            return 0;
        }
        int leftDepth = dfs(root.left); // 左儿子为根的子树的深度

        int rigthDepth = dfs(root.right); // 右儿子为根的子树的深度
        res = Math.max(res,leftDepth + rigthDepth); // 计算d_node即L+R 并更新res
        return Math.max(leftDepth,rigthDepth) + 1; // 返回该节点为根的子树的深度
    }

    //1071. 字符串的最大公因子
    public String gcdOfStrings(String str1, String str2) {
        if((str1+str2).equals(str2+str1)){
            // 辗转相除法求gcd。
            return str1.substring(0, gcd(str1.length(), str2.length()));
        }
        return "";
    }
    private int gcd(int a, int b) {
        return b == 0? a: gcd(b, a % b);
    }

    @Test
    public void test(){

        int[] A = {4,5,6};
        int[] B = {1,2,3};
        //merge(A,3,B,3);

        double medianSortedArrays = findMedianSortedArrays(A, B);
        System.out.println(medianSortedArrays);

      /*  int[] s = {7,6,4,3,1};
        int i = maxProfit(s);
        System.out.println(i);

        String str = "AAAAAAAA";
        String abab = gcdOfStrings(str, "AA");
        System.out.println(abab);*/

    }
}
