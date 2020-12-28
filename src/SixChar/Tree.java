package SixChar;

public class Tree<T> {
    public TreeNode<T> root;                     //根结点，树的父母孩子兄弟链表结点类型

    public Tree()                                //构造空树
    {
        this.root=null;
    }
    public boolean isEmpty()                     //判断是否空树
    {
        return this.root==null;
    }

    //3.  树的遍历
    //树的先根和后根次序遍历算法
    public void preorder()                       //先根次序遍历树，算法同二叉树。//第5版教材未给出
    {
        System.out.print("先根次序遍历树/森林：  ");
        preorder(root);
        System.out.println();
    }
    public void preorder(TreeNode<T> p)         //先根次序遍历以p为根的子树，递归算法
    {
        if(p!=null)
        {
            System.out.print(p.data+" ");
            preorder(p.child);                   //先遍历孩子子树，递归调用
            preorder(p.sibling);                 //再遍历兄弟子树，递归调用
        }
    }

    //第5版教材写
    //先根次序遍历树，算法先根次序递归遍历子树，循环遍历兄弟链。    ////图的深度优先搜索遍历算法
    public void preorder2()
    {
        System.out.print("先根次序遍历树/森林：  ");
        for(TreeNode<T> p=this.root;  p!=null;  p=p.sibling) //p循环遍历根的兄弟链，森林
            preorder2(p);                        //遍历以p结点为根的一棵树
        System.out.println();
    }
    public void preorder2(TreeNode<T> p)         //先根次序遍历以p为根的子树，递归算法
    {
        if(p!=null)
        {
            System.out.print(p.data+" ");
            for(TreeNode<T> q=p.child;  q!=null;  q=q.sibling)//q循环遍历p.child的兄弟链
                preorder2(q);                    //先根次序遍历p的一棵子树，递归调用
        }
    }

    public void postorder()                      //后根次序遍历树
    {
        System.out.print("后根次序遍历树/森林：  ");
        postorder(root);
        System.out.println();
    }
    public void postorder(TreeNode<T> p)        //后根次序遍历以p为根的子树，递归算法，算法同二叉树的中根次序遍历
    {
        if(p!=null)
        {
            postorder(p.child);                  //遍历p的所有子树
            System.out.print(p.data+" ");
            postorder(p.sibling);                //遍历p的兄弟子树
        }
    }

    public int size()                            //返回树的结点个数，算法同二叉树
    {
        return size(root);
    }
    public int size(TreeNode<T> p)               //返回以p结点为根的子树的结点个数
    {
        if(p==null)
            return 0;
        return 1+size(p.child)+size(p.sibling);
    }

    //4. 树的横向凹入表示法
    //（2） 先根次序遍历树，输出树的横向凹入表示
    public String toString()                     //返回树的横向凹入表示字符串，以先根次序遍历树
    {
        return "树/森林的横向凹入表示： \n "+toString(root, "");
    }
    //先根次序遍历以p为根的子树，tab指定缩进量，返回子树的横向凹入表示字符串，递归算法
    public String toString(TreeNode<T> p, String tab)
    {
        if(p==null)
            return "";
        return tab+p.data.toString()+"\n" + toString(p.child, tab+"\t")
                + toString(p.sibling, tab);
    }
    //【例6.6】   以树的横向凹入表示构造一棵城市树/森林。
}
