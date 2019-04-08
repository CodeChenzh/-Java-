package infixForm;

import java.util.Scanner;
import java.util.Stack;

public class output {
    private static input in = new input();
    public static void main(String[] args) {
        //String arithmetic = "6+(8-7)+5*6+9-12/2";   /*输入的表达式*/
        Scanner scanner = new Scanner(System.in);
        String arithmetic = scanner.next();
        in.analyze(arithmetic);
        operation();
    }

    public static void operation() {
        Stack<Integer> stack = new Stack<>();   /*用于存放数据*/

        for (int i = 0; i < in.intMap.size() + in.charMap.size(); i++) {    /*遍历两个集合*/
            if (in.intMap.get(i) == null) {
                switch (in.charMap.get(i)) {
                    case '-':   /*因为是减号，所以要考虑顺序*/
                        int one = stack.pop();
                        int two = stack.pop();
                        stack.push(two - one);
                        break;
                    case '+':
                        stack.push(stack.pop() + stack.pop());
                        break;
                    case '*':
                        stack.push(stack.pop() * stack.pop());
                        break;
                    case '/':   /*同减号的顺序*/
                        int first = stack.pop();
                        int second = stack.pop();
                        stack.push(second / first);
                        break;
                }
            } else {
                stack.push(in.intMap.get(i));
            }
        }
        System.out.print("结果是：" + stack.pop());

    }
}
