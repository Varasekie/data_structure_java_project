package SeventhChar;

import SecondChar.SeqList;

public abstract class AbstractGraph<T> implements Graph<T> {
    protected static final int MAX_WEIGHT = 0x0000fffff;
    protected SeqList<T> vertexlist;//储存图的顶点集合

    public AbstractGraph() {
        this.vertexlist = new SeqList<>();
    }

    public AbstractGraph(SeqList<T> seqList) {
        this.vertexlist = seqList;
    }


    @Override
    public int vertexCount() {
        return vertexlist.size();
    }

    @Override
    public T get(int i) {
        return this.vertexlist.get(i);
    }

    @Override
    public String toString() {
        return "顶点集合" + this.vertexlist.toString();
    }

    @Override
    public void set(int i, T x) {
        this.vertexlist.set(i, x);
    }

    @Override
    public int insert(T x) {
        return this.vertexlist.insert(x);
    }

    @Override
    public void insert(int i, int j, int w) {

    }

    @Override
    public int search(T key) {
        return this.vertexlist.search(key);
    }

    @Override
    public T remove(int i) {
//        return this.vertexlist.remove(this.search(i));
        return this.vertexlist.remove(i);
    }

    public T remove(T obj){
        return this.vertexlist.remove(obj);
    }


    protected abstract int next(int i,int j);

    @Override
    public void remove(int i, int j) {

    }

    @Override
    public int weight(int i, int j) {
        return 0;
    }

    @Override
    public void DFSTraverse(int i) {

    }

    @Override
    public void BFSTraverse(int i) {

    }

    @Override
    public void minSpanTree() {

    }

    @Override
    public void shortestPath(int i) {

    }

    @Override
    public void shortestPath() {

    }
}
