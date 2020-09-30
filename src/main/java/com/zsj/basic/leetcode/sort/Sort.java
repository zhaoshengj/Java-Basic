package com.zsj.basic.leetcode.sort;

/**
 * @author ZSJ
 * @date 2020-07-23 09:48
 * @description 排序
 */
public class Sort {

    public static int binary(int[] nums,int left,int right,int n){
        int mid = (left+right)/2+(left+right)%2;
        if(n == nums[mid]){
            return mid;
        }else if(n > nums[mid]){
           return binary(nums,mid,right,n);
        }else {
           return binary(nums,left,mid,n);
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
    }
    //i: nums1的起始位置 j: nums2的起始位置   k 中间位置
    public int findKth(int[] nums1, int i, int[] nums2, int j, int k){
        if( i >= nums1.length) return nums2[j + k - 1];//nums1为空数组
        if( j >= nums2.length) return nums1[i + k - 1];//nums2为空数组
        if(k == 1){
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if(midVal1 < midVal2){
            return findKth(nums1, i + k / 2, nums2, j , k - k / 2);
        }else{
            return findKth(nums1, i, nums2, j + k / 2 , k - k / 2);
        }
    }





    public static void main(String[] args) {
        int[] nums = {2,2,3,8,10};
        System.out.println(binary(nums,0,nums.length-1,8));
    }
}
