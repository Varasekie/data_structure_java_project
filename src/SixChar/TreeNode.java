package SixChar;

public class TreeNode<T> {
    public T data;                               //数据域
    public TreeNode<T> parent, child, sibling;   //父母结点链、孩子结点链、兄弟结点链

    //构造结点，参数分别指定元素、父母结点、孩子结点和兄弟结点
    public TreeNode(T data, TreeNode<T> parent, TreeNode<T> child, TreeNode<T> sibling)
    {
        this.data = data;
        this.parent = parent;
        this.child = child;
        this.sibling = sibling;
    }
    public TreeNode(T data, TreeNode<T> parent)  //构造叶子结点，parent指向其父母结点
    {
        this(data, parent, null, null);
    }
    public TreeNode(T data)                      //构造叶子结点
    {
        this(data, null, null, null);
    }
    public String toString()                     //返回结点数据域的描述字符串
    {
        return this.data.toString();
    }

    public boolean isLeaf()                      //判断是否叶子结点
    {
        return this.child==null;
    }
}
/*
    //可声明以下方法
    public TreeNode()
    {
        this(null, 0, null, null, null);
    }
    public boolean equals(Object obj)            //比较两个结点值是否相等，覆盖Object类的equals(obj)方法
    {
        return obj==this || obj instanceof TreeNode<?> && this.data.equals(((TreeNode<T>)obj).data);
    }
*/