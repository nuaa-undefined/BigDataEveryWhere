package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.HeroDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroLogDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroUserDao;
import org.nuaa.undefined.BigDataEveryWhere.service.HeroLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:21
 */
@Service
public class HeroLogServiceImpl implements HeroLogService{
    @Autowired
    private HeroLogDao heroLogDao;
    @Autowired
    private HeroDao heroDao;
    @Autowired
    private HeroUserDao heroUserDao;
}
