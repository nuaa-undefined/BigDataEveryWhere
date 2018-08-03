package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 21:24
 * @Description:
 */
public class GameAllTwoEntity {
    private String osRatio;     //操作系统使用比
    private long averLoginPersonNum;//总人均登陆时长
    private long averLoginTimesNum;//总此君登陆时长
    private String highestTimesDay;//次均时长最高的一天
    private String averRetentionRate;//平均留存率
    private String highestRetentionDay;//最高留存率
    public GameAllTwoEntity(String osRatio, long averLoginPersonNum, long averLoginTimesNum, String highestTimesDay, String averRetentionRate, String highestRetentionDay) {
        this.osRatio = osRatio;
        this.averLoginPersonNum = averLoginPersonNum;
        this.averLoginTimesNum = averLoginTimesNum;
        this.highestTimesDay = highestTimesDay;
        this.averRetentionRate = averRetentionRate;
        this.highestRetentionDay = highestRetentionDay;
    }

    public GameAllTwoEntity() {
    }

    public String getOsRatio() {
        return osRatio;
    }

    public void setOsRatio(String osRatio) {
        this.osRatio = osRatio;
    }

    public long getAverLoginPersonNum() {
        return averLoginPersonNum;
    }

    public void setAverLoginPersonNum(long averLoginPersonNum) {
        this.averLoginPersonNum = averLoginPersonNum;
    }

    public long getAverLoginTimesNum() {
        return averLoginTimesNum;
    }

    public void setAverLoginTimesNum(long averLoginTimesNum) {
        this.averLoginTimesNum = averLoginTimesNum;
    }

    public String getHighestTimesDay() {
        return highestTimesDay;
    }

    public void setHighestTimesDay(String highestTimesDay) {
        this.highestTimesDay = highestTimesDay;
    }

    public String getAverRetentionRate() {
        return averRetentionRate;
    }

    public void setAverRetentionRate(String averRetentionRate) {
        this.averRetentionRate = averRetentionRate;
    }

    public String getHighestRetentionDay() {
        return highestRetentionDay;
    }

    public void setHighestRetentionDay(String highestRetentionDay) {
        this.highestRetentionDay = highestRetentionDay;
    }
}
