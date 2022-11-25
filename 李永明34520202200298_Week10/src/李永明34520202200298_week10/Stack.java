/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package 李永明34520202200298_week10;

/**
 * 利用栈实现表达式的计算 请从Calculator类的"源"中运行文件
 *
 * @author YongLer
 */
public class Stack {

    String express;

    public void stack(String s) {
        this.express = s;
    }

    public double main(String[] args) {

        int flag = 0;//用于后续判断

        //创建两个栈，一个数栈，一个符号栈
        ArrayStack numStack = new ArrayStack(20);
        ArrayStack charStack = new ArrayStack(20);

        int index = 0;//遍历所需索引
        double num1;
        double num2;
        double oper;//运算符
        double res;//计算结果
        char ch;//将每次拿到的符号放在ch中
        String keepNum = "";//用于拼接数字，因为会涉及到多位数
        //遍历输入的字符串
        while (true) {
            //依次取到字符串的每个字符
            ch = express.charAt(index);
            //判断ch是什么
            if (charStack.isOper(ch)) {//ch是否是运算符，是的话为true
                //先判断是否为减号，是的话需要单独处理
                if (charStack.isOper_(ch) == 1) {//说明是减号
                    ch = '+';
                    flag = 1;
                }
                //判断符号栈中是否有符号在栈
                if (!charStack.isEmpty()) {
                    //如果栈中有符号，则需要比较栈中的操作符的优先级与当前优先级，
                    //若当前操作符的优先级小于或等于栈中的，则需从数栈中pop出两个数值，将符号栈中的符号出栈进行计算
                    if (charStack.priority(ch) <= charStack.priority(charStack.peak())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = charStack.pop();
                        res = numStack.calRes(num1, num2, oper);
                        //将res重新入数栈
                        numStack.push(res);
                        //将当前的符号入符号栈
                        charStack.push(ch);
                    } else {
                        //若当前的符号的优先级要高于栈中的优先级，则直接入栈
                        charStack.push(ch);
                    }
                } else {//栈空，直接将运算符放入栈中
                    charStack.push(ch);
                }
            } else {//ch是数字或者小数点
                if (flag == 1) {
                    flag = 0;
                    keepNum = keepNum + '-' + ch;//把减号和后面的数字合并
                } else {
                    keepNum = keepNum + ch;//正常拼接数字
                }
                if (index == express.length() - 1) {//运算式到头了
                    numStack.push(Double.valueOf(keepNum));
                    keepNum = "";
                } else {
                    if (charStack.isOper(express.charAt(index + 1))) {//判断后一位是否为运算符
                        numStack.push(Double.valueOf(keepNum));//若下一位是运算符则直接入栈
                        keepNum = "";
                    }
                }
            }
            //往后进行遍历
            index++;
            if (index >= express.length()) {//终止条件
                break;
            }
        }
        //计算表达式遍历完毕，开始计算，计算的终止条件是仅剩一个运算数，输出数栈中的值（最终结果）
        while (true) {
            if (charStack.isEmpty() || (charStack.top==0&&numStack.top==0)) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = charStack.pop();
            res = numStack.calRes(num1, num2, oper);
            numStack.push(res);
        }
        //将数栈最后的数出栈
        double res2 = numStack.pop();
        return (res2);

    }
}

//创建一个栈
class ArrayStack {

    int maxSize;//栈的大小
    double[] stack;//数组栈
    int top = -1;//表示栈顶

    //构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new double[this.maxSize];
    }

    //返回当前栈顶的值
    public double peak() {
        return stack[top];
    }

    //栈满，满则返回true
    public boolean isFull() {
        return top == maxSize - 1;
    }

    //栈空
    public boolean isEmpty() {
        return top == -1;
    }

    //入栈
    public void push(double value) {
        //判断栈是否为满
        if (isFull()) {
            //System.out.println("栈满，无法插入");
            return;
        }
        top++;
        stack[top] = value;
    }

    public double pop() {
        //判断栈是否空,这里因为要涉及到取数据，故这里会有异常
        if (isEmpty()) {
            throw new RuntimeException("栈空，元素无法出栈");
        }
        double value = stack[top];
        top--;
        return value;
    }

    //优先级的定位
    public int priority(double oper) {
        int oper1 = (int) oper;
        if (oper1 == '×' || oper == '÷') {
            return 1;
        } else if (oper1 == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    //判断是否为一个运算符
    public boolean isOper(char ch) {
        return ch == '×' || ch == '÷' || ch == '+' || ch == '-';
    }

    //判断是否为减号
    public int isOper_(char ch) {
        if (ch == '-') {
            return 1;
        } else {
            return 0;
        }
    }

    //计算
    public double calRes(double num1, double num2, double oper) {
        int oper1 = (int) oper;
        double res = 0;//用来存放计算的结果

        switch (oper1) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '×':
                res = num1 * num2;
                break;
            case '÷':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}
