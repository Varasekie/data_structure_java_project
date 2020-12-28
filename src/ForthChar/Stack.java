package ForthChar;

public interface Stack<T> {
    public abstract boolean isEmpty();
    public abstract void push(T x);//入栈
    public abstract T peek();//返回栈顶元素，未出栈
    public abstract T pop();//出栈，返回栈顶
}
