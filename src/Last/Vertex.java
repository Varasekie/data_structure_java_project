package Last;

//顶点节点
public class Vertex<T> {
    DoubleNode<Triple> head;
    //考虑triple就是用的int
    int ver;

    public Vertex(){
        this.head = new DoubleNode<>(null);
        ver = 0;
    }

    public Vertex(int ints){
        this.ver = ints;
    }
}
