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
 * @Auther: cyw35
 * @Date: 2018/8/1 09:42
 * @Description:
 */
public class GoodsBean implements DBWritable, Writable {
    private String id;
    private int successCounts;
    private int failCounts;
    private int totalCounts;
    private int maleCounts = 0;         //男性购买次数
    private int femaleCounts = 0;       //女性购买次数
    private double totalAmounts = 0;       //成交总额
    private double singleMaxAmounts = 0;   //单笔最大金额
    private String earlistTime = " ";   //最早被浏览时间

    public GoodsBean(){

    }

    public GoodsBean(String id, int successCounts, int failCounts, int totalCounts, int maleCounts, int femaleCounts, double totalAmounts, double singleMaxAmounts, String earlistTime) {
        this.id = id;
        this.successCounts = successCounts;
        this.failCounts = failCounts;
        this.totalCounts = totalCounts;
        this.maleCounts = maleCounts;
        this.femaleCounts = femaleCounts;
        this.totalAmounts = totalAmounts;
        this.singleMaxAmounts = singleMaxAmounts;
        this.earlistTime = earlistTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(int successCounts) {
        this.successCounts = successCounts;
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

    public double getSingleMaxAmounts() {
        return singleMaxAmounts;
    }

    public void setSingleMaxAmounts(double singleMaxAmounts) {
        this.singleMaxAmounts = singleMaxAmounts;
    }

    public String getEarlistTime() {
        return earlistTime;
    }

    public void setEarlistTime(String earlistTime) {
        this.earlistTime = earlistTime;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        Text.writeString(dataOutput, this.id);
        Text.writeString(dataOutput, String.valueOf(this.successCounts));
        Text.writeString(dataOutput, String.valueOf(this.failCounts));
        Text.writeString(dataOutput, String.valueOf(this.totalCounts));
        Text.writeString(dataOutput, String.valueOf(this.maleCounts));
        Text.writeString(dataOutput, String.valueOf(this.femaleCounts));
        Text.writeString(dataOutput, String.valueOf(this.totalAmounts));
        Text.writeString(dataOutput, String.valueOf(this.singleMaxAmounts));
        Text.writeString(dataOutput, this.earlistTime);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = Text.readString(dataInput);
        this.successCounts = Integer.parseInt(Text.readString(dataInput));
        this.failCounts = Integer.parseInt(Text.readString(dataInput));
        this.totalCounts = Integer.parseInt(Text.readString(dataInput));
        this.maleCounts = Integer.parseInt(Text.readString(dataInput));
        this.femaleCounts = Integer.parseInt(Text.readString(dataInput));
        this.totalAmounts = Double.parseDouble(Text.readString(dataInput));
        this.singleMaxAmounts = Double.parseDouble(Text.readString(dataInput));
        this.earlistTime = Text.readString(dataInput);
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, id);
        preparedStatement.setInt(2, successCounts);
        preparedStatement.setInt(3, failCounts);
        preparedStatement.setInt(4, totalCounts);
        preparedStatement.setInt(5, maleCounts);
        preparedStatement.setInt(6, femaleCounts);
        preparedStatement.setDouble(7, totalAmounts);
        preparedStatement.setDouble(8, singleMaxAmounts);
        preparedStatement.setString(9, earlistTime);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString("id");
        this.successCounts = resultSet.getInt("successCounts");
        this.failCounts = resultSet.getInt("failCounts");
        this.totalCounts = resultSet.getInt("totalCounts");
        this.maleCounts = resultSet.getInt("maleCounts");
        this.femaleCounts = resultSet.getInt("femaleCounts");
        this.totalAmounts = resultSet.getDouble("totalAmounts");
        this.singleMaxAmounts = resultSet.getDouble("singleMaxAmounts");
        this.earlistTime = resultSet.getString("earlistTime");
    }
}
