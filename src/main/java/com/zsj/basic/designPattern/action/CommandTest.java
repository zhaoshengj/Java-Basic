package com.zsj.basic.designPattern.action;

/**
 * 命令模式的角色
 *
 * 客户端角色（Client）：创建一个具体命令（ConcreteCommand）对象并确定其接收者。
 * 命令角色（Command）：声明一个给所有命令类的抽象接口。
 * 具体命令角色(ConcreteCommand)：定义一个接收者和行为之间的弱耦合；实现execute()方法，负责调用接收者的相应操作。execute()方法叫做执行方法。
 * 请求者角色（Invoker)：负责调用命令对象执行请求，相关的方法叫做行动方法。
 * 接收者角色（Receiver）：负责具体实施和执行一个请求。任何一个类都可以称为接收者，实施和执行请求的方法叫做行动方法。
 *
 *
 * 举个简单例子（录音机有播音Play，倒带Rewind和停止Stop功能）
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51058106
 */
public class CommandTest {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();
        Command playCommand = new PlayCommand(audioPlayer);
        Command rewindCommand = new RewindCommand(audioPlayer);
        Command stopCommand = new StopCommand(audioPlayer);

        Keypad keypad = new Keypad();
        keypad.setPlayCommand(playCommand);
        keypad.setRewindCommand(rewindCommand);
        keypad.setStopCommand(stopCommand);

        keypad.play();
        keypad.rewind();
        keypad.stop();

    }
}
//1 接收者角色
class AudioPlayer
{
    public void play()
    {
        System.out.println("Play");
    }

    public void rewind()
    {
        System.out.println("Rewind");
    }

    public void stop()
    {
        System.out.println("Stop");
    }
}
interface Command
{
    public void execute();
}
class PlayCommand implements Command
{
    private AudioPlayer myAudio;
    public PlayCommand(AudioPlayer audioPlayer)
    {
        this.myAudio = audioPlayer;
    }
    @Override
    public void execute()
    {
        myAudio.play();
    }
}
class RewindCommand implements Command
{
    private AudioPlayer myAudio;
    public RewindCommand(AudioPlayer audioPlayer)
    {
        this.myAudio = audioPlayer;
    }
    @Override
    public void execute()
    {
        this.myAudio.rewind();
    }
}
class StopCommand implements Command
{
    private AudioPlayer myAudio;
    public StopCommand(AudioPlayer audioPlayer)
    {
        this.myAudio = audioPlayer;
    }
    @Override
    public void execute()
    {
        this.myAudio.stop();
    }
}
class Keypad
{
    private Command playCommand;
    private Command rewindCommand;
    private Command stopCommand;
    public void setPlayCommand(Command playCommand)
    {
        this.playCommand = playCommand;
    }
    public void setRewindCommand(Command rewindCommand)
    {
        this.rewindCommand = rewindCommand;
    }
    public void setStopCommand(Command stopCommand)
    {
        this.stopCommand = stopCommand;
    }
    public void play()
    {
        playCommand.execute();
    }
    public void rewind()
    {
        rewindCommand.execute();
    }
    public void stop()
    {
        stopCommand.execute();
    }
}
