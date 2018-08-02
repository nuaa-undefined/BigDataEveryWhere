package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

/**
 * @Author: ToMax
 * @Description: 用于区域实体类
 * @Date: Created in 2018/7/27 9:45
 */
public class RegionEntity {
    /**
     * 区域编码
     */
    private String regionCode;
    /**
     * 区域名称
     */
    private String regionName;
    /**
     * 区域用户总数
     */
    private int userNum;
    /**
     * 区域男性用户总数
     */
    private int manUserNum;
    /**
     * 区域女性用户总数
     */
    private int womanUserNum;
    /**
     * 区域消费总额
     */
    private double sumConsume;
    /**
     * 区域男性消费总额
     */
    private double manConsume;
    /**
     * 区域女性消费总额
     */
    private double womanConsume;

    /**
     * 区域用户交易总数
     */
    private int userConsumeSum;
    /**
     * 区域用户成功交易总数
     */
    private int userConsumeSuccessSum;
    /**
     * 区域用户失败交易总数
     */
    private int userConsumeFailSum;
    /**
     * 最早发生交易时间
     */
    private String earliestConsumeTime;
    /**
     * 区域最高成交额
     */
    private double maxConsumeValue = 0;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getManUserNum() {
        return manUserNum;
    }

    public void setManUserNum(int manUserNum) {
        this.manUserNum = manUserNum;
    }

    public int getWomanUserNum() {
        return womanUserNum;
    }

    public void setWomanUserNum(int womanUserNum) {
        this.womanUserNum = womanUserNum;
    }

    public double getSumConsume() {
        return sumConsume;
    }

    public void setSumConsume(double sumConsume) {
        this.sumConsume = sumConsume;
    }

    public double getManConsume() {
        return manConsume;
    }

    public void setManConsume(double manConsume) {
        this.manConsume = manConsume;
    }

    public double getWomanConsume() {
        return womanConsume;
    }

    public void setWomanConsume(double womanConsume) {
        this.womanConsume = womanConsume;
    }

    public int getUserConsumeSum() {
        return userConsumeSum;
    }

    public void setUserConsumeSum(int userConsumeSum) {
        this.userConsumeSum = userConsumeSum;
    }

    public int getUserConsumeSuccessSum() {
        return userConsumeSuccessSum;
    }

    public void setUserConsumeSuccessSum(int userConsumeSuccessSum) {
        this.userConsumeSuccessSum = userConsumeSuccessSum;
    }

    public int getUserConsumeFailSum() {
        return userConsumeFailSum;
    }

    public void setUserConsumeFailSum(int userConsumeFailSum) {
        this.userConsumeFailSum = userConsumeFailSum;
    }

    @Override
    public String toString() {
        return regionCode + "\t" + regionName + "\t" +
                userNum + "\t" + manUserNum + "\t" +
                womanUserNum + "\t" + sumConsume + "\t" +
                manConsume + "\t" + womanConsume + "\t" + maxConsumeValue + "\t" +
                userConsumeSum + "\t" + userConsumeSuccessSum + "\t" +
                userConsumeFailSum + "\t" + earliestConsumeTime;
    }

    public String getEarliestConsumeTime() {
        return earliestConsumeTime;
    }

    public void setEarliestConsumeTime(String earliestConsumeTime) {
        this.earliestConsumeTime = earliestConsumeTime;
    }

    public double getMaxConsumeValue() {
        return maxConsumeValue;
    }

    public void setMaxConsumeValue(double maxConsumeValue) {
        this.maxConsumeValue = maxConsumeValue;
    }
}
