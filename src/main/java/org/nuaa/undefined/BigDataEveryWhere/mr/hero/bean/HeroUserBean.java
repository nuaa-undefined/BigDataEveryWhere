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
 * @Date: Created in 2018/8/3 16:28
 */
public class HeroUserBean implements Writable, DBWritable{
    private Integer id;
    private Integer winNum;
    private Integer failNum;
    private Integer heroNum;

    public HeroUserBean() {

    }

    public HeroUserBean(Integer id, Integer winNum, Integer failNum, Integer heroNum) {
        this.id = id;
        this.winNum = winNum;
        this.failNum = failNum;
        this.heroNum = heroNum;
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, winNum);
        preparedStatement.setInt(3, failNum);
        preparedStatement.setInt(4, heroNum);
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
}
