package com.zsj.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

public class BasicApplication {


    public static void main(String[] args) {
        System.out.println(-1 ^ -1 << 5);
        System.out.println(Integer.toBinaryString(-1 ^ -1 << 5));

        System.out.println(146945491210L << 22  );
        System.out.println(Long.toBinaryString(146945491210L << 22).length() );
        System.out.println(146945491210L << 22 | 0 << 17 | 1 << 12 | 1);

    }




}
