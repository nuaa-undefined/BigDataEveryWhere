package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:18
 */
public class EComMonthDistributionEntity {
    /**
     * 年份
     */
    private Integer month;
    /**
     * 购买总次数
     */
    private Integer buySum;
    /**
     * 成功次数
     */
    private Integer successSum;
    /**
     * 失败次数
     */
    private Integer failSum;
    /**
     * 总交易额
     */
    private Double moneySum;
    /**
     * 男性用户总交易额
     */
    private Double manMoneySum;
    /**
     * 女性用户总交易额
     */
    private Double womanMoneySum;
    /**
     * 女性用户总交易额
     */
    private Integer newUser;
    /**
     * 最大成交额
     */
    private Double maxMoney;

    private Integer manBuyCount;
    private Integer womanBuyCount;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getBuySum() {
        return buySum;
    }

    public void setBuySum(Integer buySum) {
        this.buySum = buySum;
    }

    public Integer getSuccessSum() {
        return successSum;
    }

    public void setSuccessSum(Integer successSum) {
        this.successSum = successSum;
    }

    public Integer getFailSum() {
        return failSum;
    }

    public void setFailSum(Integer failSum) {
        this.failSum = failSum;
    }

    public Double getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(Double moneySum) {
        this.moneySum = moneySum;
    }

    public Double getManMoneySum() {
        return manMoneySum;
    }

    public void setManMoneySum(Double manMoneySum) {
        this.manMoneySum = manMoneySum;
    }

    public Double getWomanMoneySum() {
        return womanMoneySum;
    }

    public void setWomanMoneySum(Double womanMoneySum) {
        this.womanMoneySum = womanMoneySum;
    }

    public Integer getNewUser() {
        return newUser;
    }

    public void setNewUser(Integer newUser) {
        this.newUser = newUser;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getManBuyCount() {
        return manBuyCount;
    }

    public void setManBuyCount(Integer manBuyCount) {
        this.manBuyCount = manBuyCount;
    }

    public Integer getWomanBuyCount() {
        return womanBuyCount;
    }

    public void setWomanBuyCount(Integer womanBuyCount) {
        this.womanBuyCount = womanBuyCount;
    }
}
