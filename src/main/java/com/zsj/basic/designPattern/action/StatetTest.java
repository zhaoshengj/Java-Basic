package com.zsj.basic.designPattern.action;

/**
 * 状态模式的角色
 *
 * 环境角色Context）：也称上下文，定义客户端所感兴趣的接口，并且保留一个具体状态类的实例。这个具体状态类的实例给出此环境对象的现有状态。
 * 抽象状态角色（State）：定义一个接口，用以封装环境对象的一个特定的状态所对应的行为。
 * 具体状态角色（ConcreteState）：每一个具体状态类都实现了环境（Context）的一个状态所对应的行为。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51245024
 */

//案例
//以酒店订房为例，房间的状态有：空闲、预订、入住。那么空闲房间的状态可以转变为：
// 预订、入住。已预订状态房间的状态可以转变为：入住、取消预订。已入住房间的状态可以转变为：退房
public class StatetTest {
    public static void main(String[] args) {
        Room[] rooms = new Room[2];
        for(int i=0;i<rooms.length;i++)
        {
            rooms[i] = new Room();
        }

        rooms[0].bookRoom();
        rooms[0].checkInRoom();
        rooms[0].bookRoom();
        System.out.println(rooms[0]);
        System.out.println("-------------");

        rooms[1].checkInRoom();
        rooms[1].bookRoom();
        rooms[1].checkOutRoom();
        rooms[1].bookRoom();
        System.out.println(rooms[1]);

    }
}
interface State
{
    public void bookRoom();
    public void unsubscribeRoom();
    public void checkInRoom();
    public void checkOutRoom();
}
class Room
{
    private State freeTimeState;
    private State checkInState;
    private State bookedState;

    private State state;

    public Room()
    {
        freeTimeState = new FreeTimeState(this);
        checkInState = new CheckInState(this);
        bookedState = new BookedState(this);
        state = freeTimeState;
    }

    public void bookRoom()
    {
        state.bookRoom();
    }
    public void unsubscribeRoom()
    {
        state.unsubscribeRoom();
    }
    public void checkInRoom()
    {
        state.checkInRoom();
    }
    public void checkOutRoom()
    {
        state.checkOutRoom();
    }

    public String toString()
    {
        return "该房间的状态是:"+getState().getClass().getName();
    }

    public State getFreeTimeState()
    {
        return freeTimeState;
    }

    public void setFreeTimeState(State freeTimeState)
    {
        this.freeTimeState = freeTimeState;
    }

    public State getCheckInState()
    {
        return checkInState;
    }

    public void setCheckInState(State checkInState)
    {
        this.checkInState = checkInState;
    }

    public State getBookedState()
    {
        return bookedState;
    }

    public void setBookedState(State bookedState)
    {
        this.bookedState = bookedState;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }
}
 class FreeTimeState implements State
{
    private Room hotelManagement;

    public FreeTimeState(Room hotelManagement)
    {
        this.hotelManagement = hotelManagement;
    }
    @Override
    public void bookRoom()
    {
        System.out.println("您已经预定成功了！");
        this.hotelManagement.setState(this.hotelManagement.getBookedState());
    }

    @Override
    public void unsubscribeRoom()
    {
    }

    @Override
    public void checkInRoom()
    {
        System.out.println("您已经入住了！");
        this.hotelManagement.setState(this.hotelManagement.getCheckInState());
    }

    @Override
    public void checkOutRoom()
    {
    }
}
 class CheckInState implements State
{
    private Room hotelManagement;

    public CheckInState(Room hotelManagement)
    {
        this.hotelManagement = hotelManagement;
    }
    @Override
    public void bookRoom()
    {
        System.out.println("该房间已经入住了");
    }

    @Override
    public void unsubscribeRoom()
    {
    }

    @Override
    public void checkInRoom()
    {
        System.out.println("该房间已经入住了");
    }

    @Override
    public void checkOutRoom()
    {
        System.out.println("退房成功");
        this.hotelManagement.setState(this.hotelManagement.getFreeTimeState());
    }
}
 class BookedState implements State
{
    private Room hotelManagement;

    public BookedState(Room hotelManagement)
    {
        this.hotelManagement = hotelManagement;
    }

    @Override
    public void bookRoom()
    {
        System.out.println("该房间已经预定了");
    }

    @Override
    public void unsubscribeRoom()
    {
        System.out.println("成功退订");
        this.hotelManagement.setState(this.hotelManagement.getFreeTimeState());
    }

    @Override
    public void checkInRoom()
    {
        System.out.println("入住成功");
        this.hotelManagement.setState(this.hotelManagement.getCheckInState());
    }

    @Override
    public void checkOutRoom()
    {
    }
}
