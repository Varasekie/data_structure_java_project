package Last;

import Matrix.Addible;

public class Triple implements Comparable<Triple>, Addible<Triple> {
    int row, column, value;                      //行号、列号、元素值，默认访问权限
    //7.2.2节，行号（边的起点序号），列号（终点序号）、元素值（权值）

    //构造方法，参数指定行号、列号、元素值。若行号、列号为负，则抛出无效参数异常
    public Triple(int row, int column, int value)
    {
        if(row>=0 && column>=0)
        {
            this.row = row;
            this.column = column;
            this.value = value;
        }
        else
            throw new IllegalArgumentException("行、列号不能为负数：row="+row+"，column="+column);
    }
    public Triple(String triple)                    //以字符串构造，形式为“(,,)”，没有空格
    {
        int i=triple.indexOf(','),  j=triple.indexOf(',', i+1);  //查找两个','字符序号
        this.row = Integer.parseInt(triple.substring(1,i));   //未处理数值格式异常
        this.column=Integer.parseInt(triple.substring(i+1,j));
        this.value=Integer.parseInt(triple.substring(j+1,triple.length()-1));
        if(this.row<0 || this.column<0)
            throw new IllegalArgumentException("行、列号错误：row="+row+"，column="+column);
    }
    public Triple(Triple triple)                 //拷贝构造方法，复制一个三元组
    {
        this(triple.row, triple.column, triple.value);
    }

    public String toString()                     //返回三元组描述字符串，形式为“(,,)”
    {
        return "("+row+","+column+","+value+")";
    }

    //7.2.2节图删除顶点用//习题5，转置矩阵用
    public Triple toSymmetry()                   //返回矩阵对称位置元素的三元组。
    {
        return new Triple(this.column, this.row, this.value);
    }

    public boolean equals(Object obj)            //比较this与obj三元组是否相等，比较位置和元素值
    {
        if(this==obj)
            return true;
        if(obj instanceof Triple)
        {
            Triple triple = (Triple)obj;
            return this.row==triple.row && this.column==triple.column && this.value==triple.value;
        }
        return false;
    }

    //根据行、列位置（行主序）比较三元组对象大小，与元素值无关，约定三元组排序次序
    public int compareTo(Triple triple)
    {
        if(this.row==triple.row && this.column==triple.column)   //相等条件，与equals()方法含义不同
            return 0;
        return (this.row<triple.row || this.row==triple.row && this.column<triple.column)?-1:1;
    }

    //（7） 矩阵相加
    public void add(Triple triple)               //相加，this+=triple，实现Addible<T>接口
    {
        this.value += triple.value;
    }
    public boolean removable()                   //约定删除元素条件，实现Addible<T>接口
    {
        return this.value==0;                    //不存储值为0的元素
    }
}
