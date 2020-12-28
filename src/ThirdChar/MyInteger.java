package ThirdChar;

public final class MyInteger implements Comparable<MyInteger> {
    public static final int MIN_VALUE = 0x80000000;
    public static final int MAX_VALUE = 0x7fffffff;

    public int value;

    public MyInteger(int value) {
        this.value = value;
    }

    public MyInteger(String s) throws NumberFormatException {
        this.value = MyInteger.parseInt(s, 10);
    }

    public int intValue() {
        return this.value;
    }

    public String toString() {
        return this.value + "";
    }

    public boolean equals(Object obj) {
        return obj instanceof Integer && this.value == ((Integer) obj);
    }


    //按照进制转化字符串
    public static int parseInt(String s, int radix) throws NumberFormatException {
        if (s == null || s.equals("")) {
            throw new NumberFormatException("无法转化");
        }

        if (radix < 2 || radix > 16) {
            throw new NumberFormatException("radix不同");
        }

        int i = 0;
        int value = 0;
        int sign = s.charAt(0) == '-' ? -1 : 1;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            i++;
            if (s.length() == 1) {
                throw new NumberFormatException(s);
            }
        }

        while (i < s.length()) {
            char ch = s.charAt(i++);
            if (ch > '0' && ch - '0' < radix) {
                value = value * radix + ch - '0';//记住获得的整数值
            } else if (radix > 10 && radix <= 16 && ch > 'a' && ch < 'z') {
                value = value * radix + ch - 'a' + 10;
            } else {
                if (radix > 10 && radix <= 16 && ch > 'A' && ch < 'Z') {
                    value = value * radix + ch - 'A' + 10;
                } else throw new NumberFormatException("不能识别");
            }
        }
        return value*sign;
    }




    @Override
    public int compareTo(MyInteger o) {
        return this.value < o.value ? -1 : (this.value == o.value ? 0 : 1);
    }
}
