package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ECommerceUserEntity> listData(int limit) {
        return eCommerceUserDao.listData(
                "select * from e_commerce_user limit ?",
                new Object[]{limit}
        );
    }
}
