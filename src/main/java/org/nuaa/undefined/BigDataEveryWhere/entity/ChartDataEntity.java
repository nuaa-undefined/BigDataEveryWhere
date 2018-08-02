package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;
import java.util.Set;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 19:32
 */
public class ChartDataEntity extends Response{
    private Set<String> xAxis;
    private List<String> yAxis;
    private List<Element> dataList;

    public Set<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(Set<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<String> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<String> yAxis) {
        this.yAxis = yAxis;
    }

    public List<Element> getDataList() {
        return dataList;
    }

    public void setDataList(List<Element> dataList) {
        this.dataList = dataList;
    }
}
