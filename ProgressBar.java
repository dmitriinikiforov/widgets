package com.github.dmitriinikiforov.widgets;

import javax.swing.*;
import java.awt.*;

public abstract class ProgressBar extends JComponent {
    protected String title="A progress bar";
    protected Color bgColor=Color.darkGray;
    protected Color fgColor=Color.lightGray;

    public abstract int getProgress(int width);
    public abstract String getLegend();

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.blue);
        int width=this.getWidth()-4;
        int height=this.getHeight()-4;
        g.setColor(bgColor);
        g.fillRect(2,2,width,height);
        g.setColor(fgColor);
        g.fillRect(3,3,getProgress(width),height-2);
        g.setColor(Color.black);
        g.drawString(getLegend(),4,height/2);
    }
}
