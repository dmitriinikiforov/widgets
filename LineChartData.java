package com.github.dmitriinikiforov.widgets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineChartData extends ChartData {
    double[] xData;
    double[] yData;
    public LineChartData(double[] xData, double[] yData) {
        this.xData=xData;
        this.yData=yData;
    }
    public BufferedImage drawChart(Dimension dim, double[] constraints) {
        if (dim.width==0 || dim.height==0){
            System.err.println("width "+dim.width+" height "+dim.height);
            return null;
        }
        BufferedImage img=new BufferedImage(dim.width,dim.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g=img.getGraphics();
        g.setColor(Color.green);
        for (int i=0; i<xData.length-1; i++) {
            g.drawLine((int) Math.round(dim.width*(constraints[1]-xData[i])/(constraints[1]-constraints[0])),
                    (int) Math.round(dim.height*(constraints[3]-yData[i])/(constraints[3]-constraints[2])),
                    (int) Math.round(dim.width*(constraints[1]-xData[i+1])/(constraints[1]-constraints[0])),
                    (int) Math.round(dim.height*(constraints[3]-yData[i+1])/(constraints[3]-constraints[2])));
        }
        return img;
    }
}
