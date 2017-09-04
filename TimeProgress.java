package com.github.dmitriinikiforov.widgets;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;

/**
 * Created by Dima on 18.11.2016.
 */
public class TimeProgress extends JComponent {
    String title;
    LocalDate beginDate;
    LocalDate endDate;
    long daysBetween;
    public TimeProgress(String title, LocalDate beginDate, LocalDate endDate) {
        this.title=title;
        this.beginDate=beginDate;
        this.endDate=endDate;
        assert endDate.isAfter(beginDate);
        daysBetween=endDate.toEpochDay()-beginDate.toEpochDay();
        this.setMinimumSize(new Dimension(100,20));
        this.setPreferredSize(new Dimension(300,40));
        this.setMaximumSize(new Dimension(600,80));
        this.setBorder(new LineBorder(Color.green,1));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.blue);
        int width=this.getWidth()-4;
        int height=this.getHeight()-4;
        g.setColor(Color.darkGray);
        g.fillRect(2,2,width,height);
        LocalDate today=LocalDate.now();
        long daysPassed=today.toEpochDay()-beginDate.toEpochDay();
        long daysRemain=endDate.toEpochDay()-today.toEpochDay();
        g.setColor(Color.lightGray);
        g.fillRect(3,3,((int)((width-2)*daysPassed*1.0/daysBetween)),height-2);
        g.setColor(Color.black);
        StringBuffer sb=new StringBuffer(title).append(": ").append(daysRemain).append(" (").append(daysPassed).append("/").append(daysBetween).append(")");
        g.drawString(sb.toString(),4,height/2);
    }
}
