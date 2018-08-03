package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceLogDao;
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
import java.util.stream.Collectors;

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
    @Autowired
    private ECommerceLogDao eCommerceLogDao;

    @Override
    public List<EComYearDistributionEntity> listYearMoney() {
        String sql = "select year, money_sum from e_commerce_year";
        return eCommerceYearDistributionDao.listData(sql, new Object[]{}).stream()
                .sorted((x, y) ->new Integer(Integer.parseInt(x.getYear())).compareTo(
                        new Integer(Integer.parseInt(y.getYear())))).collect(Collectors.toList());
    }

    @Override
    public List<EComMonthDistributionEntity> listMonthSexCountDistribute() {
        String sql = "select * from e_commerce_month order by month";
        return eCommerceMonthDistributionDao.listData(sql, new Object[]{});
    }

    @Override
    public List<EComYearDistributionEntity> listYearSexCountDistribute() {
        String sql = "select year, man_buy_count, woman_buy_count, money_sum, man_money_sum, woman_money_sum from e_commerce_year order by year";
        String sqlCount = "select count(*) from e_commerce_log where beg_time like ?";
        String sqlCountMan = "select count(*) from e_commerce_log where sex = 1 and beg_time like ?";
        String sqlCountWoman = "select count(*) from e_commerce_log where sex = 2 and beg_time like ?";
        return eCommerceYearDistributionDao.listData(sql, new Object[]{}).stream()
                .map(x -> {
                    Object[] keys = new Object[]{x.getYear() + "%"};
                    x.setSumUserNum(eCommerceLogDao.count(sqlCount, keys));
                    x.setManUserNum(eCommerceUserDao.count(sqlCountMan, keys));
                    x.setWomanUserNum(eCommerceUserDao.count(sqlCountWoman, keys));
                    return x;
                }).collect(Collectors.toList());
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
