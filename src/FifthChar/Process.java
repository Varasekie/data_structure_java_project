package FifthChar;

public class Process implements Comparable<Process>{
    private String name;                         //进程名
    private int priority;                        //优先级
    private static int MAX_PRIORITY=10;          //优先级最大值，优先级范围为1～MAX_PRIORITY

    //构造进程，参数name、priority分别指定进程名和优先级，优先级超出范围时抛出无效参数异常
    public Process(String name, int priority)
    {
        this.name = name;
        if(priority>=1 && priority<=MAX_PRIORITY)
            this.priority = priority;
        else
            throw new IllegalArgumentException("priority="+priority);
    }
    public String toString()
    {
        return "("+this.name+","+this.priority+")";
    }
    public int compareTo(Process process)        //进程按优先级比较大小
    {
        return this.priority - process.priority;
    }
}
