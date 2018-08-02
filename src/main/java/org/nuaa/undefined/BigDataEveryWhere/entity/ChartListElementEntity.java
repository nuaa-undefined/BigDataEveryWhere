package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 17:30
 */
public class ChartListElementEntity<T> extends Chart{
    private String name;
    private List<T> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }
}
