package com.zsj.basic.leetcode.jzof;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;

public class JZOF_EASY {

    public static class ListNode {

        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }

    }

    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //面试题03. 数组中重复的数字
    public int findRepeatNumber(int[] nums) {

        /*
        for(int i = 0 ;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i] == nums[j]){
                    return nums[i];
                }
            }

        }
        return 0;*/

        Set<Integer> set = new HashSet<Integer>();
        int repeat = -1;
        for (int num : nums) {
            if (!set.add(num)) {
                repeat = num;
                break;
            }
        }
        return repeat;

    }

    //面试题04. 二维数组中的查找
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix == null || matrix.length <1 || matrix[0].length < 1){
            return false;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0;
        n = n-1;
        while (row < m && n>=0){
            int num = matrix[row][n];
            if(num == target){
                return true;
            }
            if(num > target){
                n--;
            }else {
                row++;
            }
        }
      /* for(int i=0;i<m;i++){
           for(int j=0;j<n;j++){
               if(matrix[i][0] > target){
                   m = i;
               }
               if(matrix[0][j] > target){
                   n = j;
               }
               if(matrix[i][j] == target){
                   return true;
               }
               System.out.println(m+"："+n);
           }
       }*/
        return false;

    }

    //面试题05. 替换空格
    public String replaceSpace(String s) {
        char[] chars = s.toCharArray();
        StringBuilder str = new StringBuilder();
        for(int i = 0; i<chars.length;i++){
            if(chars[i] == ' '){
               str.append("%20");
            }else {
                str.append(chars[i]);
            }
        }
        return str.toString();
    }

    //面试题06. 从尾到头打印链表
    public int[] reversePrint(ListNode head) {
        Stack<Integer> stack = new Stack();
       while (head != null){
           stack.push(head.val);
           head =head.next;
       }
        int size = stack.size();
        int[] str = new int[size];
        for (int i = 0; i < size; i++) {
            str[i] = stack.pop();
        }
        return str;

    }

    //面试题10- II. 青蛙跳台阶问题
    HashMap<Integer,Integer> map = new HashMap();
    public int numWays(int n) {
        if(n == 1 || n==0){
            map.put(1,1);
            return 1;
        }else if (n == 2){
            map.put(2,2);
            return 2;
        }
        if(n > 2){
            if(map.get(n-2) == null){
                numWays(n - 2);
            }
            if(map.get(n-1) == null){
                numWays(n - 1);
            }
            map.put(n,(map.get(n-2)+map.get(n-1))%1000000007);
        }
        return map.get(n);
    }
    //动态规划
    public int numWays1(int n) {
        if(n == 1 || n==0){
            return 1;
        }
        int fir = 1;
        int sec = 1;
        for(int i =2;i<=n;i++){
            int s = (fir +sec)%1000000007;
            fir = sec;
            sec = s;

        }
        return sec;
    }

    //面试题10- I. 斐波那契数列
    public int fib(int n) {
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        int fir =0;
        int sec = 1;
        for(int i= 2;i<=n;i++){
            int s = (fir+sec)%1000000007;
           fir = sec;
           sec = s;

        }
        return sec;
    }

    //面试题11. 旋转数组的最小数字
    public int minArray(int[] numbers) {
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int m = (i + j) / 2;
            if (numbers[m] > numbers[j]) {
                i = m + 1;
            } else if (numbers[m] < numbers[j]) {
                j = m;
            } else {
                j--;
            }
        }
        return numbers[i];
    }

    //面试题15. 二进制中1的个数
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }

        return res;
    }

    //面试题17. 打印从1到最大的n位数
    public int[] printNumbers(int n) {
        int num = 1;
        for(int i =0;i<n;i++){
            num *= 10;
        }
        int[] in = new int[num-1];
        for (int i=1;i<num;i++){
            in[i - 1] = i;
        }
        return in;
    }


    //面试题18. 删除链表的节点
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null){
            return null;
        }
       ListNode pre = new ListNode(-1);//头节点
        pre.next = head;
        head = pre;
        while (head.next != null){
            if(head.next.val == val){
                head.next = head.next.next;
                break;
            }
            head = head.next;
        }
        return pre.next;
    }

    //面试题21. 调整数组顺序使奇数位于偶数前面
    public int[] exchange(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        while (left < right){
            if((nums[left] & 1) == 0){
                if((nums[right] & 1) == 1){
                    int a = nums[left];
                    nums[left] = nums[right];
                    nums[right] = a;
                }else {
                    right--;
                }
            }else {
                left++;
            }
        }
        return nums;
    }

    //面试题22. 链表中倒数第k个节点
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode node = head;
        int size = 0;
        while (head != null){
            head = head.next;
            size++;
        }
        head = node;
        if(size < k){
            return null;
        }
        if(k == size){
            return head;
        }
        size = size -k;
        while (size != 0){
            head = head.next;
            size--;
        }
        return head;
    }
    //双指针，移动K步
    public ListNode getKthFromEnd1(ListNode head, int k) {
        ListNode fir = head;
        ListNode sec = head;
        int size = 0;
        while (sec != null){
           if(size < k){
               sec = sec.next;
               size++;
           }else {
               sec = sec.next;
               fir = fir.next;
           }
        }
        return fir;
    }

    //面试题24. 反转链表
    //双指针(两个指针移动)
    public ListNode reverseList0(ListNode head) {
        ListNode cur = null;
        ListNode pre = head;
        while (pre != null){
            ListNode s =  pre.next;
            pre.next = cur;
            cur = pre;
            pre = s;
        }
        return cur;
    }
    //递归
    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ret = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return ret;

    }
    //双指针(一个指针固定，另外一个指针移动)
    public ListNode reverseList2(ListNode head) {
        /*if (head == null) { return null; }
        ListNode cur = head; //移动的当前指针
        while (head.next != null){
            ListNode t = head.next.next;
            head.next.next = cur;
            cur = head.next;
            head.next = t;
        }
        return cur;*/
        if(head == null){
            return null;
        }
        ListNode curr = head;
        while (head.next != null){
           ListNode t  = head.next.next;
           head.next.next = curr;
           curr = head.next;
           head.next = t;
        }
        return curr;
    }

    //面试题25. 合并两个排序的链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }if(l2 == null){
            return l1;
        }
        ListNode l = new ListNode(0);
        ListNode ret = l;
        while (l1 != null && l2 != null){
            if(l1.val > l2.val){
                l.next = l2;
                l2 = l2.next;
            }else {
                l.next = l1;
                l1 = l1.next;
            }
            l = l.next;
        }
        l.next = l1== null ? l2:l1;
        return ret.next;
    }
    //递归
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
       if(l1.val <= l2.val){
           l1.next = mergeTwoLists1(l1.next,l2);
           return l1;
       }
       l2.next = mergeTwoLists1(l1,l2.next);
       return l2;
    }

    //面试题27. 二叉树的镜像
    public TreeNode mirrorTree(TreeNode root) {
        //递归函数的终止条件，节点为空时返回
        if(root==null) {
            return null;
        }
        //下面三句是将当前节点的左右子树交换
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = tmp;
        //递归交换当前节点的 左子树
        mirrorTree(root.left);
        //递归交换当前节点的 右子树
        mirrorTree(root.right);
        //函数返回时就表示当前这个节点，以及它的左右子树
        //都已经交换完了
        return root;
    }

    //面试题28. 对称的二叉树
    public boolean isSymmetric(TreeNode root) {
        return root == null ? true : recur(root.left, root.right);
    }
    boolean recur(TreeNode left,TreeNode right){
        if(left == null && right == null) {
            return true;
        }
        if(left == null || right == null || left.val != right.val) {
            return false;
        }
        return recur(left.left,right.right) && recur(left.right,right.left);
    }



    //面试题29. 顺时针打印矩阵
    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length <= 0) {
            return new int[0];
        }
        int left = 0;
        int right = matrix[0].length-1;
        int top = 0;
        int bottom = matrix.length-1;
        int[] sum = new int[matrix.length*matrix[0].length];
        int s =0;
        while (true){
            //上边界
            for(int i = left;i<=right;i++){ // left to right.
                sum[s++] = matrix[top][i];
            }
            if(++top > bottom) {
                break;
            }
            //右边界
            for(int i = top;i<=bottom;i++){ // top to bottom.
                sum[s++] = matrix[i][right];
            }
            if(left > --right) {
                break;
            }
            //下边界
            for(int i = right;i>=left;i--){ // right to left.
                sum[s++] = matrix[bottom][i];
            }
            if(top > --bottom) {
                break;
            }
            //左边界
            for(int i = bottom;i>=top;i--){ // bottom to top.
                sum[s++] = matrix[i][left];
            }
            if(++left > right) {
                break;
            }
        }
        return sum;

    }



    //面试题32 - II. 从上到下打印二叉树 II
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> tmp = new ArrayList();
            int len = queue.size();
            for (int i = 0; i < len; i ++) {
                TreeNode node = queue.poll();
                tmp.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(new ArrayList<>(tmp));
        }
        return res;
    }

    //面试题39. 数组中出现次数超过一半的数字
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return  nums[nums.length/2];
    }

    //面试题40. 最小的k个数   最小堆处理
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        int[] res = new int[k];
        for(int i = 0;i<k;i++){
            res[i] = arr[i];
        }
        return res;
    }
    public int[] getLeastNumbers1(int[] arr, int k) {
        int[] res = new int[k];
        for(int i = k;i<arr.length;i++){
            for(int j = 0;j<k;j++ ){
                if(arr[i] < arr[j]){
                    int s = arr[i];
                    arr[i] = arr[j];
                    arr[j] = s;
                }
            }
        }
        for(int i = 0;i<k;i++){
            res[i] = arr[i];
        }
        return res;
    }

    //面试题42. 连续子数组的最大和
    public int maxSubArray(int[] nums) {
        if(nums == null){
            return 0;
        }
        int sum  = Integer.MIN_VALUE;
        int cur = 0;
        for(int i =0;i<nums.length;i++){
            if(cur <= 0){
                cur = nums[i];
            }else {
                cur += nums[i];
            }
            if(cur > sum){
                sum = cur;
            }
        }
        return sum;
    }

    //面试题43. 1～n整数中1出现的次数
    public int countDigitOne(int n) {
        int count = 0;
        long i = 1;        // 从个位开始遍历到最高位
        while(n / i != 0) {
            long high = n / (10 * i);  // 高位
            long cur = (n / i) % 10;   // 当前位
            long low = n - (n / i) * i;
            if(cur == 0) {
                count += high * i;
            }else if(cur == 1) {
                count += high * i + (low + 1);
            }else {
                count += (high + 1) * i;
            }
            i = i * 10;
        }
        return count;
    }

    //面试题49. 丑数
    public int nthUglyNumber(int n) {
        int[] sum = new int[n];
        sum[0] =1;
        int p2 =0,p3=0,p5=0;
        for(int i=1;i<n;i++){
            sum[i] = Math.min(sum[p2]*2,Math.min(sum[p3]*3,sum[p5]*5));
            if(sum[i] == sum[p2]*2) {
                p2++;
            }
            if(sum[i] == sum[p3]*3) {
                p3++;
            }
            if(sum[i] == sum[p5]*5) {
                p5++;
            }
        }
        return sum[n-1];

    }

    //面试题50. 第一个只出现一次的字符
    public char firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character,Integer> map =new HashMap();
        for(int i=0;i<chars.length;i++){
            Integer integer = map.get(chars[i]);
            if(integer == null){
                map.put(chars[i],1);
            }else {
                map.put(chars[i],++integer);
            }
        }
        for(int i=0;i<chars.length;i++){
            if(map.get(chars[i]) == 1){
                return chars[i];
            }
        }
        return ' ';
    }

    //面试题52. 两个链表的第一个公共节点
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode node = headA;
        while (node != null){
            ListNode node2 = headB;
            while (node2 != null){
                if(node == node2){
                    return node;
                }
                node2 = node2.next;
            }
            node = node.next;
        }
        return null;
    }

    //面试题53 - II. 0～n-1中缺失的数字
    public int missingNumber(int[] nums) {
        for(int i =0;i<nums.length;i++){
            if(i != nums[i]){
                return  i;
            }
        }
        return nums.length;
    }

    //剑指 Offer 54. 二叉搜索树的第k大节点
    int s,k;
    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        dsf(root);
        return s;

    }
    void dsf(TreeNode node){
        if(node == null){
            return;
        }
        dsf(node.right);
        if(k == 0){return;}
        if(--k == 0){s = node.val;}
        dsf(node.left);
    }


    //面试题55 - I. 二叉树的深度
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left,right)+1;

    }
    //剑指 Offer 55 - II. 平衡二叉树
    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }
        return balance(root) != -1;

    }
    public int balance(TreeNode treeNode){
        if(treeNode == null){
            return  0;
        }
        int right = balance(treeNode.right);
        if(right == -1){
            return right;
        }
        int left = balance(treeNode.left);
        if(left == -1){
            return  left;
        }
        return Math.abs(right-left) < 2?Math.max(right,left)+1:-1;
    }
    //面试题57. 和为s的两个数字
    //双指针
    public int[] twoSum(int[] nums, int target) {
       int left =0,right = nums.length-1;
       while (left < right){
           if(nums[left]+nums[right] == target){
               int[] res = {nums[left],nums[right]};
               return res;
           }
           if(nums[left] + nums[right] > target){
               right --;
           }else {
               left ++;
           }
       }
       return null;
    }

    //面试题57 - II. 和为s的连续正数序列
    //方法一：枚举 + 暴力
    public int[][] findContinuousSequence(int target) {
        List<int[]> list = new ArrayList<>();
        int sum = 0,limit = target/2;
        for(int i=1;i<=limit;i++){
            for(int j=i;;j++){
                sum +=j;
                if(sum == target){
                    int[] tem = new int[j-i+1];
                    int a= 0;
                    for(int k=i;k<=j;k++){
                        tem[a] =k;
                        a++;
                    }
                    list.add(tem);
                    sum =0;
                    break;
                }
                if(sum > target){
                    sum = 0;
                    break;

                }
            }
        }
        int[][] res = new int[list.size()][];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }
    //双指针
    public int[][] findContinuousSequence1(int target) {
        List<int[]> list = new ArrayList<>();
        for(int left = 1,right =2;left<right;){
            // 数学公式（）
            int sum = (left+right)*(right-left+1)/2;
            if(sum == target){
                int[] tem = new int[right-left+1];
                int a= 0;
                for(int k=left;k<=right;k++){
                    tem[a] =k;
                    a++;
                }
                list.add(tem);
                left++;
            }else if(sum < target){
                right++;
            }else {
                left++;
            }
        }
        int[][] res = new int[list.size()][];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    //面试题58 - I. 翻转单词顺序
    public String reverseWords(String s) {
        /*char[] chars = s.trim().toCharArray();
        StringBuilder str = new StringBuilder("");
        int a = 0;
        for(int i=0;i<=chars.length-1;i++){
            if(chars[i] == ' '){
                a++;
                if(a > 1){
                  continue;
                }
            }else {
                a = 0;
            }
            str.append(chars[i]);

        }*/
        String[] s1 = s.split(" ");
        StringBuilder str = new StringBuilder("");
        for(int i = s1.length-1;i>=0;i--){
            if(s1[i] != " "){
                str.append(s1[i]);
                str.append(" ");
            }
        }
        return str.toString().trim();
    }


    //面试题59 - I. 滑动窗口的最大值
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length < k){
            k = nums.length;
        }
        int[] res = new int[nums.length - k +1];
        if(k < 1){
            return new int[0];
        }
        int star = 0;
        int end = k-1;
        int s =0;
        while (end < nums.length){
            int sum = nums[star];
            for(int i =star;i<= end;i++){
               if(sum < nums[i]){
                   sum = nums[i];
               }
            }
            res[s++] = sum;
            star ++;
            end ++;
        }
        return res;
    }


    //面试题60. n个骰子的点数
    public double[] twoSum(int n) {
        //初始二位数组
        int[][] num = new int[n+1][6*n+1];
        for(int i=1;i<=6;i++){
            num[1][i] = 1;
        }
        //n 个骰子
        for(int i = 2;i<=n;i++){
            // n 个骰子的和
           for(int s= i;s<=i*6;s++){
               //骰子出现的可能
               for(int j=1;j<=6;j++){
                   //小于骰子数的，不可能
                   if(s-j<i-1) {
                       break;
                   }
                   //骰子出现的可能
                   num[i][s] += num[i-1][s-j];
               }
           }
        }

        double pow = Math.pow((double) 6, (double) n);
        double[] res = new double[n*5+1];
        for(int i=n;i<=n*6;i++){
            res[i-n] = (double) num[n][i]/pow;
        }
        return  res;
    }
    //剑指 Offer 61. 扑克牌中的顺子
    public boolean isStraight(int[] nums) {
        int min = 0;
        sort(nums);
        for(int i=0;i< 4;i++){
            if(nums[i] == 0){
                min ++;
            }else if(nums[i] == nums[i+1]) {
                return false;
            }
        }
        return nums[4]-nums[min] < 5;
    }
    void sort(int[] nums){
        for(int i=0;i<nums.length-1;i++){
            for(int j=i;j<nums.length;j++){
                if(nums[i] > nums[j]){
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j]= tmp;
                }
            }
        }
    }
    //剑指 Offer 62. 圆圈中最后剩下的数字
    public int lastRemaining(int n, int m) {
        int res = 0;
        for(int i = 2;i<= n;i++){
            res = (res+m)%i;
        }
        return res;
    }

    //面试题65. 不用加减乘除做加法
    public int add(int a, int b) {
        while (b != 0) {
            int plus = (a ^ b); // 求和（不计进位）. 相同位置0，相反位置1
            b = ((a & b) << 1); // 计算进位. 先保留同为1的位，都为1的位要向左进位，因此左移1位
            a = plus;
        }
        return a;
    }

    //面试题66. 构建乘积数组
    public int[] constructArr(int[] a) {
        int[] arr = new int[a.length];
        for(int i = 0;i<a.length;i++){
            int num = 1;
            for(int j = 0;j<a.length;j++){
                if(i != j){
                    num = a[j]*num;
                }
            }
            arr[i] = num;
        }
        return arr;
    }
    // 优化后 对称遍历
    public int[] constructArr1(int[] a) {
        if(0==a.length) {
            return new int[0];
        }
        int length=a.length;
        int[] b=new int[length];
        b[0]=1;
        for(int i=1;i<length;i++) {
            b[i]=b[i-1]*a[i-1];
        }
        int temp=1;
        for(int j=length-2;j>=0;j--) {
            temp*=a[j+1];
            b[j]*=temp;
        }
        return b;
    }
    //剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
       while (root != null){
           if(root.val > p.val && root.val > q.val){
               root = root.left;
           }else if(root.val < p.val && root.val < q.val){
              root = root.right;
           }else {
               break;
           }
       }
       return root;
    }

    //剑指 Offer 68 - II. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == q || root==p){
            return  root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left == null){
            return right;
        }
        if(right == null){
            return left;
        }
        return root;
    }



    @Test
    public void test(){
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(4);
        node1.next.next = new ListNode(5);

        ListNode node2 = new ListNode(3);
        node2.next = new ListNode(4);
        node2.next.next = new ListNode(6);

        /*
        ListNode node1 = deleteNode(node, 4);
        while (node1 != null){
            System.out.println(node1.val);
            node1 = node1.next;
        }
        double[] doubles = twoSum(2);
        System.out.println(JSON.toJSONString(doubles));
        int add = add(2, 3);
        System.out.println(add);

        ListNode node = getIntersectionNode(node1, node2);
        System.out.println(node);
        System.out.println(node.val);

        int i = nthUglyNumber(11);
        System.out.println(i);

        ListNode node = reverseList2(node1);
        ListNode node = mergeTwoLists1(node1, node2);


        int[] nums = {10,26,30,31,47,60};
        int[] ints = twoSum(nums, 40);
        System.out.println(JSON.toJSONString(ints));


        int[][] continuousSequence = findContinuousSequence1(15);
        System.out.println(JSON.toJSONString(continuousSequence));

        ListNode node = getKthFromEnd1(node1, 3);
        while (node != null){
            System.out.println(node.val);
            node = node.next;
        }
        int i = maxSubArray(nums);

        int[] nums = {1,2,3,3,1};
        int[] exchange = exchange(nums);
        System.out.println(JSON.toJSONString(exchange));

        String zsj_gjq = reverseWords("the sky is blue ");
        System.out.println(zsj_gjq);

        int[] nums = {0,1,3};
        int i = missingNumber(nums);
        System.out.println(i);

        char abaccdeff = firstUniqChar("abaccdeff");
        System.out.println(abaccdeff);

        int[] nums = {};
        int[] ints = maxSlidingWindow(nums, 3);
         */

        //1,1,2,6,24
        int[] nums = {1,2,3,4,5};
        int[] ints = constructArr1(nums);
        System.out.println(JSON.toJSONString(ints));


        System.out.println(887902074721L%4);

    }


}

