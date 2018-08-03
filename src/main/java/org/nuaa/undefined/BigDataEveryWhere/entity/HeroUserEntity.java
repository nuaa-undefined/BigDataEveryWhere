package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 16:59
 */
public class HeroUserEntity {
    private Integer id;
    private Integer winNum;
    private Integer failNum;
    private Integer heroNum;
    private Double winRate;
    private List<Integer> labelUseNum;
    private List<Integer> labelWinNum;
    private List<HeroEntity> top3Heroes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public Integer getHeroNum() {
        return heroNum;
    }

    public void setHeroNum(Integer heroNum) {
        this.heroNum = heroNum;
    }

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public List<Integer> getLabelUseNum() {
        return labelUseNum;
    }

    public void setLabelUseNum(List<Integer> labelUseNum) {
        this.labelUseNum = labelUseNum;
    }

    public List<Integer> getLabelWinNum() {
        return labelWinNum;
    }

    public void setLabelWinNum(List<Integer> labelWinNum) {
        this.labelWinNum = labelWinNum;
    }

    public List<HeroEntity> getTop3Heroes() {
        return top3Heroes;
    }

    public void setTop3Heroes(List<HeroEntity> top3Heroes) {
        this.top3Heroes = top3Heroes;
    }
}
