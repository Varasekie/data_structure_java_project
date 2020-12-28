package EigthChar;

public class TriBinaryTree2 <T> {
    public TriNode<T> root;                      //根结点，三叉链表结点结构

    public TriBinaryTree2()                      //构造空二叉树
    {
        this.root = null;
    }
    public boolean isEmpty()                     //判断是否空二叉树
    {
        return this.root==null;
    }
    public void clear()                          //删除二叉树的所有结点
    {
        this.root = null;                        //Java自动释放不再被使用的存储单元
    }

    //以下二叉树（三叉链表存储）的迭代遍历

    //以中根次序迭代遍历二叉树，输出所有结点元素，以空格分隔
//    public void inorder()
//    {
//        System.out.print("中根次序迭代遍历二叉树：");
//        for(TriNode<T> p=this.infixFirst(this.root);  p!=null;  p=this.infixNext(p))//中根次序迭代遍历
//            System.out.print(p.data.toString()+" ");
//    }////多一个空格

    public void inorder()              //以中根次序迭代遍历二叉树，输出所有结点元素，形式为“(,)”
    {
        System.out.print("(");
        TriNode<T> p = this.infixFirst(this.root); //寻找第一个访问结点
        if(p!=null)
        {
            System.out.print(p.data.toString());
            p = this.infixNext(p);                 //返回p在中根次序下的后继结点
        }
        for(;  p!=null;  p=this.infixNext(p))
            System.out.print(", "+p.data.toString());
        System.out.println(")");
    }

    public TriNode<T> infixFirst()
    {
        return this.infixFirst(this.root);
    }
    //在以p为根的子树中，返回中根次序下第一个访问结点，即是p最左边的子孙结点，二叉排序树的最小值
    public TriNode<T> infixFirst(TriNode<T> p)
    {
        if(p!=null)
            while(p.left!=null)
                p = p.left;
        return p;
    }
    public TriNode<T> infixNext(TriNode<T> p)    //返回p在中根次序下的后继结点
    {
        if(p!=null)
        {
            if(p.right!=null)                    //若p有右孩子，
                return this.infixFirst(p.right); //则p的后继是其右子树上第一个访问结点
            while(p.parent!=null)                //若p没有右孩子，向上寻找某个祖先结点
            {
                if(p.parent.left==p)             //若p是其父母的左孩子，则p的后继是其父母
                    return p.parent;
                p=p.parent;
            }////写不了for
        }
        return null;
    }

    //以下第5版教材没写
    //返回中根次序遍历二叉树所有结点的描述字符串，以空格分隔
    public String toString()
    {
        String str="(";
        for(TriNode<T> p=this.infixFirst(this.root);  p!=null;  p=this.infixNext(p))
            str += p.data.toString()+" ";
        return str+")";
    }

    //以下【实验题8-2】
    //中根次序遍历二叉树（逆序），输出所有结点元素，以空格分隔
    //迭代算法从中根次序下最后一个访问结点（二叉排序树最大值）开始，依次到达前驱结点再访问。
    public void inorderPrevious()
    {
        System.out.print("(");
        for(TriNode<T> p=this.infixLast(this.root);  p!=null;  p=this.infixPrevious(p))
            System.out.print(p.data.toString()+" ");
        System.out.println("]");
    }
    //在以p为根的子树中，返回中根次序遍历的最后一个结点，即p最右边的子孙结点
    public TriNode<T> infixLast(TriNode<T> p)
    {
        if(p!=null)
            while(p.right!=null)
                p = p.right;
        return p;
    }
    public TriNode<T> infixPrevious(TriNode<T> p)//返回p在中根次序下的前驱结点
    {
        if(p!=null)
        {
            if(p.left!=null)                     //若p有左孩子，
                return this.infixLast(p.left);   //则p的前驱是其左子树最后一个访问结点
            while(p.parent!=null)                //若p没有左孩子，向上寻找某个祖先结点
            {
                if(p.parent.right==p)            //若p是其父母的右孩子，则p的前驱是其父母
                    return p.parent;
                p=p.parent;
            }
        }
        return null;
    }

    //使用中根迭代。使用先根迭代？？
    public int size()                           //返回二叉树的结点个数
    {
        int i=0;
        for(TriNode<T> p=this.infixLast(this.root);  p!=null;  p=this.infixPrevious(p))
            i++;
        return i;
    }

    public TriNode<T> preNext(TriNode<T> p)           	//返回p在先根次序下的后继结点
//？？    public TriNode<T> infixPrevious(TriNode<T> p)//返回p在中根次序下的前驱结点
    {
        if(p!=null)
        {
            if(p.left!=null)                     //若p有左孩子，
                return this.infixLast(p.left);   //则p的前驱是其左子树最后一个访问结点
            while(p.parent!=null)                //若p没有左孩子，向上寻找某个祖先结点
            {
                if(p.parent.right==p)            //若p是其父母的右孩子，则p的前驱是其父母
                    return p.parent;
                p=p.parent;
            }
        }
        return null;
    }


    //【课程设计题8-】
    //先根次序迭代遍历二叉树。////非递归算法，不使用栈，没有调用迭代方法，不声明front
    public void preorder()
    {
        System.out.print("先根次序迭代遍历二叉树：");
        TriNode<T> p=this.root, front=null;      //front记住p在后根次序下的前驱，区分从左/右子树返回
        boolean leftChild=true;                  //leftChild区分左/右遍历方向，默认先左后右
        while(p!=null)
            if(leftChild)                        //遍历p的左子树
            {
                System.out.print(p.data.toString()+" ");
//                front=p;                       //front记住p在先根次序下的前驱，不行判断时两者太远
                if(p.left!=null)
                    p = p.left;
                else
                    leftChild=false;
            }
            else if(p.right!=null && front!=p.right)      //若后根次序下的前驱不是p的右孩子（有），
            {   p = p.right;                           //遍历p的右子树
                leftChild=true;
            }
            else                   //p是叶子或右子树返回，向上返回父母，再遍历父母的右子树
            {
                //front记住p在后根次序下的前驱，即前次经过的结点，识别从左/右子树返回
                front=p;
                p=p.parent;
                leftChild=false;
            }
        System.out.println();
    }

    //查找与key相等元素，若查找成功，返回结点，否则返回null。
    //本类若实现，则采用先根遍历算法同二叉链表的二叉。由子类提供其他算法实现
    public TriNode<T> searchNode(T key)
    {
        return null;
    }

    //返回key结点所在的层次，令根结点的层次为1，若空树或未查找到x返回0
    public int level(T key)
    {
        return level(this.searchNode(key));
    }
    public int level(TriNode<T> p)               //返回p结点所在的层次
    {
        int level = 0;
        while(p!=null)
        {   level++;
            p=p.parent;
        }
        return level;
    }

    //【课程设计题8-5】
    //深拷贝bitree二叉树。//先根次序遍历二叉树，非递归算法，不使用栈，没有调用迭代方法。
    //算法声明leftChild区分创建的结点是p的左/右孩子；声明front记住p在后根次序下的前驱，区分从左/右子树返回
    public TriBinaryTree2(TriBinaryTree2<T> bitree)
    {
        this.root = null;
        if(bitree.isEmpty())
            return;
        TriNode<T> p=bitree.root, front=null;    //front记住p在后根次序下的前驱，区分从左/右子树返回
        boolean leftChild=true;                  //leftChild区分左/右遍历方向，默认先左后右
        this.root = new TriNode<T>(p.data);
        TriNode<T> q=this.root;
        while(p!=null)
            if(leftChild)                       //遍历p的左子树
                if(p.left!=null)
                {
                    p=p.left;                    //进入左子树
                    q.left = new TriNode<T>(p.data, q, null, null);//创建q的左孩子结点
                    q=q.left;
                }
                else
                    leftChild=false;
            else if(p.right!=null && front!=p.right) //若后根次序下的前驱不是p的右孩子（有），
            {
                p=p.right;                   //进入右子树
                q.right = new TriNode<T>(p.data, q, null, null);//创建q的右孩子结点
                q=q.right;
                leftChild = true;            //之后将创建q的左孩子
            }
            else                   //p是叶子或右子树返回，向上返回父母，再遍历父母的右子树
            {
                //front记住p在后根次序下的前驱，即前次经过的结点，识别从左/右子树返回
                front=p;
                p=p.parent;
                q=q.parent;
                leftChild=false;
            }
    }
}
