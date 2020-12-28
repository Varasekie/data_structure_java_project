package ThirdChar;

public final class MyStringBuffer implements Comparable<MyStringBuffer> {
    private char[] value;
    private int n;//长度

    public MyStringBuffer(int capacity) {
        this.value = new char[capacity];
        this.n = capacity;
    }

    public MyStringBuffer() {
        this(16);
    }

    public MyStringBuffer(String s) {
        this(s.length() + 16);
        this.n = s.length();

        for (int i = 0; i < this.n; i++) {
            value[i] = s.charAt(i);
        }
    }

    public int length() {
        return this.n;
    }

    //返回字符数组容量
    public int capacity() {
        return this.value.length;
    }

    //以value数组从0到n构造字符串
    public synchronized String toString() {
        return new String(this.value, 0, this.n);
    }

    public synchronized char charAt(int n) {
        if (n < 0 || n > this.n) {
            return value[0];
        }

        return this.value[n];
    }

    public void setCharAt(int i, char ch) throws Exception {
        if (i < 0 || i > this.n) {
            throw new Exception("数组下标越界");
        }
        //需要考虑ch为空吗

        this.value[i] = ch;

    }

    public synchronized MyStringBuffer insert(int i, String s) {
        if (this.n == 0 && i == 0 || this.n > 0 && i >= 0 && i <= this.n) {
            if (s == null) {
                s = "";
            }

            //考虑扩容
            char[] temp = this.value;
            if (this.value.length + s.length() < this.n) {
                this.value = new char[(this.value.length + s.length()) * 2];
                for (int j = 0; j < temp.length; j++) {
                    this.value[j] = temp[j];
                }
            }

            //下面是移动串到后面去
            for (int j = i; j < n; j++) {
                this.value[j + s.length()] = this.value[j];
            }

            //插入串,重新赋值
//            System.out.println(s.length());
            for (int j = i; j < s.length() + i; j++) {
                this.value[j] = s.charAt(j - i);
            }
            this.n = n + s.length();
            return this;
        } else throw new StringIndexOutOfBoundsException("字符串越界");
    }

    @Override
    public int compareTo(MyStringBuffer s) {
        for (int i = 0; i < this.value.length && i < s.value.length; i++) {
            if (this.value[i] != s.value[i]) {
                //回到第一个字串不同的地方
                return this.value[i] - s.value[i];
            }
        }
//        return 0;
        return this.value.length - s.value.length;
    }

    public synchronized MyStringBuffer append(String s) {
        return this.insert(this.n, s);
    }

    //所有字串只移动一次，向前移动，
    public static StringBuffer removeAll(StringBuffer target, String pattern) {
        int n = target.length(), m = pattern.length();
        //next为一个标量，用来看后面还有没有indexof，没有就跳出去
        //indexof是返回第一个相等的字串
        int empty = target.indexOf(pattern), next = empty;
        //说明后面还有
        while (next != -1) {
            //末尾的坐标
            int move = next + m;
            next = target.indexOf(pattern, move);
            //把move到next-1的字串往前移动
            while (next > 0 && move < next || next < 0 && move < n) {
                target.setCharAt(empty++, target.charAt(move++));
            }
        }
        if (empty != -1) {
            target.setLength(empty);
        }
        //是直接改变了参数
        return target;
    }


    //作为removeAll复习的时候自己再写一遍，应当和上面的完全相同
    public static StringBuffer removeAll_test(StringBuffer stringBuffer, String pattern) {
        int m = stringBuffer.length(), n = pattern.length();

        //直接不用比较了
        if (m < n || n == 0) {
            return null;
        }
        int next = stringBuffer.indexOf(pattern);
        int empty = next;
        while (next != -1) {
            //move是记录第一个数要移动的距离
            int move = next + n;
            //下一位需要移动的地方
            next = stringBuffer.indexOf(pattern);
            //两种情况。第一个是只找到一个匹配的，next变成了-1，那就把所有的都移动
            //第二个是找到多个匹配的，直接移动
            while (move < next && next > 0 || move < n && next < 0) {
                stringBuffer.setCharAt(empty++, stringBuffer.charAt(move++));
            }
        }

        return stringBuffer;
    }

    //KMP
    //找首个和pattern匹配的字符串
    public static int getIndex(StringBuffer target, String pattern, int begin) {
        //容错
        if (begin < 0) {
            begin = 0;
        }
        if (begin >= target.length() || target.length() - begin < pattern.length()) {
            return -1;
        }

        int m = target.length(), n = pattern.length();
        //一个next数组存储
        int[] next = getNext(pattern);
        //i为匹配串，j为子串
        int i = begin, j = 0;
        while (i < target.length() && j < pattern.length()) {
            //如果相等， 就返回去
//            System.out.println(target.charAt(i));
            //注意==-1的要放在最前面，
            if (i == -1 || target.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                //注意目标串i不回溯！！！
//                i = i - j + 1;
                j = next[j];
                //如果剩余串小于字串长度，直接跳出
                if (m - i < n) {
                    break;
                }
            }
        }
        //这里可以用条件表达式
        //如果最终走完pattern，说明匹配了，如果没有……就说明没匹配成功
        if (j == pattern.length()) {
            return i - n;
        } else return -1;
    }

    //求next数组,next数组定长
    public static int[] getNext(String s) {
        int[] next = new int[s.length()];
        next[0] = -1;
        //j为前键，i为后键
        int j = -1, i = 0;

        while (i < s.length() - 1) {
            //先解决不相等的情况
            if (j == -1 || s.charAt(j) == s.charAt(i)) {
                j++;
                i++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }

        return next;
    }

    public synchronized static StringBuffer trim1(StringBuffer s)    {
        int i=0;
        while(i<s.length())
            if(s.charAt(i)==' ')
                s.delete(i, i+1);
            else  i++;
        return s;
    }

    public MyStringBuffer replaceAll(int begin, int end, String s) {
        return new MyStringBuffer("123336");
    }

    public static void main(String[] args) {
        MyStringBuffer str = new MyStringBuffer("123456");
//        str.insert(2, "4567");
//        System.out.println(str);
//        str.append("1234");
//        System.out.println(str);
//
//        StringBuffer s = new StringBuffer("123456123");
//        removeAll(s, "1");
//        System.out.println(s);
//        System.out.println(getIndex(new StringBuffer("123455"), "455", 3));
        ;

//        System.out.println(str.replaceAll(2, 5, "333"));

        StringBuffer stringBuffer = new StringBuffer("1   3");
        System.out.println(trim1(stringBuffer));
    }
}
