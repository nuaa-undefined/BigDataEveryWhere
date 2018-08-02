package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceYearDistributionDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:09
 */
@Repository
public class EComYearDistributionDaoImpl extends DaoHelper implements ECommerceYearDistributionDao {
    @Override
    public List<EComYearDistributionEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, EComYearDistributionEntity.class);
    }

    @Override
    public void updateYearData(String sql, List<EComYearDistributionEntity> eComYearDistributionEntities) {
        this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setDouble(1, eComYearDistributionEntities.get(i).getManMoneySum());
                preparedStatement.setDouble(2, eComYearDistributionEntities.get(i).getWomanMoneySum());
                preparedStatement.setInt(3, eComYearDistributionEntities.get(i).getNewUser() != null ?
                eComYearDistributionEntities.get(i).getNewUser() : 0);
                preparedStatement.setString(4, eComYearDistributionEntities.get(i).getYear());
            }

            @Override
            public int getBatchSize() {
                return eComYearDistributionEntities.size();
            }
        });
    }
}
