package SecondChar;

public class DoublyList<T> {
    DoubleNode<T> head;

    public DoublyList(){
        this.head = new DoubleNode<>();
        this.head.prev = this.head;
        this.head.next = this.head;
    }

    public boolean isEmpty(){
        return this.head.next == this.head;
    }

    public DoubleNode insert(T x,int i ){
        if (x == null){
            return null;
        }
        DoubleNode<T> front = this.head;
        for (int j = 0;front.next!=null&& j<i;j++){
            front = front.next;
        }

        DoubleNode q = new DoubleNode(x,front,front.next);
        front.next.prev = q;
        front.next = q;
        return q;
    }
}
