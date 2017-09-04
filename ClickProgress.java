package com.github.dmitriinikiforov.widgets;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Dima on 18.11.2016.
 */
public class ClickProgress extends JComponent {
    String title;
    int initCount;
    int curCount;
    int totalCount;
    Color curColor;
    public ClickProgress(String title, int initCount, int totalCount) {
        this.title=title;
        this.initCount = initCount;
        this.totalCount =totalCount;
        this.setMinimumSize(new Dimension(100,20));
        this.setPreferredSize(new Dimension(300,40));
        this.setMaximumSize(new Dimension(600,80));
        this.setBorder(new LineBorder(Color.green,1));
        this.curColor=Color.lightGray;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (curCount<totalCount-1) {
                    curCount++;
                    repaint();
                }
                else if (curCount==totalCount-1){
                    curCount++;
                    curColor=Color.green;
                    repaint();
                }
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.blue);
        int width=this.getWidth()-4;
        int height=this.getHeight()-4;
        g.setColor(Color.darkGray);
        g.fillRect(2,2,width,height);
        g.setColor(curColor);
        g.fillRect(3,3,((int)((width-2)*(curCount)*1.0/totalCount)),height-2);
        g.setColor(Color.black);
        StringBuffer sb=new StringBuffer(title).append(": ").append(totalCount-curCount).append(" (").append(curCount).append("/").append(totalCount).append(")");
        g.drawString(sb.toString(),4,height/2);
    }
}
