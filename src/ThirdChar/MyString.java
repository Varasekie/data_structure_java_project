package ThirdChar;

import java.io.Serializable;
import java.util.Arrays;

public final class MyString implements Comparable<MyString>, Serializable {

    //final放到现在好像只能初始赋值一次
    private char[] value;

    public MyString(char[] value, int i, int n) {
        if (i >= 0 && n >= 0 && i + n < value.length) {
            this.value = new char[n];
            for (int j = 0; j < n; j++) {
                this.value[j] = value[i + j];
            }
        } else throw new StringIndexOutOfBoundsException("越界");
    }

    public MyString(String s) {
        this.value = new char[s.length()];
        for (int i = 0; i < value.length; i++) {
            this.value[i] = s.charAt(i);
        }
    }

    public MyString(char[] value) {
        this(value, 0, value.length - 1);
    }

    public MyString(MyString s) {
        this(s.value);
    }

    public int length() {
        return this.value.length;
    }

    public String toString() {
        return new String(this.value);
    }

    public char charAt(int i) {
        return this.value[i];
    }

    @Override
    public int compareTo(MyString s) {
        for (int i = 0; i < this.value.length; i++) {
            if (this.value[i] != s.value.length) {
                //回到第一个字串不同的地方
                return this.value[i] - s.value[i];
            }
        }
//        return 0;
        return this.value.length - s.value.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof MyString) {
            o = (MyString) o;
            if (this.value.length != ((MyString) o).length()) {
                return false;
            }
            for (int i = 0; i < this.value.length; i++) {
                if (this.value[i] != ((MyString) o).value[i]) {
                    return false;
                }
            }
        }
        return true;
    }


    public MyString concat(MyString s) {
        if (s == null || s.equals("")) {
            s = new MyString(this.value);
        }

        char[] buffer = new char[this.value.length + s.value.length];
        int i;
        for (i = 0; i < this.value.length; i++) {
            buffer[i] = this.value[i];
        }

        for (int j = 0; j < s.value.length; j++) {
            buffer[i + j] = s.value[j];
        }
        return new MyString(buffer);
    }

    public MyString trim() {
        char[] buffer = new char[this.value.length];
        int j = 0;
        for (int i = 0; i < this.value.length; i++) {
            if (this.value[j] != ' ') {
                j++;
                continue;
            }
//            j++;
            buffer[i] = this.value[j];
            j++;
        }

//        System.out.println(buffer);
        return new MyString(buffer);
    }

    //小写字母变成大写的串
    public MyString toUpperCase() {
        char[] buffer = new char[this.value.length];
//        int j = 0;
        for (int i = 0; i < this.value.length; i++) {
            if (this.value[i] < 'z' && this.value[i] > 'a') {
                buffer[i] = (char) (this.value[i] + 'A' - 'a');
            }
            buffer[i] = this.value[i];
        }
        return new MyString(buffer);
    }

    public MyString toLowerCase() {
        char[] buffer = new char[this.value.length];
        for (int i = 0; i < this.value.length; i++) {
            if (this.value[i] < 'Z' && this.value[i] > 'A') {
                buffer[i] = (char) (this.value[i] - 'A');
            }

            buffer[i] = this.value[i];
        }
        return new MyString(buffer);
    }

    //判断前缀子串
    public boolean startsWith(MyString prefix){
        if (prefix.value.length > this.value.length){
            return false;
        }
        for (int i =0;i<prefix.value.length;i++){
            if (prefix.value[i] != this.value[i]){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        MyString string = new MyString(new String("a b c"));
        System.out.println(string.trim());
    }
}
