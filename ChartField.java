package com.github.dmitriinikiforov.widgets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;

public class ChartField extends Canvas {
    private int width;
    private int height;
    private double[] constraints;
    private int xTicksNum=10;
    private double[] Xticks;
    private int borderMargin=10;
    private LinkedList<BufferedImage> plots;
    public ChartField() {
        plots=new LinkedList<>();
        constraints=new double[4];
        constraints[0]=-1.0;
        constraints[1]=1.0;
        constraints[2]=-1.0;
        constraints[3]=1.0;
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
        bufG.setColor(Color.white);
        bufG.fillRect(0,0,width,height);
        bufG.setColor(Color.black);
        bufG.drawRect(borderMargin,borderMargin,width-2*borderMargin,height-2*borderMargin);
        bufG.setColor(Color.BLUE);
        bufG.drawString(String.format("%.2f",constraints[0]),borderMargin,height);
        bufG.drawString(String.format("%.2f",constraints[1]),width-borderMargin,height);
        bufG.drawString(String.format("%.2f",constraints[2]),0,height-borderMargin);
        bufG.drawString(String.format("%.2f",constraints[3]),0,borderMargin);

        int xTicksStep=(width-2*borderMargin)/(xTicksNum-1);
        Xticks=new double[xTicksNum];
        for (int i=1; i<xTicksNum; i++) {
            bufG.setColor(Color.lightGray);
            bufG.drawLine(borderMargin+i*xTicksStep,borderMargin,
                    borderMargin+i*xTicksStep,height-borderMargin);
            Xticks[i-1]=constraints[0]+i*(constraints[1]-constraints[0])/xTicksNum;
            bufG.setColor(Color.BLUE);
            bufG.drawString(String.format("%.2f",Xticks[i-1]),borderMargin+i*xTicksStep,height);
        }

        g.drawImage(buffer,0,0,null);

        for (BufferedImage img: plots) {
            g.drawImage(img,borderMargin,borderMargin,null);
        }
    }

    public void addLinePlot(double[] xData, double[] yData) {
        if (width==0 || height==0){
            System.err.println("width "+width+" height "+height);
            return;
        }
        int n=xData.length;
        if (n!=yData.length) {
            System.err.println("x and y data has different lengths");
            return;
        }

        double[] sortedX=Arrays.copyOf(xData,n);
        double[] sortedY=Arrays.copyOf(yData,n);
        double minX=sortedX[0];
        double maxX=sortedX[n-1];
        double minY=sortedY[0];
        double maxY=sortedY[n-1];
        if (minX<constraints[0]) constraints[0]=minX;
        if (maxX>constraints[1]) constraints[1]=maxX;
        if (minY<constraints[2]) constraints[2]=minY;
        if (maxY>constraints[3]) constraints[3]=maxY;

        plots.add(plotLine(new Dimension(width-2*borderMargin,height-2*borderMargin),constraints,xData,yData));
        repaint();
    }

    private BufferedImage plotLine(Dimension dim, double[] constraints, double[] xData, double[] yData) {
        BufferedImage img=new BufferedImage(dim.width,dim.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g=img.getGraphics();
        int n=xData.length;
        g.setColor(Color.green);
        for (int i=0; i<n-1; i++) {
            g.drawLine((int)(dim.width*(constraints[1]-xData[i])/(constraints[1]-constraints[0])),
                    (int)(dim.height*((constraints[3]-yData[i])/(constraints[3]-constraints[2]))),
                    (int)(dim.width*(constraints[1]-xData[i+1])/(constraints[1]-constraints[0])),
                    (int)(dim.height*((constraints[3]-yData[i+1])/(constraints[3]-constraints[2]))));
        }
        return img;
    }
}
