package SecondChar;

public class DoubleNode<T> {
    public T data;
    public DoubleNode<T> prev,next;

    public DoubleNode(T data,DoubleNode<T> prev,DoubleNode<T> next){
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public DoubleNode(){
        this(null,null,null);
    }

    public String toString(){
        return data.toString();
    }
}
