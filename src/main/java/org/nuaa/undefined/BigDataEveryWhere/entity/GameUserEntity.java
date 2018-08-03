package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 15:19
 * @Description:
 */
public class GameUserEntity {
    private String id;
    private String os;
    private String firstTime;
    private String finalTime;
    private String totalTime;
    private String mainPlayTime;
    private int allLoginDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getMainPlayTime() {
        return mainPlayTime;
    }

    public void setMainPlayTime(String mainPlayTime) {
        this.mainPlayTime = mainPlayTime;
    }

    public int getAllLoginDay() {
        return allLoginDay;
    }

    public void setAllLoginDay(int allLoginDay) {
        this.allLoginDay = allLoginDay;
    }
}
