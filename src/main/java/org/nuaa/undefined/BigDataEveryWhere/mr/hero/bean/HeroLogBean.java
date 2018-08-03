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
public class HeroLogBean implements Writable, DBWritable{
    private String heroName;
    private Integer userId;
    private Integer status;
    private Integer label;

    public HeroLogBean() {

    }

    public HeroLogBean(String text) {
        String [] data = text.split("\\s+");
        this.heroName = data[0];
        this.userId = Integer.parseInt(data[2]);
        this.status = Integer.parseInt(data[1]);
        this.label = Integer.parseInt(data[3]);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, heroName);
        preparedStatement.setInt(2, userId);
        preparedStatement.setInt(3, status);
        preparedStatement.setInt(4, label);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }
}
