package Test;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 高文凤
 * 2020/12/31
 */
public class demo3 {
    private MapColor mc;
    private Polygon[] p;
    private JCheckBox cbx;
    private int[][] metrix;
    private int current = -1;
    private int[] guide;
    private Test[] test;
    int[] province;
    private ArrayList<Test> list;
    int[][] neighbor;
    private String[] pname = {"新疆", "西藏", "云南", "青海", "四川", "贵州", "甘肃", "内蒙古",
            "宁夏", "黑龙江", "吉林", "辽宁", "陕西", "山西", "北京", "天津",
            "河北", "河南", "山东", "重庆", "湖北", "安徽", "江苏", "上海",
            "浙江", "湖南", "广西", "江西", "福建", "广东", "香港", "澳门",
            "海南", "台湾"};

    public demo3(MapColor mc, Polygon[] p, JCheckBox cbx, int[][] metrix) {
        this.mc = mc;
        this.p = p;
        this.cbx = cbx;
        this.metrix = metrix;
        neighbor = new int[34][10];
        province = new int[34];
        guide = new int[34];
        test = new Test[34];
        list = new ArrayList<Test>();
        int c;
        for (int i = 0; i < 34; i++) {//将各省所相邻省份用数组neighbor[][]记录起来，neighbor[i][0]记录相邻省的个数。
            province[i] = -1;
            c = 0;
            for (int j = 0; j < 34; j++) {
                if (metrix[i][j] == 1)
                    neighbor[i][++c] = j;
            }
            neighbor[i][0] = c;
            test[i] = new Test(pname[i], i, c);
            list.add(test[i]);//将34个省份放入list对象中
        }
    }

    public boolean isOK(int i) {
        int temp = guide[current];
        for (int j = 1; j <= neighbor[temp][0]; j++)
            if (province[neighbor[temp][j]] == province[temp])
                return false;
        for (int j = 1; j <= neighbor[temp][0]; j++) {
            if (province[neighbor[temp][j]] == -1) {
                test[neighbor[temp][j]].surplus.setFalse(i);
                if (test[neighbor[temp][j]].surplus.getSnum() == 0) {
                    test[neighbor[temp][j]].surplus.setTrue(i);
                    return false;
                }
                test[neighbor[temp][j]].setConstraint(test[neighbor[temp][j]].getConstraint() - 1);
            }
        }
        return true;
    }

    public int color(Test t) {
        int i = 0;
        int temp = t.getNum();
        guide[++current] = temp;

        if (current <= 33)
            for (i = t.surplus.getPos(); i < 4; i++) {
                province[temp] = i;
                Graphics g = mc.getGraphics();
                mc.fillColor(g, temp, i);
                if (cbx.isSelected())
                    try {
                        Thread t1 = new Thread();
                        t1.sleep(150);
                    } catch (Exception e) {
                    }
                if (isOK(i)) {
                    if (current + 1 > 33) {
                        current++;
                        return 1;
                    }
                    Collections.sort(list);//对所有未着色的省进行排序，选取第一个省进行着色。
                    Test t1 = (Test) list.iterator().next();
                    list.remove(t1);//着色后将该省移除
                    int j = color(t1);
                    if (current > 33)
                        return 1;
                    if (j == -1) {
                        current--;
                        province[temp] = -1;
                        for (int k = 1; k <= neighbor[temp][0]; k++) {
                            int l = neighbor[temp][k];
                            test[l].setConstraint(test[l].getConstraint() + 1);
                            test[l].surplus.setTrue(i);
                        }
                        list.add(t1);
                        Collections.sort(list);
                    }
                }
            }
        if (i >= 4) return -1;
        return 1;
    }

    public void optimize() {
        Collections.sort(list);
        Test t = (Test) list.iterator().next();
        list.remove(t);
        color(t);
    }

    public void resetMap() {
        Graphics g = mc.getGraphics();
        for (int k = 0; k < 34; k++) {
            mc.fillColor(g, k, 4);
        }
        mc.paint(g);

    }
}

class Surplus {//用来存未着色省剩余可着色人数
    private boolean[] range = {true, true, true, true};//记录当前可用着颜色
    private int snum = 4;//剩余可用颜色
    private int pos = 0;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getSnum() {
        return snum;
    }//获取snum值看是否出现无色可着的省

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public void setFalse(int i) {//将特定颜色删除
        if (range[i] == true) {//每次着色前，调用此函数将可用颜色中与当前着色省相同的颜色删除
            range[i] = false;
            snum--;
            if (i >= pos)
                while (range[pos] == false && pos < 3)
                    pos++;
        }
    }

    public void setTrue(int i) {//将特定颜色加入;着色失败可用该函数恢复
        if (range[i] == false) {
            range[i] = true;
            snum++;
            if (i < pos)
                pos = i;
        }
    }
}

class Test implements Comparable {//对为着色省进行排序
    public Surplus surplus;
    private Integer constraint = 0;//影响未着色省数
    private String name;
    private int num;

    public Test(String name, int num, int constraint) {
        this.name = name;
        this.num = num;
        this.constraint = constraint;
        surplus = new Surplus();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getConstraint() {
        return constraint;
    }

    public void setConstraint(int constraint) {
        this.constraint = constraint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "province:" + name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        Test t = (Test) o;
        return name.equals(t.name);
    }

    public int compareTo(Object o) {
        Test t = (Test) o;
        if (t.getConstraint() < this.getConstraint())
            return -1;
        else if (t.getConstraint() > this.getConstraint())
            return 1;
        else if (t.surplus.getSnum() < this.surplus.getSnum())
            return 1;
        else if (t.surplus.getSnum() > this.surplus.getSnum())
            return -1;
        else return 0;
    }
}
