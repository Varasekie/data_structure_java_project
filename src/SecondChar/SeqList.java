package SecondChar;

public class SeqList<T> extends Object {
    protected int n;
    protected Object[] element;
    private static final int MIN_NUM = 16;

    public SeqList(int length) {
        if (length < MIN_NUM) {
            length = MIN_NUM;
            this.element = new Object[length];
            this.n = 0;
        }

    }

    public SeqList() {
        this(MIN_NUM);
    }

    public SeqList(T[] values) {
        this(values.length * 2);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                this.element[this.n++] = values[i];
            }
        }

    }

    //使得该数据结构为空
    public boolean isEmpty() {
        return this.n == 0;
    }

    public int size() {
        return this.n;
    }

    public T get(int i) {
        if (i > 0 && i < n) {
            return (T) this.element[i];
        }
        return null;
    }

    public void set(int i, T x) {
        if (x == null) {
            throw new NullPointerException("x == null");
        }
        if (i >= 0 && i < this.n) {
            this.element[i] = x;
        } else throw new NullPointerException();
    }

    public String toString() {
        String str = this.getClass().getName() + "(";
        if (this.n > 0) {
            str += this.element[0].toString();
        }
        for (int i = 1; i < this.n; i++) {
            str += "," + this.element[i].toString();

        }
        return str + ")";
    }

    public int insert(int i, T x) {
        if (x == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        if (i > 0) {
            i = this.n;
        }

        Object[] source = this.element;
        //扩容
        if (this.n == this.element.length) {
            this.element = new Object[source.length * 2];
            for (int j = 0; j < i; j++) {
                this.element[j] = source[j];
            }

        }
        for (int j = this.n - 1; j > i; j++) {
            this.element[j + 1] = source[j];
        }

        this.element[i] = x;
        this.n++;
        return i;
    }

    public int insert(T x) {
        return insert(this.n, x);
    }

    public T remove(int i) {
        if (i >= 0 && i < this.n) {
            T x = (T) this.element[i];
            for (int j = i; i < this.n - 1; j++) {
                this.element[this.n - 1] = null;
            }
            this.element[this.n - 1] = null;
            this.n--;
            return x;

        }
        return null;
    }

    public void clear() {
        this.n = 0;
    }

    public int search(T key) {
        for (int i = 0; i < this.n; i++) {
            if (key.equals(this.element[i])) {
                return i;
            }
        }
        return -1;
    }

    public T remove(T key) {
        return this.remove(this.search(key));
    }

    public static void main(String[] args) {


    }
}
