package NinthChar;

public class Array1 {
    //【例1.4】随机数序列。
    public static void print(Object[] value)     // 输出对象数组元素，以空格分隔元素
    {
        for(Object obj : value)               	 // 逐元循环，obj逐个引用value数组元素，次序同数组
            System.out.print(obj==null ? "null " : " "+obj.toString());
        System.out.println();
    }

//    // 产生n个随机数（可相同），范围是0～size-1，返回整数对象数组
//    public static Integer[] random(int n, int size)
//    {
//        Integer[] values = new Integer[n];       // java.lang.Integer是int类型的包装类
//        for(int i=0;  i<values.length;  i++)     // 遍历数组，访问每个元素仅一次
//            // java.lang.Math.random()方法产生一个0～1之间double类型的随机数
//            values[i] = new Integer((int)(Math.random()*size));
////        	values[i] = (int)(Math.random()*size);   //也可，Java自动将int整数封装成Integer对象，赋值相容
//        return values;                           // 返回数组引用
//    }

    //【例1.4思考题1-2】不包含0
    // 产生n个随机数（可重复），范围是1～size-1，不包含0，返回整数对象数组
    public static Integer[] random(int n, int size)
    {
        Integer[] values = new Integer[n];       // java.lang.Integer是int类型的包装类
        int i=0;
        while(i<n)                               // 不包含0，不用for语句
        {
            values[i] = new Integer((int)(Math.random()*size));// 产生一个0～size-1之间的随机数
            if(values[i]!=0)
                i++;
        }
        return values;                           // 返回数组引用
    }

    //【例1.4思考题1-2】////后续调用
    // 输出对象数组元素，形式为“{,}”，以“,”分隔元素，“,”比元素少一个
//  public static<T> void print(T[] values)//不能，二义性
    public static void printBracket(Object[] values)
    {
        System.out.print("{");
        if(values.length>0)
            System.out.print(values[0].toString());
        for(int i=1;  i<values.length;  i++)     // 遍历数组，访问每个元素仅一次
            //下句中obj.toString()方法运行时多态
            System.out.print(", "+(values[i]==null ? "" : values[i].toString()));
        System.out.print("} ");
    }
    public static void printlnBracket(Object[] values)
    {
        printBracket(values);
        System.out.println();
//      System.out.println("}，"+value.length+"个元素");
    }

    //以下§8.2 二分法查找； §9.1～9.4 排序【试题9】用
    //输出数组begin～end元素，0≤begin≤end<values.length，包含end，end不容错。
    //以“,”分隔，没有“{}”
    public static void print(int[] values, int begin, int end)
    {
        if(values.length>0 && begin>=0 && begin<end &&  end<values.length) //begin、end参数有效
            System.out.print(values[begin]);
        for(int i=begin+1;  i<=end;  i++)      //遍历数组begin～end
            System.out.print(", "+values[i]);
    }
    public static void print(int[] values, int begin)
    {
        print(values, begin, values.length-1);
    }
    public static void print(int[] values)
    {
        print(values, 0, values.length-1);
    }
    public static void println(int[] values)
    {
        print(values, 0, values.length-1);
        System.out.println();
    }

    //以“{,}”形式输出数组begin～end元素，0≤begin≤end<values.length，包含end，end不容错
    public static void printBracket(int[] values, int begin, int end)
    {
        System.out.print("{");
        print(values, begin, end);
//        if(values.length>0 && begin>=0 && begin<end &&  end<values.length) //begin、end参数有效
//            System.out.print(values[begin]);
//        for(int i=begin+1;  i<=end;  i++)      //遍历数组begin～end
//            System.out.print(", "+values[i]);
        System.out.print("}");
    }
    public static void printBracket(int[] values, int begin)
    {
        printBracket(values, begin, values.length-1);
    }
    public static void printBracket(int[] values)//以“{,}”形式输出数组元素
    {
        printBracket(values, 0, values.length-1);
        System.out.println("，"+values.length+"个元素");
    }
    public static void printlnBracket(int[] values)//以“{,}”形式输出数组元素
    {
        printBracket(values, 0, values.length-1);
        System.out.println();
    }


    //【例1.4思考题1-2】不包含0
    //产生n个随机数（可重复），范围是1～size-1，不包含0，返回整数对象数组
    //调用：§8.1.2 二分法查找【试题8】；§9.3.2 堆排序【试题9】
    public static int[] randomInt(int n, int size)
    {
        int values[] = new int[n], i=0;
        while(i<n)                               //不包含0，不用for语句
        {
            values[i] = (int)(Math.random()*size);//产生一个0～size-1之间的随机数
            if(values[i]!=0)
                i++;
        }
        return values;                            //返回数组引用
    }
}
