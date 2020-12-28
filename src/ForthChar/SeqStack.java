package ForthChar;

import SecondChar.SeqList;

//用list的末尾当栈顶，这样复杂度是O（1）
public class SeqStack<T> implements Stack<T> {
    private SeqList<T> list = new SeqList<T>(64);

    public SeqStack(int length) {
        this.list = new SeqList<T>(length);
    }

    public SeqStack() {
        this(64);
    }


    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void push(T x) {
        //尾插入
        this.list.insert(x);
    }

    //返回栈顶元素
    @Override
    public T peek() {
        return this.list.get(list.size()-1);
    }

    @Override
    public T pop() {
//        return null;
        return this.list.remove(list.size()-1);
    }

}
