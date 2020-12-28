package ForthChar;

import SecondChar.SinglyList;

public class LinkedStack<T> implements Stack<T> {
    private SinglyList<T> list;

    public LinkedStack(int length){
        this.list = new SinglyList<T>();
    }
    public LinkedStack(){
        this.list = new SinglyList<>();
    }
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void push(T x) {
        list.insert(0,x);
    }

    @Override
    public T peek() {
        return list.get(0);
    }

    @Override
    public T pop() {
        return this.list.remove(0);
    }
}
