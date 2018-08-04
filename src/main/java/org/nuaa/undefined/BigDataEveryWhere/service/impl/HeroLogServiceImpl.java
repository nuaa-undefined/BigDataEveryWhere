package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.HeroDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroLogDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroInfoEntity;
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

    @Override
    public HeroInfoEntity getHeroInfo() {
        HeroInfoEntity hero = new HeroInfoEntity();
        hero.setUserNum(heroUserDao.count("select count(*) from hero_user", new Object[]{}));
        hero.setGameNum(heroLogDao.count("select count(*) from hero_log", new Object[]{}));
        hero.setHeroNum(heroDao.count("select count(*) from hero", new Object[]{}));
        hero.setMoreUseHero(heroDao.getHeroName("select name from hero order by (win_num + fail_num) desc limit 1", new Object[]{}));
        hero.setMostUsefulHero(heroDao.getHeroName("select name from hero order by win_num /(win_num + fail_num) desc limit 1", new Object[]{}));
        hero.setSumWinRate(heroDao.getSumRate("SELECT SUM(win_num) / (SUM(win_num)+SUM(fail_num)) FROM hero"));
        return hero;
    }
}
