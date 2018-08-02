package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceLogDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceLogEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 16:08
 */
@Service
public class ECommerceLogServiceImpl implements ECommerceLogService{

    @Autowired
    private ECommerceLogDao eCommerceLogDao;

    @Override
    public List<ECommerceLogEntity> listMaxConsumeTopLog() {
        String sql = "select user_id, sex, money from e_commerce_log order by money desc limit 10";
        return eCommerceLogDao.listData(sql, new Object[]{});
    }
}
