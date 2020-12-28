package SixChar;



import ForthChar.SeqStack;
import ForthChar.Stack;

import java.util.HashMap;

public class ExpressionBinaryTree extends BinaryTree<String>{
    private static Operators operators;          //运算符集合，见例4.2

    static
    {
        operators = new Operators();
    }
    public ExpressionBinaryTree()                //构造空二叉树
    {
        super();
    }

    //1.  表达式二叉树定义，遍历
//  public void preorder()                       //继承，输出前缀表达式
//  public void postorder()                      //继承，输出后缀表达式
    //覆盖，输出中缀表达式（有括号），中根次序遍历。算法必须比较运算符优先级的大小
    public void inorder()
    {
        inorder(this.root, null);
        System.out.println();
    }
    //中根次序遍历以p结点为根的子树，parent是p的父母结点，递归方法。
    //比较运算符的优先级大小确定是否要输出括号
    private void inorder(BinaryNode<String> p, BinaryNode<String> parent)
    {
        if(p!=null)
        {
            boolean bracket = false;             //是否要加括号的信号量
            if(!p.isLeaf() && parent!=null)
                //若孩子结点运算符的优先级低，或右子树为同级运算符，则要加括号
                bracket = operators.compare(p.data, parent.data)>0 ||
                        p==parent.right && operators.compare(p.data, parent.data)==0;
            if(bracket)
                System.out.print("(");
            inorder(p.left, p);                  //中根次序遍历p的左子树，递归调用
            System.out.print(p.data.toString()+" ");
            inorder(p.right, p);                 //中根次序遍历p的左子树，递归调用
            if(bracket)
                System.out.print(")");
        }
    }

    //返回中缀表达式（有括号），中根次序遍历，覆盖。算法必须比较运算符优先级的大小
    public String toString()
    {
        return toString(this.root, null);
    }
    //返回以p结点为根子树的中缀表达式（有括号），parent是p的父母；中根次序遍历，递归算法
    private String toString(BinaryNode<String> p, BinaryNode<String> parent)
    {
        if(p==null)
            return "";
        String str = toString(p.left, p)+p.data.toString()+toString(p.right, p);  //中根次序遍历
        if(!p.isLeaf() && parent!=null && operators.compare(p.data, parent.data)>0)
            return "("+str+")";                  //若孩子结点运算符的优先级低，则要加括号
        return str;
    }

    //2．表达式二叉树的构造算法
    //（1） 由前缀、后缀表达式构造的递归算法
    private int i=0;
    public void createByPrefix(String prefix)    //以前缀表达式prefix构造，递归算法
    {
        this.i=0;                                //第0个元素是根
        this.root = createSubtreeByPrefix(prefix);
    }
    //以从prefix的第i个字符开始向后的前缀表达式子串构造子树，返回子树的根，递归算法
    private BinaryNode<String> createSubtreeByPrefix(String prefix)
    {
        BinaryNode<String> p=null;
        if(i<prefix.length())
        {
            char ch=prefix.charAt(this.i);  this.i++;
            p = new BinaryNode<String>(ch+"");             //创建结点
            if(!(ch>='0' && ch<='9' || ch>='A' && ch<='Z'))//非叶子，ch是运算符
            {
                p.left = createSubtreeByPrefix(prefix);    //创建左子树，递归调用
                p.right = createSubtreeByPrefix(prefix);   //创建右子树，递归调用
            }
        }
        return p;
    }

    //以后缀表达式构造表达式二叉树，递归算法
    //算法与前缀表达式构造相同，次序相反，从右向左，先创建右子树；转换成先根次序
    public void createByPostfix(String postfix)
    {
        this.i=postfix.length()-1;               //最后一个元素是根
        this.root = createSubtreeByPostfix(postfix);
    }
    //以从postfix的第i个字符开始向左的后缀表达式子串构造子树，返回子树的根，递归算法
    private BinaryNode<String> createSubtreeByPostfix(String postfix)
    {
        BinaryNode<String> p=null;
        if(this.i>=0)
        {
            char ch=postfix.charAt(this.i);  this.i--;
            p = new BinaryNode<String>(ch+"");             //创建结点
            if(!(ch>='0' && ch<='9' || ch>='A' && ch<='Z'))//非叶子，ch是运算符
            {
                p.right = createSubtreeByPostfix(postfix);  //创建右子树，递归调用
                p.left = createSubtreeByPostfix(postfix);   //创建左子树，递归调用
            }
        }
        return p;
    }

    //（2） 由后缀表达式构造的非递归算法
    //以后缀表达式构造表达式二叉树，非递归算法，使用栈
    //使用栈存储之前创建子树的根，遇到数值创建叶子结点入栈；
    //遇到运算符创建2度结点，出栈两个棵树作为左右孩子，当前根再入栈。
    //只有遇到运算符，才能创建一棵子树。需要暂时存储无法确定关系的多个数值。因此，递归算法不行。
    public void createByPostfixStack(String postfix)
    {
        Stack<BinaryNode<String>> stack=new SeqStack<BinaryNode<String>>();   //创建空栈
//        Stack<BinaryNode<String>> stack=new LinkedStack<BinaryNode<String>>();//创建空栈
        BinaryNode<String> p=null;
        int i=0;
        while(i<postfix.length())
        {
            char ch=postfix.charAt(i);  i++;
            p = new BinaryNode<String>(ch+"");             //创建结点，叶子结点
            if(!(ch>='0' && ch<='9' || ch>='A' && ch<='Z'))//非叶子，ch是运算符
            {
                p.right = stack.pop();                     //创建右子树
                p.left = stack.pop();                      //创建左子树
            }
            stack.push(p);                                 //结点入栈
            System.out.println(stack.toString());
            //注意：此处，BinaryNode<T>类必须实现toString()方法，否则输出Object类的toString()方法。
        }
        this.root = p;
    }

    //（3） 由中缀表达式构造的非递归算法
    //以中缀表达式构造表达式二叉树，非递归算法，使用栈，有括号。
    //创建运算符结点栈，存储之前创建子树的根结点，用于改变运算符的次序。左括号入栈。
    //只用一个运算符栈，因为，要改变运算符的次序；即使没有括号，也要运算符栈。
    public ExpressionBinaryTree(String infix)
    {
        Stack<BinaryNode<String>> stack=new SeqStack<BinaryNode<String>>();   //结点栈
//        Stack<BinaryNode<String>> stack=new LinkedStack<BinaryNode<String>>();//创建空栈
        BinaryNode<String> p=null, child=null;
        int i=0;
        while(i<infix.length())
        {
            char ch=infix.charAt(i);  i++;
            if(ch>='0' && ch<='9' || ch>='A' && ch<='Z')   //若ch是操作数，或变量，
                child = new BinaryNode<String>(ch+"");     //则创建叶子结点，不入栈
            else if(ch=='(')                               //若ch是运算符和括号（左括号），
                stack.push(new BinaryNode<String>(ch+"")); //则创建结点入栈
            else
            {
                //当栈不空、且ch比栈顶运算符优先级低或同级时，出栈；
                //若ch是右括号，出栈，直到出栈运算符为左括号，匹配
                while(!stack.isEmpty() && (ch==')' ||
                        !stack.peek().data.equals("(") && operators.compare(ch+"", stack.peek().data)>=0))
                {
                    p = stack.pop();                       //出栈
                    if(p.data.equals("("))                 //出栈运算符为左括号，匹配
                        break;
                    else
                    {
                        p.right = child;                   //建立右孩子链
                        child = p;                         //child指向其父母，向上一层；必须，一棵子树也会成为右子树
                    }
                }
                if(ch!=')')
                {
                    p=new BinaryNode<String>(ch+"", child, null);//创建运算符结点，child是p的左孩子
                    stack.push(p);                        //运算符结点入栈
                }
            }
//            System.out.print("中缀表达式, child->"+(child==null?"null":child.data)+
//            		"，p->"+(p==null?"null":this.toString(p, null))+"，");
//            System.out.println(stack.toString());
            //注意：此处，BinaryNode<T>类必须实现toString()方法，否则输出Object类的toString()方法。
        }
        while(!stack.isEmpty())                  //中缀表达式结束，出栈所有运算符，建立右孩子链
        {
            p = stack.pop();
            p.right = child;
            child = p;
        }
        this.root = child;                       //最后出栈的运算符结点是根
    }


    //3.  计算表达式值
    //（1）计算算术表达式，设操作数类型是整数，返回整数值
    //以下方法，整数只能个位数，因为构造时只用大写字母表示变量
    public int toValue()
    {
        return this.toValue(this.root);
    }
    private int toValue(BinaryNode<String> p)    //计算以p结点为根的子树，后根次序遍历递归算法
    {
        if(p==null)
            return 0;
        if(p.isLeaf())                           //叶子结点
            return Integer.parseInt(p.data);     //返回值
        int left = toValue(p.left);
        int right = toValue(p.right);
        return operators.operate(left, right, p.data);   //根据运算符分别计算，后根次序遍历
    }

    //（2）计算算术表达式，设操作数是变量，从“变量取值表”中取值
    //默认变量集合是{"A","B","C","D","E","F","G","H","I"}，单字母且从A开始递增；
    //values指定取值集合
    public int toValue(int[] values)
    {
        return this.toValue(this.root, values);
    }
    //计算以p结点为根的子树，后根次序遍历递归算法
    private int toValue(BinaryNode<String> p, int[] values)
    {
        if(p==null)
            return 0;
        if(p.isLeaf())                           //叶子结点
            return values[p.data.charAt(0)-'A']; //从“变量取值表”中取值，返回值
        int left = toValue(p.left, values);
        int right = toValue(p.right, values);
        return operators.operate(left, right, p.data);   //根据运算符分别计算，后根次序遍历
    }

    //（3）计算算术表达式，设操作数是变量，从“变量取值表”中取值；
    //vars、values指定变量集合和取值集合，
    //使用顺序表存储变量集合，图6.41(b)
/*    public int toValue(String[] vars, int[] values)
    {
        //使用顺序表存储变量集合，因为要使用顺序表的顺序查找算法
        SeqList<String> varlist = new SeqList<String>(vars);
        return this.toValue(this.root, varlist, values);
    }
    //计算以p结点为根的子树，后根次序遍历递归算法
    //由SeqList<String>和int[]组成“变量取值表”
    private int toValue(BinaryNode<String> p, SeqList<String> varlist, int[] values)
    {
        if(p==null)
            return 0;
        if(p.isLeaf())                           //叶子结点
            //从“变量取值表”中取值，即使用顺序表的顺序查找算法，……，返回值
            return values[varlist.search(p.data)];
        int left = toValue(p.left);
        int right = toValue(p.right);
        return operators.operate(left, right, p.data);   //根据运算符分别计算，后根次序遍历
    }*/

    //（4）计算算术表达式，设操作数是变量，从“变量取值表”中取值；
    //vars、values指定变量集合和取值集合
    //使用散列映射存储“变量取值表”
    public int toValue(String[] vars, int[] values)
    {
        //变量取值散列映射映射，映射元素是(变量,取值)
        HashMap<String, Integer> varmap = new HashMap<String, Integer>();
        for(int i=0; i<vars.length; i++)
            varmap.put(vars[i], values[i]);      //添加映射元素(关键字,值)
        System.out.println(varmap.getClass().getName()+" "+varmap);
//        varmap.printAll();
        return this.toValue(this.root, varmap);
    }
    //计算以p结点为根的子树，后根次序遍历递归算法
    //由HashMap存储“变量取值表”
    private int toValue(BinaryNode<String> p, HashMap<String, Integer> varmap)
    {
        if(p==null)
            return 0;
        if(p.isLeaf())                           //叶子结点
            //从“变量取值表”中取值，即使用顺序表的顺序查找算法，……，返回值
            return varmap.get(p.data);
        int left = toValue(p.left);
        int right = toValue(p.right);
        return operators.operate(left, right, p.data);   //根据运算符分别计算，后根次序遍历
    }
}
