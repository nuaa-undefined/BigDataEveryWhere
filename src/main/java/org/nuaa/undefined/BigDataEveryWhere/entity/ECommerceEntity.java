package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/5 12:39
 */
public class ECommerceEntity {
    private int userNum;
    private int activeUserNum;
    private int goodNum;
    private int consumeNum;
    private double successRate;
    private double sumMoney;
//    private

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getConsumeNum() {
        return consumeNum;
    }

    public void setConsumeNum(int consumeNum) {
        this.consumeNum = consumeNum;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getActiveUserNum() {
        return activeUserNum;
    }

    public void setActiveUserNum(int activeUserNum) {
        this.activeUserNum = activeUserNum;
    }
}
