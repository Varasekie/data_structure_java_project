package Last;

public class LinkmatGraph<T> extends AbstractGraph<T> {
    protected SeqList<T> vertexlist;
    Linkmat<T> linkmat ;
    @Override
    protected int next(int i, int j) {
        return 0;
    }

    @Override
    public int insert(T x) {
        return 0;
    }

    @Override
    public void insert(int i, int j, int w) {

    }

    @Override
    public T remove(int i) {
        return null;
    }

    @Override
    public void remove(int i, int j) {

    }

    @Override
    public int weight(int i, int j) {
        return 0;
    }
}
