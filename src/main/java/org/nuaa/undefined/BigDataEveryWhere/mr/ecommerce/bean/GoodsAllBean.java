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
 * @Auther: cyw35
 * @Date: 2018/8/1 15:23
 * @Description:
 */
public class GoodsAllBean implements DBWritable, Writable {
    private String id;
    private int successCounts;
    private int failCounts;
    private int totalCounts;
    private int maleCounts = 0;         //男性购买次数
    private int femaleCounts = 0;       //女性购买次数
    private double totalAmounts = 0;       //成交总额
    private int season;
    private String highestRegion;
    private double abandonRate;
    private double maleRate;
    private double femaleRate;

    public GoodsAllBean(){

    }

    public GoodsAllBean(String id, int successCounts, int failCounts, int totalCounts, int maleCounts, int femaleCounts, double totalAmounts, int season, String highestRegion, double abandonRate, double maleRate, double femaleRate) {
        this.id = id;
        this.successCounts = successCounts;
        this.failCounts = failCounts;
        this.totalCounts = totalCounts;
        this.maleCounts = maleCounts;
        this.femaleCounts = femaleCounts;
        this.totalAmounts = totalAmounts;
        this.season = season;
        this.highestRegion = highestRegion;
        this.abandonRate = abandonRate;
        this.maleRate = maleRate;
        this.femaleRate = femaleRate;
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


    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getHighestRegion() {
        return highestRegion;
    }

    public void setHighestRegion(String highestRegion) {
        this.highestRegion = highestRegion;
    }

    public double getAbandonRate() {
        return abandonRate;
    }

    public void setAbandonRate(double abandonRate) {
        this.abandonRate = abandonRate;
    }

    public double getMaleRate() {
        return maleRate;
    }

    public void setMaleRate(double maleRate) {
        this.maleRate = maleRate;
    }

    public double getFemaleRate() {
        return femaleRate;
    }

    public void setFemaleRate(double femaleRate) {
        this.femaleRate = femaleRate;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

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
        preparedStatement.setInt(8,season);
        preparedStatement.setString(9,highestRegion);
        preparedStatement.setDouble(10,abandonRate);
        preparedStatement.setDouble(11,maleRate);
        preparedStatement.setDouble(12,femaleRate);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }
}
