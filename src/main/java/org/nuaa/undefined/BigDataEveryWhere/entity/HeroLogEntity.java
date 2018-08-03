package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 16:59
 */
public class HeroLogEntity {
    private String heroName;
    private Integer userId;
    private Integer status;
    private Integer label;

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }
}
