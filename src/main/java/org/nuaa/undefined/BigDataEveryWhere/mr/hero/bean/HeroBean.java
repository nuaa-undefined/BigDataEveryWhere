package org.nuaa.undefined.BigDataEveryWhere.mr.hero.bean;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 16:46
 */
public class HeroBean implements Writable, DBWritable{
    private String name;
    private Integer winNum;
    private Integer failNum;
    private Integer userNum;

    public HeroBean() {

    }

    public HeroBean(String name, Integer winNum, Integer failNum, Integer userNum) {
        this.name = name;
        this.winNum = winNum;
        this.failNum = failNum;
        this.userNum = userNum;
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, winNum);
        preparedStatement.setInt(3, failNum);
        preparedStatement.setInt(4, userNum);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }
}
