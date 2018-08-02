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
 * @Date: Created in 2018/8/2 10:42
 */
public class ECommerceLogBean implements Writable, DBWritable{

    private String ecomId;
    private String goodId;
    private String regionId;
    private String userId;
    private Integer sex;
    private String begTime;
    private String buyTime;
    private Double money;
    private Integer status;

    public ECommerceLogBean() {

    }
    public ECommerceLogBean(String text) {
        String[] data = text.split("\\s+");
        this.ecomId = data[0];
        this.goodId = data[1];
        this.regionId = data[2];
        this.userId = data[3];
        this.sex = Integer.parseInt(data[4]);
        this.begTime = data[5];
        this.buyTime = data[6];
        this.money = Double.parseDouble(data[7]);
        this.status = Integer.parseInt(data[8]);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, ecomId);
        preparedStatement.setString(2, goodId);
        preparedStatement.setString(3, regionId);
        preparedStatement.setString(4, userId);
        preparedStatement.setInt(5, sex);
        preparedStatement.setString(6, begTime);
        preparedStatement.setString(7, buyTime);
        preparedStatement.setDouble(8, money);
        preparedStatement.setInt(9, status);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }
}
