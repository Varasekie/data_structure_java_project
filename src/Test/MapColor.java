package Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.Graphics;

class MapColor extends Canvas{
    private Color[] c = {Color.yellow,Color.blue,Color.green,Color.red,Color.white};
    private Polygon[] p;
    public MapColor(Polygon[] p){
        this.p = p;
    }
    public void paint(Graphics g){
        g.setColor(Color.black);
        for(int i=0;i<34;i++)
            g.drawPolygon(p[i]);
    }
    public void fillColor(Graphics g,int i,int j){
        g.setColor(c[j]);
        g.fillPolygon(p[i]);
    }
}
