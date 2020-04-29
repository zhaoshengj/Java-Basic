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
     /*
        int[] A = {4,5,6,0,0,0};
        int[] B = {1,2,3};
        merge(A,3,B,3);
     */

        int[] s = {7,6,4,3,1};
        int i = maxProfit(s);
        System.out.println(i);

        String str = "AAAAAAAA";
        String abab = gcdOfStrings(str, "AA");
        System.out.println(abab);

    }
}
