package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.*;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/5 12:47
 */
@Service
public class ECommerceServiceImpl implements ECommerceService{
    @Autowired
    private ECommerceGoodsDao eCommerceGoodsDao;
    @Autowired
    private ECommerceUserDao eCommerceUserDao;
    @Autowired
    private ECommerceYearDistributionDao eCommerceYearDistributionDao;
    @Autowired
    private ECommerceMonthDistributionDao eCommerceMonthDistributionDao;
    @Autowired
    private ECommerceRegionDao regionDao;
    @Autowired
    private ECommerceLogDao logDao;

    @Override
    public ECommerceEntity getSystemData() {
        ECommerceEntity eCommerceEntity = new ECommerceEntity();
        eCommerceEntity.setUserNum(eCommerceUserDao.count());
        eCommerceEntity.setActiveUserNum(eCommerceUserDao.count(
                "select count(*) from e_commerce_user where buy_count > 10", new Object[]{}
        ));
        eCommerceEntity.setGoodNum(eCommerceGoodsDao.count());
        eCommerceEntity.setConsumeNum(logDao.count(
                "select count(*) from e_commerce_log", new Object[]{}
        ));
        eCommerceEntity.setSumMoney(logDao.getDouble(
                "select sum(money) from e_commerce_log where status = 1", new Object[]{}
        ));
        eCommerceEntity.setSuccessRate(logDao.getDouble(
                "select sucCount / sumCount as successRate from (select count(*) as sucCount from e_commerce_log where status = 1)a, " +
                        "(select count(*) as sumCount from e_commerce_log)b", new Object[]{}
        ));
        return eCommerceEntity;
    }

    @Override
    public List<EComYearDistributionEntity> getUserAddNumByYear() {
        String sql = "select year, new_user from e_commerce_year order by year";
        List<EComYearDistributionEntity> yearDistributionEntities = eCommerceYearDistributionDao.listData(sql, new Object[]{});
        for (EComYearDistributionEntity in : yearDistributionEntities) {
            in.setConsumeUserNum(logDao.count(
                    "select count(distinct user_id) from e_commerce_log where beg_time like ? and status = 1", new Object[]{in.getYear()+'%'}
            ));
        }
        return yearDistributionEntities;
    }
}
