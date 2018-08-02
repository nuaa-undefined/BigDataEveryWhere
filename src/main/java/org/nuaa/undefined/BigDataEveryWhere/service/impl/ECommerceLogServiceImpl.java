package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceLogDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceYearDistributionDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceLogEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 16:08
 */
@Service
public class ECommerceLogServiceImpl implements ECommerceLogService{

    @Autowired
    private ECommerceLogDao eCommerceLogDao;
    @Autowired
    private ECommerceYearDistributionDao eCommerceYearDistributionDao;

    @Override
    public List<ECommerceLogEntity> listMaxConsumeTopLog() {
        String sql = "select user_id, sex, money from e_commerce_log order by money desc limit 10";
        return eCommerceLogDao.listData(sql, new Object[]{});
    }

    @Override
    public List<EComYearDistributionEntity> listShoppingGiveUpData() {
        String sql = "select year, buy_sum, success_sum, fail_sum from e_commerce_year";
        List<EComYearDistributionEntity> eComYearDistributionEntities = eCommerceYearDistributionDao.listData(sql, new Object[]{});
        return eComYearDistributionEntities.stream()
                .map(x -> {
                    x.setGiveUpRate((double) (x.getFailSum() / (double)x.getBuySum()));
                    return x;
                }).collect(Collectors.toList());
    }
}
