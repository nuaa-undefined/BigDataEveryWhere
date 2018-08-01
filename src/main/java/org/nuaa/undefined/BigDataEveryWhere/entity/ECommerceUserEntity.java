package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 20:42
 */
public class ECommerceUserEntity {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户性别
     */
    private Integer sex;
    /**
     * 交易次数
     */
    private Integer buyCount;
    /**
     * 成交次数
     */
    private Integer successCount;
    /**
     * 失败交易次数
     */
    private Integer failCount;
    /**
     * 总成交金额
     */
    private Double sumMoney;
    /**
     * 最大成交金额
     */
    private Double maxMoney;
    /**
     * 用户归属省级行政区编号
     */
    private String place;
    /**
     * 最早活跃时间
     */
    private String earliestRecord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEarliestRecord() {
        return earliestRecord;
    }

    public void setEarliestRecord(String earliestRecord) {
        this.earliestRecord = earliestRecord;
    }
}
