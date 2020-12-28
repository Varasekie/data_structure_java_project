package EigthChar;

public class TriNode <T>    {
    public T data;                               //数据域，存储数据元素
    public TriNode<T> parent, left, right;       //地址域，分别指向父母结点、左和右孩子结点

    //构造结点，参数分别指定元素、父母结点、左和右孩子结点
    public TriNode(T data, TriNode<T> parent, TriNode<T> left, TriNode<T> right)
    {
        this.data = data;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
    public TriNode(T data)                       //构造指定值的叶子结点
    {
        this(data, null, null, null);
    }
    public TriNode()//没用到？？
    {
        this(null, null, null, null);
    }

    public String toString()//没用到？？
    {
        return this.data.toString();
    }
    public boolean isLeaf()                      //判断是否叶子结点
    {
        return this.left==null && this.right==null;
    }
}
