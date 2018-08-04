package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.HeroDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroLogDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroLogEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:19
 */
@Service
public class HeroServiceImpl implements HeroService{
    @Autowired
    private HeroLogDao heroLogDao;
    @Autowired
    private HeroDao heroDao;
    @Autowired
    private HeroUserDao heroUserDao;

    @Override
    public List<HeroEntity> getHeroInfo(String heroName) {
        List<HeroEntity> heroes = heroDao.listData("select * from hero where name = ?", new Object[]{heroName});
        if(heroes.size() == 0) {
            return null;
        }
        List<HeroLogEntity> heroLogEntities = heroLogDao.listData("select * from hero_log where hero_name = ?", new Object[]{heroName});
        List<Integer> labelUseNum = new ArrayList<>();
        List<Integer> labelWinNum = new ArrayList<>();
        List<Double> userWinRate = new ArrayList<>();
        int [] userUseNum = new int[300];
        int [] userWinNum = new int[300];
        int gameCount = heroLogDao.count("select count(*) from hero_log", new Object[]{});
        for (int i = 0; i < 5; i++) {
            labelUseNum.add(0);
            labelWinNum.add(0);
        }
        for (HeroLogEntity in : heroLogEntities) {
            labelUseNum.set(in.getLabel() - 1, labelUseNum.get(in.getLabel() - 1) + 1);
            labelWinNum.set(in.getLabel() - 1, in.getStatus() == 1 ? labelWinNum.get(in.getLabel() - 1) + 1 : labelWinNum.get(in.getLabel() - 1));
            userUseNum[in.getUserId()] ++;
            userWinNum[in.getUserId()] += in.getStatus() == 1 ? 1 : 0;
        }
        for (int i = 0; i < userUseNum.length; i++) {
            if (userUseNum[i] != 0) {
                userWinRate.add(userWinNum[i] / (double) userUseNum[i]);
            } else {
                userWinRate.add(0d);
            }
        }
        heroes.get(0).setLabelUseNum(labelUseNum);
        heroes.get(0).setLabelWinNum(labelWinNum);
        heroes.get(0).setUseRate(heroes.get(0).getUserNum() / (double) gameCount);
        heroes.get(0).setTop3Users(getTop3Users(userWinRate, userWinNum));
        return heroes;
    }

    /**
     * 获取胜率前三的用户
     * @param data
     * @return
     */
    private List<HeroUserEntity> getTop3Users(List<Double> data, int [] winNum) {
        List<HeroUserEntity> heroUserEntities = new ArrayList<>();
        List<HeroUserEntity> result = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            HeroUserEntity bean = new HeroUserEntity();
            bean.setId(i);
            bean.setWinRate(data.get(i));
            bean.setWinNum(winNum[i]);
            heroUserEntities.add(bean);
        }
        heroUserEntities = heroUserEntities.stream()
                .filter(x -> x.getWinNum() >= 3)
                .sorted((x, y) -> y.getWinRate().compareTo(x.getWinRate()))
                .collect(Collectors.toList());
        int i = 3;
        while (i < heroUserEntities.size()) {
            heroUserEntities.remove(i);
        }
        return heroUserEntities;
    }

    @Override
    public List<HeroEntity> getHeroUseRate() {
        String sql = "select name, (win_num + fail_num) as use_sum, win_num / (win_num + fail_num) as win_rate from hero order by use_sum desc";
        return heroDao.listData(sql, new Object[]{});
    }

    @Override
    public List<HeroEntity> getStarHeroList() {
        List<HeroLogEntity> heroLogEntities = heroLogDao.listData("select * from hero_log", new Object[]{});
        return null;
    }
}
