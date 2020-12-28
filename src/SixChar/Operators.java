package SixChar;

import java.util.HashMap;

public class Operators implements java.util.Comparator<String>{
    private String[] operator={"*","/","%", "+","-", "&","^","|"};//运算符集合，包含算术和位运算符
//    private int[] priority={3,3,3, 4,4, 8,9,10};  //上述各运算符的优先级，值小的优先级高，见附录D，与Java约定相同

    //【例4.2】 使用顺序表存储运算符集合，用作映射的背景。
/*    private SeqList<String> operlist;             //使用顺序表存储运算符集合
    public Operators()                            //构造方法
    {
        this.operlist = new SeqList<String>(this.operator);
    }

    public int compare(String oper1, String oper2)         //比较oper1、oper2运算符的优先级大小
    {
        int i=operlist.search(oper1),  j=operlist.search(oper2);
        return this.priority[i] - this.priority[j];
    }
*/
    //§8.5.2 散列映射【典型案例8-4】【例8.2】【课程设计题4-1】运算符散列映射
    private Integer[] priority={3,3,3, 4,4, 8,9,10};  //上述各运算符的优先级，值小的优先级高，见附录D，与Java约定相同
    private HashMap<String, Integer> opermap;         //运算符映射，映射元素是(运算符,优先级)
    //    private java.util.HashMap<String, Integer> opermap;         //运算符映射，映射元素是(运算符,优先级)
    public Operators()
    {
        opermap = new HashMap<String, Integer>();     //散列映射
//        opermap = new java.util.HashMap<String, Integer>(); //散列映射
        for(int i=0; i<operator.length; i++)
            opermap.put(operator[i], priority[i]);    //添加映射元素(关键字,值)

        System.out.println(opermap.getClass().getName()+" "+opermap);
//        opermap.printAll();
    }
    public int compare(String oper1, String oper2)    //比较oper1、oper2运算符的优先级大小
    {
        return opermap.get(oper1) - opermap.get(oper2);//获得Map中指定关键字映射的值
    }

    //两者共用
    public int operate(int x, int y, String oper)//返回x、y操作数进行oper运算结果
    {
        int value=0;
        switch(oper)                             //根据运算符分别计算
        {
            case "+":  value=x+y;  break;
            case "-":  value=x-y;  break;
            case "*":  value=x*y;  break;
            case "/":  value=x/y;  break;        //整除。若除数为0，抛出算术异常
            case "%":  value=x%y;  break;        //取余。若除数为0，抛出算术异常
            case "&":  value=x&y;  break;        //位与
            case "^":  value=x^y;  break;        //位异或
            case "|":  value=x|y;  break;        //位或
        }
        System.out.print(x+oper+y+"="+value+"，");
        return value;
    }
    public static void main(String args[])
    {
        new Operators();
    }
}
