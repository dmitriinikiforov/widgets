package com.github.dmitriinikiforov.widgets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ChartField extends Canvas {
    private int width;
    private int height;
    private double[] constraints;
    private int xTicksNum=9;
    private int yTicksNum=5;
    private int borderMargin=10;
    private ArrayList<ChartData> charts;
    public ChartField() {
        width=getWidth();
        height=getHeight();
        constraints=new double[4];
        constraints[0]=-1.0;
        constraints[1]=1.0;
        constraints[2]=-1.0;
        constraints[3]=1.0;
        charts=new ArrayList<>();
        repaint();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        width=getWidth();
        height=getHeight();
        if (width<=0 || height <=0) return;

        BufferedImage buffer=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics bufG=buffer.getGraphics();

        drawBorder(bufG);
        drawSecondaryAxes(bufG);

        g.drawImage(buffer,0,0,null);

        BufferedImage img;
        for (ChartData cData: charts) {
            img=cData.drawChart(new Dimension(width-2*borderMargin-2,height-2*borderMargin-2),constraints);
            g.drawImage(img,borderMargin+1,borderMargin+1,null);
        }
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,width,height);
        g.setColor(Color.black);
        g.drawRect(borderMargin,borderMargin,width-2*borderMargin,height-2*borderMargin);
        g.setColor(Color.BLUE);
        g.drawString(String.format("%.2f",constraints[0]),borderMargin,height);
        g.drawString(String.format("%.2f",constraints[1]),width-borderMargin,height);
        g.drawString(String.format("%.2f",constraints[2]),0,height-borderMargin);
        g.drawString(String.format("%.2f",constraints[3]),0,borderMargin);
    }

    private void drawSecondaryAxes(Graphics g) {
        double[] xTicks=new double[xTicksNum];
        for (int i=1; i<xTicksNum+1; i++) {
            g.setColor(Color.lightGray);
            g.drawLine(borderMargin+(int)(i*(width-2*borderMargin)*1.0/(xTicksNum+1)),borderMargin+1,
                    borderMargin+(int)(i*(width-2*borderMargin)*1.0/(xTicksNum+1)),height-borderMargin-1);
            xTicks[i-1]=constraints[0]+i*(constraints[1]-constraints[0])/(xTicksNum+1);
            g.setColor(Color.BLUE);
            g.drawString(String.format("%.2f",xTicks[i-1]),borderMargin+(int)(i*(width-2*borderMargin)*1.0/(xTicksNum+1)),height);
        }
        double[] yTicks=new double[yTicksNum];
        for (int i=1; i<yTicksNum+1; i++) {
            g.setColor(Color.lightGray);
            g.drawLine(borderMargin+1,borderMargin+(int)(i*(height-2*borderMargin)*1.0/(yTicksNum+1)),
                    width-borderMargin-1, borderMargin+(int)(i*(height-2*borderMargin)*1.0/(yTicksNum+1)));
            yTicks[i-1]=constraints[2]+i*(constraints[3]-constraints[2])/(yTicksNum+1);
            g.setColor(Color.BLUE);
            g.drawString(String.format("%.2f",yTicks[i-1]),0,borderMargin+(int)((yTicksNum-i+1)*(height-2*borderMargin)*1.0/(yTicksNum+1)));
        }
    }

    public void addLinePlot(double[] xData, double[] yData) {
        int n=xData.length;
        if (n!=yData.length) {
            System.err.println("x and y data has different lengths");
            return;
        }
        double[] sortedX=Arrays.copyOf(xData,n);
        double[] sortedY=Arrays.copyOf(yData,n);
        Arrays.sort(sortedX);
        Arrays.sort(sortedY);
        double minX=sortedX[0];
        double maxX=sortedX[n-1];
        double minY=sortedY[0];
        double maxY=sortedY[n-1];
        if (minX<constraints[0]) constraints[0]=minX;
        if (maxX>constraints[1]) constraints[1]=maxX;
        if (minY<constraints[2]) constraints[2]=minY;
        if (maxY>constraints[3]) constraints[3]=maxY;

        charts.add(new LineChartData(xData,yData));

        repaint();
    }
}
