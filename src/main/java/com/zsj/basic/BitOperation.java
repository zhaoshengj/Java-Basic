package com.zsj.basic;

/**
 * @author ZSJ
 * @date 2020-08-27 15:48
 * @description 位运算实现加减乘除    计算机中存储的二进制是补码的形式
 *
 *   正数： 反码 == 补码 ==原码
 *
 *   负数： 负数的反码就是他的原码除符号位外，按位取反。
 *         负数的补码等于反码+1。
 *
 *
 */
public class BitOperation {

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(~1));
        System.out.println(~1);

    }

    static int add1(int a, int b) //递归形式
    {
        //递归结束条件：如果右加数为0，即不再有进位了，则结束。
        if (b == 0) {
            return a;
        }
        if (a == 0) {
            return b;
        }
        int s = a ^ b;
        //进位左移1位，达到进位的目的。
        int c = (a & b) << 1;
        //再把'和'和'进位'相加。递归实现。
        return add1(s, c);
    }

    //非递归形式
    int add(int a, int b) {
        int s, c;
        while (b != 0) {
            s = a ^ b;
            c = (a & b) << 1;
            a = s;
            b = c;
        }
        return a;
    }

    //减法运算：利用求负操作和加法操作
    int subtraction(int a, int b) {
        return add(a, negtive(b));
    }

    int negtive(int i) {
        return add(~i, 1);
    }


}


