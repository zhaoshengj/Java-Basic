package com.zsj.basic.leetcode.jzof;

import java.util.Stack;

public class JZOF9 {

    class CQueue {

        Stack stack1; //辅助
        Stack stack2; //存储数据

        public CQueue() {
            stack1 = new Stack();
            stack2 = new Stack();
        }

        public void appendTail(int value) {
           while (stack2.size() > 0){
               stack1.push(stack2.pop());
           }
           stack2.push(value);
           while (stack1.size() > 0){
               stack2.push(stack1.pop());
           }
        }

        public int deleteHead() {
            if(stack2.empty()){
                return -1;
            }else {
               return (int) stack2.pop();
            }
        }
    }
}
