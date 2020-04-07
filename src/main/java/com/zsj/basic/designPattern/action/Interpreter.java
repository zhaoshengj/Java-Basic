package com.zsj.basic.designPattern.action;

import java.util.HashMap;
import java.util.Map;

/**
 * 解释器有以下5个角色。
 *  ■ 抽象表达式（Abstract Expression）
 *  角色：该角色声明一个所有的具体表达式角色都需要实现的抽象接口，该接口主要是一个解释操作interpret()方法。
 *  ■ 终结符表达式（Terminal Expression）
 *  角色：该角色实现了抽象表达式角色所要求的接口，文法中的每一个终结符都有一个具体终结表达式与之对应。
 *  ■ 非终结符表达式（Nonterminal Expression）
 *  角色：该角色是一个具体角色，文法中的每一条规则都对应一个非终结符表达式类。
 *  ■ 环境（Context）角色：该角色提供解释器之外的一些全局信息。
 *  ■ 客户端（Client）角色：该角色创建一个抽象语法树，调用解释操作。
 */
public class Interpreter {
}


class  Context{
    private HashMap map = new HashMap();
}
//抽象表达式
abstract class AbstractExpression{

    abstract Object interpreter(Context context);
}
class TerminalExpression extends AbstractExpression{

    @Override
    Object interpreter(Context context) {
        return null;
    }
}
class NonterminalExpression extends AbstractExpression{

    public NonterminalExpression(AbstractExpression expression){

    }

    @Override
    Object interpreter(Context context) {
        return null;
    }
}