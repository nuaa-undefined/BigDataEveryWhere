package org.nuaa.undefined.BigDataEveryWhere.kmeans;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/5 10:57
 */
public class UserLabelBean {
    private String id;
    private List<Integer> labels;

    public UserLabelBean() {

    }

    public UserLabelBean(ECommerceUserEntity bean) {
        this.id = bean.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }
}
