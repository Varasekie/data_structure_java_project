package ForthChar;

import SecondChar.SinglyList;

//检查是否有括号匹配问题
public class Match {

    //字符串
    public static String check(String target, String left, String right) {
        Stack<String> stack =  new SeqStack<>(target.length());

        int i = 0;
        char ch = ' ';

        while (i < target.length()) {
            while (i < target.length() && (ch = target.charAt(i)) != left.charAt(0) && ch != right.charAt(0)) {
                i++;
            }
            //indexOf查找在当中第一次出现的地方的索引，从i开始找left，未找到返回-1
            //如果left是一串字符串，就不能用charAt来比较了
            if (target.indexOf(left, i) == i) {
                stack.push(left);
                i = i + left.length();
            } else if (target.startsWith(right, i)) {
                //如果和right匹配,以上是两种方法，才能产生
                if (stack.isEmpty() || !stack.peek().equals(left)) {
                    return "语法错误";
                } else
                    if (stack.peek().equals(left)) {
                    stack.pop();
                    i = i + right.length();
                }
            }
        }
        return (stack.isEmpty() ? "匹配" : "语法错误。缺少" + left);
    }

    public static void main(String[] args) {
        String s = check("(helloworld))","(",")");
        System.out.println(s);
    }


}
