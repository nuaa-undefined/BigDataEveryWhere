package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean;

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
 * @Date: Created in 2018/8/2 11:30
 */
public class RegionBean implements Writable, DBWritable{
    private String regionCode;
    private String regionName;
    private Integer userNum;
    private Integer manNum;
    private Integer womanNum;
    private Double sumMoney;
    private Double manMoney;
    private Double womanMoney;
    private Double maxMoney;
    private Integer buySum;
    private Integer successSum;
    private Integer failSum;
    private String earliestTime;

    public RegionBean() {

    }
    public RegionBean(String text) {
        String [] data = text.split("\\s+");
        this.regionCode = data[0];
        this.regionName = data[1];
        this.userNum = Integer.parseInt(data[2]);
        this.manNum = Integer.parseInt(data[3]);
        this.womanNum = Integer.parseInt(data[4]);
        this.sumMoney = Double.parseDouble(data[5]);
        this.manMoney = Double.parseDouble(data[6]);
        this.womanMoney = Double.parseDouble(data[7]);
        this.maxMoney = Double.parseDouble(data[8]);
        this.buySum = Integer.parseInt(data[9]);
        this.successSum = Integer.parseInt(data[10]);
        this.failSum = Integer.parseInt(data[11]);
        this.earliestTime = data[12];
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, regionCode);
        preparedStatement.setString(2, regionName);
        preparedStatement.setInt(3, userNum);
        preparedStatement.setInt(4, manNum);
        preparedStatement.setInt(5, womanNum);
        preparedStatement.setDouble(6, sumMoney);
        preparedStatement.setDouble(7, manMoney);
        preparedStatement.setDouble(8, womanMoney);
        preparedStatement.setDouble(9, maxMoney);
        preparedStatement.setInt(10, buySum);
        preparedStatement.setInt(11, successSum);
        preparedStatement.setInt(12, failSum);
        preparedStatement.setString(13, earliestTime);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }

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

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getManNum() {
        return manNum;
    }

    public void setManNum(Integer manNum) {
        this.manNum = manNum;
    }

    public Integer getWomanNum() {
        return womanNum;
    }

    public void setWomanNum(Integer womanNum) {
        this.womanNum = womanNum;
    }

    public Double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Double getManMoney() {
        return manMoney;
    }

    public void setManMoney(Double manMoney) {
        this.manMoney = manMoney;
    }

    public Double getWomanMoney() {
        return womanMoney;
    }

    public void setWomanMoney(Double womanMoney) {
        this.womanMoney = womanMoney;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getBuySum() {
        return buySum;
    }

    public void setBuySum(Integer buySum) {
        this.buySum = buySum;
    }

    public Integer getSuccessSum() {
        return successSum;
    }

    public void setSuccessSum(Integer successSum) {
        this.successSum = successSum;
    }

    public Integer getFailSum() {
        return failSum;
    }

    public void setFailSum(Integer failSum) {
        this.failSum = failSum;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }
}
