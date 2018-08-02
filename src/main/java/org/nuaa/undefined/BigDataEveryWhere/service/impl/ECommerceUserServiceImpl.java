package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.ConstCommerceValue;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 21:08
 */
@Service
public class ECommerceUserServiceImpl implements ECommerceUserService {

    @Autowired
    private ECommerceUserDao eCommerceUserDao;

    @Override
    public List<ECommerceUserEntity> listSumMoneyTopUsers() {
        String sql = "select id, sex, sum_money from e_commerce_user order by sum_money desc limit 10";
        return eCommerceUserDao.listData(sql, new Object[]{});
    }

    @Override
    public List<ECommerceUserEntity> listActiveTopUsers() {
        String sql = "select * from e_commerce_user order by buy_count desc limit 10";
        return eCommerceUserDao.listData(sql, new Object[]{}).stream().map(x -> {
            x.setPlaceName(ConstCommerceValue.CITY_CODE_NAME_MAP.get(x.getPlace()));
            return x;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ECommerceUserEntity> listData(int page, int limit) {
        return eCommerceUserDao.listData(
                "select * from e_commerce_user limit ?,?",
                new Object[]{limit * (page - 1), limit}
        );
    }

    @Override
    public int getUserNum() {
        return eCommerceUserDao.count();
    }
}
