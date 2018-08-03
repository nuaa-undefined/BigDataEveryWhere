package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceMonthDistributionDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceUserDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceYearDistributionDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComMonthDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceTimeDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:14
 */
@Service
public class ECommerceTimeDistributionServiceImpl implements ECommerceTimeDistributionService{
    @Autowired
    private ECommerceUserDao eCommerceUserDao;
    @Autowired
    private ECommerceYearDistributionDao eCommerceYearDistributionDao;
    @Autowired
    private ECommerceMonthDistributionDao eCommerceMonthDistributionDao;

    @Override
    public List<EComYearDistributionEntity> listYearMoney() {
        String sql = "select year, money_sum from e_commerce_year";
        return eCommerceYearDistributionDao.listData(sql, new Object[]{});
    }

    @Override
    public void updateData() {
        String sql = "select * from e_commerce_user";
        String sqlYear = "select * from e_commerce_year";
        String sqlMonth = "select * from e_commerce_month";

        List<ECommerceUserEntity> eCommerceUserEntities = eCommerceUserDao.listData(sql, new Object[]{});
        List<EComYearDistributionEntity> eComYearDistributionEntities = eCommerceYearDistributionDao.listData(sqlYear, new Object[]{});
        List<EComMonthDistributionEntity> eComMonthDistributionEntities = eCommerceMonthDistributionDao.listData(sqlMonth, new Object[]{});

        Map<String, EComYearDistributionEntity> eComYearDistributionEntityMap = new HashMap<>();
        for (EComYearDistributionEntity in : eComYearDistributionEntities) {
            eComYearDistributionEntityMap.put(in.getYear(), in);
        }
        for (ECommerceUserEntity user: eCommerceUserEntities) {
            eComYearDistributionEntityMap.get(user.getEarliestRecord().split("-")[0]).setNewUser(
                    eComYearDistributionEntityMap.get(user.getEarliestRecord().split("-")[0]).getNewUser() != null ?
                            eComYearDistributionEntityMap.get(user.getEarliestRecord().split("-")[0]).getNewUser() + 1 :
                            1
            );
        }
        List<EComYearDistributionEntity> result = new ArrayList<>();
        for (Map.Entry<String, EComYearDistributionEntity> eComYearDistributionEntityEntry : eComYearDistributionEntityMap.entrySet()) {
            result.add(eComYearDistributionEntityEntry.getValue());
        }

        for (EComYearDistributionEntity in : result) {
            in.setManMoneySum(in.getMoneySum() / 4);
            in.setWomanMoneySum((in.getMoneySum() / 4) * 3);
        }

        for (EComMonthDistributionEntity in : eComMonthDistributionEntities) {
            in.setManMoneySum(in.getMoneySum() / 4);
            in.setWomanMoneySum((in.getMoneySum() / 4) * 3);
        }

        String updateYearSql = "update e_commerce_year set man_money_sum = ?, woman_money_sum = ?, new_user = ? where year = ?";
        String updateMonthSql = "update e_commerce_month set man_money_sum = ?, woman_money_sum = ? where month = ?";
        eCommerceMonthDistributionDao.updateYearData(updateMonthSql, eComMonthDistributionEntities);
        eCommerceYearDistributionDao.updateYearData(updateYearSql, result);
    }
}
