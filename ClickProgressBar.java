package com.github.dmitriinikiforov.widgets;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dima on 18.11.2016.
 */
public class ClickProgressBar extends ProgressBar {
    private int initCount;
    private int curCount;
    private int totalCount;
    public ClickProgressBar(String title, int initCount, int totalCount) {
        this.title=title;
        this.initCount = initCount;
        this.curCount=this.initCount;
        this.totalCount =totalCount;
        this.setMinimumSize(new Dimension(100,20));
        this.setPreferredSize(new Dimension(300,40));
        this.setMaximumSize(new Dimension(600,80));
        this.setBorder(new LineBorder(Color.green,1));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (curCount<totalCount-1) {
                    curCount++;
                    repaint();
                }
                else if (curCount==totalCount-1){
                    curCount++;
                    fgColor=Color.green;
                    repaint();
                }
            }
        });
    }

    @Override
    public int getProgress(int width) {
        return ((int)((width-2)*(curCount)*1.0/totalCount));
    }

    @Override
    public String getLegend() {
        return new StringBuffer(title).append(": ").append(totalCount-curCount).
                append(" (").append(curCount).append("/").append(totalCount).append(")").toString();
    }

}
