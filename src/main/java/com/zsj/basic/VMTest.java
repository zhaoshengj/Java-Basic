package com.zsj.basic;

/**
 * @author ZSJ
 * @date 2020-09-04 18:27
 * @description
 */
public class VMTest {

    private static User user;

    public void setUser() {
        user = new User();
        user.setId(1);
        user.setName("blueStarWei");
    }
    //u只在函数内部生效，不是逃逸对象
    //当函数调用结束，会自行销毁对象u
    public void createUser() {
        User u = new User();
        u.setId(2);
        u.setName("JVM");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("VMTest");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
             User user = alloc();
             //user.setUser();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        Thread.sleep(1000000000);

    }

    private static User alloc() {
        User user = new User();
        user.setId(1);
        user.setName("blueStarWei");
        return user;
    }

    private static User test(){
         User user = alloc();
         return user;
    }
}
class User{

    private User user;
    private int id;

    private String name;

    public void setUser() {
        user = new User();
        user.setId(1);
        user.setName("blueStarWei");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}