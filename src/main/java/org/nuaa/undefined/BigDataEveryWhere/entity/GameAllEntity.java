package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 20:12
 * @Description:
 */
public class GameAllEntity {
    private int  averActiveUser;            //平均活跃用户数
    private String highestTime;            //登陆用户数最高时段
    private int allUsers;               //累计用户
    private int allLogins;              //累计登陆次数
    private String highestLoginDay;        //登陆人数最高一天
    private String highestLoginDayNum;  //用户平均登陆天数

    public GameAllEntity() {
    }

    public GameAllEntity(int averActiveUser, String highestTime, int allUsers, int allLogins, String highestLoginDay, String highestLoginDayNum) {
        this.averActiveUser = averActiveUser;
        this.highestTime = highestTime;
        this.allUsers = allUsers;
        this.allLogins = allLogins;
        this.highestLoginDay = highestLoginDay;
        this.highestLoginDayNum = highestLoginDayNum;

    }

    public int getAverActiveUser() {
        return averActiveUser;
    }

    public void setAverActiveUser(int averActiveUser) {
        this.averActiveUser = averActiveUser;
    }

    public String getHighestTime() {
        return highestTime;
    }

    public void setHighestTime(String highestTime) {
        this.highestTime = highestTime;
    }

    public int getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(int allUsers) {
        this.allUsers = allUsers;
    }

    public int getAllLogins() {
        return allLogins;
    }

    public void setAllLogins(int allLogins) {
        this.allLogins = allLogins;
    }

    public String getHighestLoginDay() {
        return highestLoginDay;
    }

    public void setHighestLoginDay(String highestLoginDay) {
        this.highestLoginDay = highestLoginDay;
    }

    public String getHighestLoginDayNum() {
        return highestLoginDayNum;
    }

    public void setHighestLoginDayNum(String highestLoginDayNum) {
        this.highestLoginDayNum = highestLoginDayNum;
    }
}
