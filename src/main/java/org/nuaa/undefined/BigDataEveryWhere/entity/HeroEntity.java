package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 16:58
 */
public class HeroEntity {
    private String name;
    private Integer winNum;
    private Integer failNum;
    private Integer userNum;
    private List<Integer> labelUseNum;
    private List<Integer> labelWinNum;
    private List<HeroUserEntity> top3Users;
    private Double useRate;
    private Integer useSum;
    private Double winRate;

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public Integer getUseSum() {
        return useSum;
    }

    public void setUseSum(Integer useSum) {
        this.useSum = useSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
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

    public List<HeroUserEntity> getTop3Users() {
        return top3Users;
    }

    public void setTop3Users(List<HeroUserEntity> top3Users) {
        this.top3Users = top3Users;
    }

    public Double getUseRate() {
        return useRate;
    }

    public void setUseRate(Double useRate) {
        this.useRate = useRate;
    }
}
