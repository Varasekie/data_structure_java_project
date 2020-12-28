package SeventhChar;

public interface Graph<T> {

    int vertexCount();//顶点数
    T get(int i );
    void set(int i ,T x);
    int insert(T x);//插入元素值，返回序号
    void insert(int i ,int j,int w);
    int search(T key);
    T remove(int i);
    void remove(int i,int j);
    int weight(int i ,int j);

    void DFSTraverse(int i);
    void BFSTraverse(int i);
    void minSpanTree();
    void shortestPath(int i);
    void shortestPath();

}
