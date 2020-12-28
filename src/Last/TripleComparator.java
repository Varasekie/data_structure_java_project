package Last;

public class TripleComparator implements java.util.Comparator<Triple>{
    public int compare(Triple triple1, Triple triple2)     //比较T类对象相等和大小
    {
        return (int)(triple1.value - triple2.value);       //三元组按值比较大小；图的边按权值比较大小
    }
}
