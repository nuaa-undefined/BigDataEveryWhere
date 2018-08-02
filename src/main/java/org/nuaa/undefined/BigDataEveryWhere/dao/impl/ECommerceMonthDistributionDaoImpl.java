package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceMonthDistributionDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComMonthDistributionEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:16
 */
@Repository
public class ECommerceMonthDistributionDaoImpl extends DaoHelper implements ECommerceMonthDistributionDao{
    @Override
    public List<EComMonthDistributionEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, EComMonthDistributionEntity.class);
    }

    @Override
    public void updateYearData(String sql, List<EComMonthDistributionEntity> eComMonthDistributionEntities) {
        this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setDouble(1, eComMonthDistributionEntities.get(i).getManMoneySum());
                preparedStatement.setDouble(2, eComMonthDistributionEntities.get(i).getWomanMoneySum());
                preparedStatement.setInt(3, eComMonthDistributionEntities.get(i).getMonth());
            }

            @Override
            public int getBatchSize() {
                return eComMonthDistributionEntities.size();
            }
        });
    }

}
