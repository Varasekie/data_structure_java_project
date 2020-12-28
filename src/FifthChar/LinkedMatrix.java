package FifthChar;

import SecondChar.Node;
import SecondChar.SeqList;
import SecondChar.SortedSinglyList;

public class LinkedMatrix {
    private int rows, columns;
    SeqList<SortedSinglyList<Triple>> rowlist;

    public LinkedMatrix(int m, int n) {
        if (m > 0 && n > 0) {
            this.rows = m;
            this.columns = n;
            this.rowlist = new SeqList<SortedSinglyList<Triple>>(m);
            for (int i = 0; i < m; i++) {
                this.rowlist.insert(new SortedSinglyList<Triple>());
            }
        } else throw new IllegalArgumentException("矩阵行列数错误");
    }

    public LinkedMatrix(int n) {
        this(n, n);
    }

    public LinkedMatrix() {
        this(0, 0);
    }

    public LinkedMatrix(int rows, int columns, Triple[] triples) {
        this(rows, columns);
        this.rowlist = new SeqList<>(triples.length);
        for (int i = 0; i < triples.length; i++) {
            this.set(triples[i]);
        }
    }

    public void set(int i, int j, int x) {
        if (i > 0 && i < this.rows && j > 0 && j < this.columns) {
            //获得第i行排序单链表
            SortedSinglyList<Triple> list = this.rowlist.get(i);
            if (x == 0) {
                list.remove(new Triple(i, j, 0));//查找成功则删除
            } else {
                Triple triple = new Triple(i, j, x);
                Node<Triple> find = list.search(triple);
                if (find != null) {
                    find.data.value = x;
                } else list.add(triple);
            }
        } else throw new IndexOutOfBoundsException("序号越界");
    }

    public void set(Triple triple) {
        this.set(triple.row, triple.column, triple.value);
    }

    public int get(int i, int j) {
        if (i > 0 && i < this.rows && j > 0 && j < this.columns) {
            Node<Triple> find = this.rowlist.get(i).search(new Triple(i, j, 0));
            return (find != null) ? find.data.value : 0;
        } else throw new IndexOutOfBoundsException("序号越界ij");
    }
}
