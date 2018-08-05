package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.Element;
import org.nuaa.undefined.BigDataEveryWhere.entity.Response;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.ConstCommerceValue;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public ResponseEntity getUserConsumeRange() {
        List<Element<Integer>> data = new ArrayList<>();
        data.add(new Element<>(">100000元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money > 100000", new Object[]{})));
        data.add(new Element<>("10000-99999元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money between 10000 and 99999", new Object[]{})));
        data.add(new Element<>("1000-9999元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money between 1000 and 9999", new Object[]{})));
        data.add(new Element<>("0-999元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money between 0 and 999", new Object[]{})));
        data.add(new Element<>(">10000元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money / success_count > 10000", new Object[]{})));
        data.add(new Element<>("1000-9999元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money / success_count between 1000 and 9999", new Object[]{})));
        data.add(new Element<>("100-999元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money / success_count between 100 and 999", new Object[]{})));
        data.add(new Element<>("0-99元", eCommerceUserDao.count("select count(*) from e_commerce_user where sum_money / success_count between 0 and 99", new Object[]{})));
        return new ResponseEntity(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                data
        );
    }
}
