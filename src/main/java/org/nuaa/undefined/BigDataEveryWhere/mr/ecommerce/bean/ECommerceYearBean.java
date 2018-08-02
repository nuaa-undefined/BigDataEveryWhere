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
 * @Date: Created in 2018/8/2 20:31
 */
public class ECommerceYearBean implements Writable, DBWritable{

    /**
     * 年份
     */
    private String year;
    /**
     * 购买总次数
     */
    private Integer buySum;
    /**
     * 成功次数
     */
    private Integer successSum;
    /**
     * 失败次数
     */
    private Integer failSum;
    /**
     * 总交易额
     */
    private Double moneySum;
    /**
     * 男性用户总交易额
     */
    private Double manMoneySum;
    /**
     * 女性用户总交易额
     */
    private Double womanMoneySum;
    /**
     * 女性用户总交易额
     */
    private Integer newUser;
    /**
     * 最大成交额
     */
    private Double maxMoney;
    private Integer manBuyCount;
    private Integer womanBuyCount;

    public ECommerceYearBean() {

    }

    public ECommerceYearBean(String year, Integer buySum, Integer successSum, Integer failSum, Double moneySum, Double manMoneySum, Double womanMoneySum, Integer manBuyCount, Integer womanBuyCount, Double maxMoney) {
        this.year = year;
        this.buySum = buySum;
        this.successSum = successSum;
        this.failSum = failSum;
        this.moneySum = moneySum;
        this.manMoneySum = manMoneySum;
        this.womanMoneySum = womanMoneySum;
        this.manBuyCount = manBuyCount;
        this.womanBuyCount = womanBuyCount;
        this.maxMoney = maxMoney;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, year);
        preparedStatement.setInt(2, buySum);
        preparedStatement.setInt(3, successSum);
        preparedStatement.setInt(4, failSum);
        preparedStatement.setDouble(5, moneySum);
        preparedStatement.setDouble(6, manMoneySum);
        preparedStatement.setDouble(7, womanMoneySum);
        preparedStatement.setInt(8, manBuyCount);
        preparedStatement.setInt(9, womanBuyCount);
        preparedStatement.setDouble(10, maxMoney);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Double getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(Double moneySum) {
        this.moneySum = moneySum;
    }

    public Double getManMoneySum() {
        return manMoneySum;
    }

    public void setManMoneySum(Double manMoneySum) {
        this.manMoneySum = manMoneySum;
    }

    public Double getWomanMoneySum() {
        return womanMoneySum;
    }

    public void setWomanMoneySum(Double womanMoneySum) {
        this.womanMoneySum = womanMoneySum;
    }

    public Integer getNewUser() {
        return newUser;
    }

    public void setNewUser(Integer newUser) {
        this.newUser = newUser;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getManBuyCount() {
        return manBuyCount;
    }

    public void setManBuyCount(Integer manBuyCount) {
        this.manBuyCount = manBuyCount;
    }

    public Integer getWomanBuyCount() {
        return womanBuyCount;
    }

    public void setWomanBuyCount(Integer womanBuyCount) {
        this.womanBuyCount = womanBuyCount;
    }
}
