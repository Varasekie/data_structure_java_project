package FifthChar;

public interface GenLList<T> {
    boolean isEmpty();
    int size();
    int depth();
    GenNode<T> get(int i);
    GenNode<T> insert(int i,T x);
    GenNode<T> insert(int i ,GenList<T> genList);
    GenNode<T> remove(int i);
    void clear();
    GenNode<T> search();
    GenNode<T> remove(T key);
}
