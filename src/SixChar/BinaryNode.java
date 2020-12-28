package SixChar;

public class BinaryNode<T> {
    T data;
    public BinaryNode<T> left,right;
    public BinaryNode(){
        this(null,null,null);
    }
    public BinaryNode(T data){
        this(data,null,null);

    }
    public BinaryNode(BinaryNode<T> node){
        this.data = node.data;

    }
    public BinaryNode(T data,BinaryNode<T> left,BinaryNode<T> right){
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public String toString(){
        return this.data.toString();
    }

    public boolean isLeaf(){
        return left == null && right == null;
    }
}
