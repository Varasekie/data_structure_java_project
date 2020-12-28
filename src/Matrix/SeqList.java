package Matrix;

public class SeqList<T> extends Object
{
    protected Object[] element;
    protected int n;
    public SeqList(int length)
    {
        this.element=new Object[length];
        this.n=0;
    }
    public SeqList()
    {
        this(64);
    }
    public SeqList(T[] values) {
        this(values.length);

        for(int i = 0; i < values.length; ++i) {
            this.element[i] = values[i];
        }

        this.n = this.element.length;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public int size() {
        return this.n;
    }

    public T get(int i) {
        return i >= 0 && i < this.n ? (T)this.element[i] : null;
    }

    public void set(int i, T x) {
        if (x == null) {
            throw new NullPointerException("x==null");
        } else if (i >= 0 && i < this.n) {
            this.element[i] = x;
        } else {
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
    }

    public String toString() {
        String str = this.getClass().getName() + "(";
        if (this.n > 0) {
            str = str + this.element[0].toString();
        }

        for(int i = 1; i < this.n; ++i) {
            str = str + ", " + this.element[i].toString();
        }

        return str + ") ";
    }

    public String toPreviousString() {
        return "";
    }

    public int insert(int i, T x) {
        if (x == null) {
            throw new NullPointerException("x==null");
        } else {
            if (i < 0) {
                i = 0;
            }

            if (i > this.n) {
                i = this.n;
            }

            Object[] source = this.element;
            int j;
            if (this.n == this.element.length) {
                this.element = new Object[source.length * 2];

                for(j = 0; j < i; ++j) {
                    this.element[j] = source[j];
                }
            }

            for(j = this.n - 1; j >= i; --j) {
                this.element[j + 1] = source[j];
            }

            this.element[i] = x;
            ++this.n;
            return i;
        }
    }

    public int insert(T x) {
        return this.insert(this.n, x);
    }

    public T remove(int i) {
        if (this.n > 0 && i >= 0 && i < this.n) {
            T old = (T)this.element[i];

            for(int j = i; j < this.n - 1; ++j) {
                this.element[j] = this.element[j + 1];
            }

            this.element[this.n - 1] = null;
            --this.n;
            return old;
        } else {
            return null;
        }
    }

    public void clear() {
        this.n = 0;
    }

    public int search(T key) {
        for(int i = 0; i < this.n; ++i) {
            if (key.equals(this.element[i])) {
                return i;
            }
        }

        return -1;
    }
}