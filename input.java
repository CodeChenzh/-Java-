package infixForm;


import java.util.*;

class input {
    //public ArrayList<Character> rpnArr = new ArrayList<>(); //用于存储逆波兰式
    int flag = 0;   //用于记录存储的顺序
    public Map<Integer, Integer> intMap = new HashMap<>();  //用于存储数字
    public Map<Integer, Character> charMap = new HashMap<>();  //用于存储运算符
    private Map<Character, Integer> map = new HashMap<>();    /*用于存四则运算符号的优先级*/
    private ArrayList<Integer> sum = new ArrayList<>();   /*可能不是一位数字，那么就需要对每个数字线存储起来，遇到符号时就将存储起来的数字进行运算*/
    private Stack<Character> stack = new Stack<>();   /*存储四则运算的符号*/
    private int add = 0;



    /*对每个字符进行相应的处理*/
    public void analyze(String arithmetic) {
        /*定义四则运算符号的优先级*/
        map.put('+', 1);
        map.put('-', 1);
        map.put('*', 2);
        map.put('/', 2);
        map.put('(', 3);
        map.put(')', 3);

        for (int i = 0; i < arithmetic.length(); i++) {
            char nowChar = arithmetic.charAt(i);    /*取出第一个字符*/
            if (nowChar == '+' || nowChar == '-' || nowChar == '*' || nowChar == '/' || nowChar == '(' || nowChar == ')') { /*如果是运算符，就要准备入栈了*/
                if (sum.size() != 0) {    /*入栈之前要先将存储在sum里的数据输出出来*/
                    summation();
                }

                if (nowChar != ')' && (stack.empty() || map.get(nowChar) > map.get(stack.peek()) || stack.peek() == '(')) {    /*入栈*/
                    stack.push(nowChar);
                } else {
                    while (!stack.isEmpty() && nowChar >= map.get(stack.peek())) {  /*当栈不为空并且当前运算符的优先级不小于栈顶运算符时*/
                        if (stack.peek() == '(' || stack.peek() == ')') {   /*对于括号运行符不需要进行输出*/
                            stack.pop();
                            break;
                        }

                        charMap.put(flag++, stack.pop());
                    }

                    if (nowChar != ')') {       /*将当前运算符入栈，如果是右括号的话就不用入栈了，因为当遇到右括号是，栈里面必然有左括号，所以右括号要随着左括号一起丢掉*/
                        stack.push(nowChar);
                    }
                }
                continue;
            } else {    /*当不是运算符时，就进行数字的处理*/
                int num = Integer.valueOf(nowChar) - 48;    /*Integer.valueOf(nowChar)输出的是字符的ASCII码值，要将它转化为实际数字*/
                sum.add(num);   /*可能不是一位数，所以可以先将该数字存储起来*/
            }
        }

        if (sum.size() != 0) {  /*此时sum里面可能还会有存储起来的数字，但还没输出，此时要进行输出了*/
            for (int j = 0; j < sum.size(); j++) {
                summation();
            }
        }

        while (!stack.isEmpty()) {  /*和上面的原因一样*/
            charMap.put(flag++, stack.pop());
        }
    }

    /*将数组中的数字求和*/
    public void summation() {
        int pow = 0;
        for (int j = sum.size() - 1; j >= 0; j--) {
            add = (int) (add + sum.get(j) * Math.pow(10, pow++));   /*求幂运算*/
        }
        sum.clear();    /*清除ArrayList集合*/
        intMap.put(flag++, add);
        add = 0;    /*归零，不然下次会接着累加*/
    }
}
