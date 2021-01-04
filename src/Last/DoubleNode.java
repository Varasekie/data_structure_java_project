package Last;

public class DoubleNode<T> {
    public T data;
    public DoubleNode<T> down;
    public DoubleNode<T> next;
    public boolean visited = false;

    public DoubleNode(T data, DoubleNode<T> down, DoubleNode<T> next) {
        this.data = data;
        this.down = down;
        this.next = next;
    }

    public DoubleNode() {
        this(null, null, null);
    }

    public DoubleNode(T data, DoubleNode<T> next)            // 构造结点，data指定数据元素，next指定后继结点
    {
        this.data = data;                        // T对象引用赋值
        this.next = next;                        // Node<T>对象引用赋值
    }

    public DoubleNode(T data) {
        this.data = data;
        this.down = null;
        this.next = null;
    }

    public String toString() {
        return data.toString();
    }
}
