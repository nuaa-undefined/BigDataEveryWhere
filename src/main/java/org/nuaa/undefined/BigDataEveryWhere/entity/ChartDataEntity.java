package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 17:27
 */
public class ChartDataEntity<T extends Chart>{
    private List<T> series;
    private List<String> xAxis;
    private List<String> yAxis;

    public List<T> getSeries() {
        return series;
    }

    public void setSeries(List<T> series) {
        this.series = series;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<String> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<String> yAxis) {
        this.yAxis = yAxis;
    }
}
