package EigthChar;

public class BinarySortTree <T extends Comparable<? super T>>  extends TriBinaryTree2<T>{
    public BinarySortTree()                      //构造空二叉排序树
    {
        super();                                 //this.root=null;
    }
    public BinarySortTree(T[] values)            //构造二叉排序树，由values数组提供元素
    {
        this();                                  //构造空二叉排序树
        for(int i=0; i<values.length; i++)       //插入values数组所有元素
            this.add(values[i]);                 //二叉排序树插入元素
    }

    //1. 查找操作
    //查找并返回与key相等元素结点，若查找不成功，返回null。////若key==null，Java抛出空对象异常
    //覆盖父类方法。
    //查找算法经过一条从根到结点的路径；查找不成功，遍历路径是从根到叶子。
    public TriNode<T> searchNode(T key)
    {
        TriNode<T> p=this.root;
        while(p!=null && key.compareTo(p.data)!=0)
        {
//            System.out.print(p.data+"? ");       //显示查找经过的结点值，可省略
            if(key.compareTo(p.data)<0)          //若key较小
                p=p.left;                        //进入左子树
            else
                p=p.right;                       //进入右子树
        }
        return p!=null ? p : null;               //若查找成功，返回结点，否则返回null
    }
    //说明
    //public TriNode<T> search(T key)  //不能声明，与例1.1 Set<T>接口声明不同，子类无法覆盖
    //public T search(T key)           //子类必需的，实现Set<T>接口


    //2.  插入操作
    //插入元素x结点，不插入空对象和相同元素，返回插入与否结果
    public boolean add(T x)
    {
        if(x==null)
            return false;                        //不插入空对象
        if(this.root==null)
            this.root = new TriNode<T>(x);       //创建根结点
        else                                     //将x插入到以root为根的二叉排序树中
        {
            TriNode<T> p=this.root, parent=null;
            while(p!=null)                       //查找x确定插入位置
            {
                if(x.compareTo(p.data)==0)
                    return false;                //查找成功，不插入相同元素
                parent = p;
                if(x.compareTo(p.data)<0)
                    p = p.left;
                else
                    p = p.right;
            }
            if(x.compareTo(parent.data)<0)       //插入x叶子结点作为parent的左/右孩子
                parent.left = new TriNode<T>(x, parent, null, null);
            else
                parent.right = new TriNode<T>(x, parent, null, null);
        }
        return true;
    }
    //【思考题8-4】能否直接调用查找算法确定插入位置？为什么？

    //5.  删除操作
    //删除与key相等元素结点，返回被删除元素；若没找到则不删除，返回null。//非递归算法，若key==null，Java抛出空对象异常
    public T remove(T key)
    {
        TriNode<T> p = this.searchNode(key);     //查找并返回与key相等元素结点，若查找不成功，则返回null
        if(p!=null && p.left!=null && p.right!=null)       //找到待删除结点p，若p是2度结点
        {
            TriNode<T> insucc = this.infixFirst(p.right);  //寻找p在中根次序下的后继结点insucc，见8.4.2节
            T temp = p.data;                     //交换待删除元素，作为返回值。////必须交换值
            p.data = insucc.data;                //以后继结点值替换p结点值
            insucc.data = temp;
            p = insucc;                          //转化为删除insucc，删除1、0度结点
        }

        if(p!=null && p==this.root)              //p是1度或叶子结点，删除根结点，p.parent==null
        {
            if(this.root.left!=null)
                this.root = p.left;              //以p的左孩子顶替作为新的根结点
            else
                this.root = p.right;             //以p的右孩子顶替作为新的根结点
            if(this.root!=null)
                this.root.parent = null;
            return p.data;                       //返回被删除根结点元素
        }

        if(p!=null && p==p.parent.left)          //p是1度或叶子结点，p是父母的左孩子
        {
            if(p.left!=null)
            {
                p.parent.left = p.left;          //以p的左孩子顶替
                p.left.parent = p.parent;        //p的左孩子的parent域指向p的父母
            }
            else
            {
                p.parent.left = p.right;         //以p的右孩子顶替
                if(p.right!=null)
                    p.right.parent = p.parent;
            }
        }

        if(p!=null && p==p.parent.right)         //p是1度或叶子结点，p是父母的右孩子
        {
            if(p.left!=null)
            {
                p.parent.right = p.left;         //以p的左孩子顶替
                p.left.parent = p.parent;
            }
            else
            {
                p.parent.right = p.right;        //以p的右孩子顶替
                if(p.right!=null)
                    p.right.parent = p.parent;
            }
        }
        return p!=null ? p.data : null;
    }

    //以下方法教材没写，为了测试
    public T removeRoot()                        //删除根结点，返回原根结点元素
    {
        return this.remove(this.root.data);
    }


    //【课程设计题8-4】输出平均查找长度ASL成功的计算公式及结果。
    //二叉树的层次遍历算法，使用队列，求结点层次level(p)方法见父类。
    public void printASL()
    {
        System.out.print("ASL成功=(");
//        SeqQueue<TriNode<T>> que=new SeqQueue<TriNode<T>>();  //创建空队列
        LinkedQueue<TriNode<T>> que=new LinkedQueue<TriNode<T>>();
        int asl=0, n=0, count=0, level=1;
        for(TriNode<T> p=this.root; p!=null; p=que.poll())   //按层次遍历二叉树，根结点没有入队
        {
            if(level(p)==level)                  //若p结点层次为当前层次，则计数
                n++;                            //当前层的结点个数
            else
            {
                System.out.print((asl==0 ? "" : "+")+level+"*"+n);//输出上一层的计算公式
                asl+=level*n;
                count+=n;                        //二叉树的结点个数
                level++;                         //深一层
                n=1;
            }
            if(p.left!=null)
                que.add(p.left);                 //p的左孩子结点入队
            if(p.right!=null)
                que.add(p.right);                //p的右孩子结点入队
        }
        if(count==0)
            System.out.println(") = 0");
        else
        {
            System.out.print("+"+level+"*"+n);   //最后一层
            asl+=level*n;
            count+=n;
            System.out.println(")/"+count+" ="+asl+"/"+count+" ="+((asl+0.0)/count));
        }
    }
    ////二叉树的层次遍历算法，也可使用2个队列，分别进行层次遍历和存储结点层次。

}
