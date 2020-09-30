package com.zsj.basic.leetcode.jzof;

import java.util.LinkedList;

/**
 * @author ZSJ
 * @date 2020-07-13 18:23
 * @description 剑指 Offer 30. 包含min函数的栈
 */
public class JZOF30 {


    class MinStack {

        private LinkedList<Integer> list ;

        private Integer min;

        /** initialize your data structure here. */
        public MinStack() {
            list = new  LinkedList<Integer>();
            min = Integer.MAX_VALUE;
        }

        public void push(int x) {
            if(x <= min){
                list.add(min);
                min = x;
            }
            list.add(x);
        }

        public void pop() {
            if(list.pollLast().equals(min)){
               min = list.pollLast();
            }
        }

        public int top() {
            return list.peekLast();
        }

        public int min() {
            return min;
        }
    }



}
