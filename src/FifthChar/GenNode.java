package FifthChar;

public class GenNode<T> {
    public T data;
    public GenList<T> child;
    public GenNode<T> next;

    public GenNode(T data, GenList<T> child, GenNode<T> next) {
        this.data = data;
        this.child = child;
        this.next = next;
    }

    public GenNode() {
        this(null, null, null);
    }

    //深拷贝
    public GenNode(GenNode<T> node) {
        this(node.data, node.child, node.next);
    }

    public String toString() {
        return this.data.toString();
    }

}
