package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean;

import org.apache.hadoop.io.Text;
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
 * @Date: Created in 2018/7/30 9:45
 */
public class UserBean implements DBWritable, Writable{
    private String id;
    private String sex;
    private int buyCount;
    private int successCount;
    private int failCount;
    private double sumMoney;
    private double maxMoney;
    private String place;
    private String earliestRecord;

    public UserBean() {

    }

    public UserBean(String id, String sex, int buyCount, int successCount, int failCount, double sumMoney, double maxMoney, String place, String earliestRecord) {
        this.id = id;
        this.sex = sex;
        this.buyCount = buyCount;
        this.successCount = successCount;
        this.failCount = failCount;
        this.sumMoney = sumMoney;
        this.maxMoney = maxMoney;
        this.place = place;
        this.earliestRecord = earliestRecord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        Text.writeString(dataOutput, this.id);
        Text.writeString(dataOutput, this.sex);
        Text.writeString(dataOutput, String.valueOf(this.buyCount));
        Text.writeString(dataOutput, String.valueOf(this.successCount));
        Text.writeString(dataOutput, String.valueOf(this.failCount));
        Text.writeString(dataOutput, String.valueOf(sumMoney));
        Text.writeString(dataOutput, String.valueOf(maxMoney));
        Text.writeString(dataOutput, place);
        Text.writeString(dataOutput, earliestRecord);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = Text.readString(dataInput);
        this.sex = Text.readString(dataInput);
        this.buyCount = Integer.parseInt(Text.readString(dataInput));
        this.successCount = Integer.parseInt(Text.readString(dataInput));
        this.failCount = Integer.parseInt(Text.readString(dataInput));
        this.sumMoney = Double.parseDouble(Text.readString(dataInput));
        this.maxMoney = Double.parseDouble(Text.readString(dataInput));
        this.place = Text.readString(dataInput);
        this.earliestRecord = Text.readString(dataInput);
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, sex);
        preparedStatement.setInt(3, buyCount);
        preparedStatement.setInt(4, successCount);
        preparedStatement.setInt(5, failCount);
        preparedStatement.setDouble(6, sumMoney);
        preparedStatement.setDouble(7, maxMoney);
        preparedStatement.setString(8, place);
        preparedStatement.setString(9, earliestRecord);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString("id");
        this.sex = resultSet.getString("sex");
        this.buyCount = resultSet.getInt("buy_count");
        this.successCount = resultSet.getInt("success_count");
        this.failCount = resultSet.getInt("fail_count");
        this.maxMoney = resultSet.getDouble("max_money");
        this.sumMoney = resultSet.getDouble("sum_money");
        this.place = resultSet.getString("place");
        this.earliestRecord = resultSet.getString("earliest_record");
    }
}
