package FifthChar;

import java.util.Comparator;

public class TripleComparator implements Comparator<Triple> {
    @Override
    public int compare(Triple o1, Triple o2) {
        return (int)(o1.value - o2.value);
    }
}
