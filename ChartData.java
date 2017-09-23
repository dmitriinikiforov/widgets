package com.github.dmitriinikiforov.widgets;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class ChartData {
    abstract public BufferedImage drawChart(Dimension dim, double[] constraints);
}
