package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 23:16
 */
public class HeroInfoEntity {
    private Integer heroNum;
    private Integer userNum;
    private Integer gameNum;
    private Double sumWinRate;
    private String moreUseHero;
    private String mostUsefulHero;

    public Integer getHeroNum() {
        return heroNum;
    }

    public void setHeroNum(Integer heroNum) {
        this.heroNum = heroNum;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getGameNum() {
        return gameNum;
    }

    public void setGameNum(Integer gameNum) {
        this.gameNum = gameNum;
    }

    public Double getSumWinRate() {
        return sumWinRate;
    }

    public void setSumWinRate(Double sumWinRate) {
        this.sumWinRate = sumWinRate;
    }

    public String getMoreUseHero() {
        return moreUseHero;
    }

    public void setMoreUseHero(String moreUseHero) {
        this.moreUseHero = moreUseHero;
    }

    public String getMostUsefulHero() {
        return mostUsefulHero;
    }

    public void setMostUsefulHero(String mostUsefulHero) {
        this.mostUsefulHero = mostUsefulHero;
    }
}
