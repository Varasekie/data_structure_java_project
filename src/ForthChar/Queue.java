package ForthChar;

public interface Queue<T> {
    public abstract boolean isEmpty();
    public abstract boolean add();
    public abstract T peek();//返回队头元素，无删除
    public abstract T poll();
}
