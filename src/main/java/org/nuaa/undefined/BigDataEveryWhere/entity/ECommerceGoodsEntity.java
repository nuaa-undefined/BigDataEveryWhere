package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 11:02
 * @Description:
 */
public class ECommerceGoodsEntity {
    private String id ;                 //商品id
    private int succussCounts;          //成功交易次数
    private int failCounts;              //失败交易次数
    private int totalCounts;               //总交易次数
    private int maleCounts = 0;         //男性购买次数
    private int femaleCounts = 0;       //女性购买次数
    private double totalAmounts = 0;       //成交总额
    private int season;                     //上市季度
    private String highestRegion;           //销售最好地区
    private double abandonRate;             //购买放弃率
    private double maleRate;                //男性购买率
    private double femaleRate;              //女性购买率

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSuccussCounts() {
        return succussCounts;
    }

    public void setSuccussCounts(int succussCounts) {
        this.succussCounts = succussCounts;
    }

    public int getFailCounts() {
        return failCounts;
    }

    public void setFailCounts(int failCounts) {
        this.failCounts = failCounts;
    }

    public int getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(int totalCounts) {
        this.totalCounts = totalCounts;
    }

    public int getMaleCounts() {
        return maleCounts;
    }

    public void setMaleCounts(int maleCounts) {
        this.maleCounts = maleCounts;
    }

    public int getFemaleCounts() {
        return femaleCounts;
    }

    public void setFemaleCounts(int femaleCounts) {
        this.femaleCounts = femaleCounts;
    }

    public double getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(double totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getHighestRegion() {
        return highestRegion;
    }

    public void setHighestRegion(String highestRegion) {
        this.highestRegion = highestRegion;
    }

    public double getAbandonRate() {
        return abandonRate;
    }

    public void setAbandonRate(double abandonRate) {
        this.abandonRate = abandonRate;
    }

    public double getMaleRate() {
        return maleRate;
    }

    public void setMaleRate(double maleRate) {
        this.maleRate = maleRate;
    }

    public double getFemaleRate() {
        return femaleRate;
    }

    public void setFemaleRate(double femaleRate) {
        this.femaleRate = femaleRate;
    }
}
