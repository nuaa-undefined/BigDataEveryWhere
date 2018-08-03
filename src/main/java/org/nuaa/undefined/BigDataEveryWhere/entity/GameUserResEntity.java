package org.nuaa.undefined.BigDataEveryWhere.entity;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 16:44
 * @Description:
 */
public class GameUserResEntity {
    private String time;
    private int  num;

    public GameUserResEntity(String time, int num) {
        this.time = time;
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
