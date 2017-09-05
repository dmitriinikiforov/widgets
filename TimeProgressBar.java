package com.github.dmitriinikiforov.widgets;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;

/**
 * Created by Dima on 18.11.2016.
 */
public class TimeProgressBar extends ProgressBar {
    private LocalDate beginDate;
    private LocalDate endDate;
    private long daysBetween;
    public TimeProgressBar(String title, LocalDate beginDate, LocalDate endDate) {
        this.title=title;
        this.beginDate=beginDate;
        this.endDate=endDate;
        if (beginDate.isAfter(endDate)) {
            bgColor=Color.red;
            this.title=title+" expanded in";
        }
        daysBetween=endDate.toEpochDay()-beginDate.toEpochDay();
        this.setMinimumSize(new Dimension(100,20));
        this.setPreferredSize(new Dimension(300,40));
        this.setMaximumSize(new Dimension(600,80));
        this.setBorder(new LineBorder(Color.green,1));
    }

    @Override
    public int getProgress(int width) {
        LocalDate today=LocalDate.now();
        long daysPassed=today.toEpochDay()-beginDate.toEpochDay();
        return ((int)((width-2)*daysPassed*1.0/daysBetween));
    }

    @Override
    public String getLegend() {
        LocalDate today=LocalDate.now();
        long daysPassed=today.toEpochDay()-beginDate.toEpochDay();
        long daysRemain=endDate.toEpochDay()-today.toEpochDay();
        return new StringBuffer(title).append(": ").append(daysRemain).
                append(" (").append(daysPassed).append("/").append(daysBetween).append(")").toString();
    }

}
