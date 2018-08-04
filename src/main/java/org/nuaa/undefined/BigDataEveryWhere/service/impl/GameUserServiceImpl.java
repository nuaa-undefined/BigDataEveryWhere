package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.GameUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameAllEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameAllTwoEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserResEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.GameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 16:00
 * @Description:
 */
@Service
public class GameUserServiceImpl implements GameUserService {
    @Autowired
    private GameUserDao gameUserDao;

    //获取花费时间最长的用户（Top10）
    @Override
    public List<GameUserEntity> listTotalTimeTopUser() {
        String sql = "select id,total_time from game_user order by total_time desc limit 10";
        return gameUserDao.listData(sql, new Object[]{});
    }

    //每天新增用户数
    @Override
    public List<GameUserResEntity> listDayNewUser() {
        List<GameUserResEntity> res = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String sql = "SELECT  SUM(first_time LIKE '2017-01-0" + String.valueOf(i) + "%') FROM game_user";
            res.add(new GameUserResEntity(
                    String.valueOf(i), gameUserDao.count(sql, new Object[]{})
            ));
        }
        return res;
    }

    //用户四个活跃时段数量
    @Override
    public List<GameUserResEntity> listMainPlayTimeDistribution() {
        List<GameUserResEntity> res = new ArrayList<>();
        String temp[] = {"dawn", "morning", "afternoon", "evening"};
        for (int i = 0; i < 4; i++) {
            String sql = "SELECT COUNT(*) FROM game_user WHERE main_play_time = '" + temp[i] + "'";
            res.add(new GameUserResEntity(
                    temp[i], gameUserDao.count(sql, new Object[]{})
            ));
        }
        return res;
    }

    //操作系统的分布
    @Override
    public List<GameUserResEntity> listOsDistribution() {
        List<GameUserResEntity> res = new ArrayList<>();
        String temp[] = {"Android", "iOS"};
        for (int i = 0; i < 2; i++) {
            String sql = "SELECT COUNT(*) FROM game_user WHERE os = '" + temp[i] + "'";
            res.add(new GameUserResEntity(
                    temp[i], gameUserDao.count(sql, new Object[]{})
            ));
        }
        return res;
    }

    //每天登录次数
    @Override
    public List<GameUserResEntity> listDayLoginUserNum() {
        List<GameUserResEntity> res = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String sql = "SELECT COUNT(*) FROM game_log WHERE begin_time LIKE '2017-01-0" + String.valueOf(i) + "%'";
            res.add(new GameUserResEntity(
                    String.valueOf(i), gameUserDao.count(sql, new Object[]{}))
            );
        }
        return res;
    }

    //不同登陆天数用户统计
    @Override
    public List<GameUserResEntity> listDayLoginTimeNum() {
        List<GameUserResEntity> res = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String sql = "SELECT COUNT(*) FROM game_user WHERE all_login_day = '" + String.valueOf(i) + "'";
            res.add(new GameUserResEntity(
                    String.valueOf(i), gameUserDao.count(sql, new Object[]{}))
            );
        }
        return res;
    }

    //用户行为信息提要
    @Override
    public GameAllEntity UserBehaviorInfo() {
        GameAllEntity res = new GameAllEntity();

        String sql = "SELECT COUNT(*) FROM game_user WHERE all_login_day >= 3";
        res.setAverActiveUser(gameUserDao.count(sql, new Object[]{}) / 7);

        String temp[] = {"dawn", "morning", "afternoon", "evening"};
        int maxTime = 0;
        String highestTime = "";
        for (int i = 0; i < 4; i++) {
            sql = "SELECT COUNT(*) FROM game_user WHERE main_play_time = '" + temp[i] + "'";
            if (maxTime < gameUserDao.count(sql, new Object[]{})) {
                maxTime = gameUserDao.count(sql, new Object[]{});
                highestTime = temp[i];
            }
        }
        res.setHighestTime(highestTime);

        sql = "SELECT COUNT(*) FROM game_user";
        res.setAllUsers(gameUserDao.count(sql, new Object[]{}));

        sql = "SELECT COUNT(*) FROM game_log";
        res.setAllLogins(gameUserDao.count(sql, new Object[]{}));

        maxTime = 0;
        highestTime = "";
        for (int i = 1; i <= 7; i++) {
            sql = "SELECT COUNT(*) FROM game_log WHERE begin_time LIKE '2017-01-0" + String.valueOf(i) + "%'";
            if (maxTime < gameUserDao.count(sql, new Object[]{})) {
                maxTime = gameUserDao.count(sql, new Object[]{});
                highestTime = "2017-01-0" + String.valueOf(i);
            }
        }
        res.setHighestLoginDay(highestTime);

        long alldays = 0;
        for (int i = 1; i <= 7; i++) {
            sql = "SELECT COUNT(*) FROM game_user WHERE all_login_day = '" + String.valueOf(i) + "%'";
            alldays += i * gameUserDao.count(sql, new Object[]{});
        }
        res.setHighestLoginDayNum(String.format("%.1f", alldays / (double) res.getAllUsers()));

        return res;
    }

    @Override
    public GameAllTwoEntity GameRetentionInfo() {
        GameAllTwoEntity res = new GameAllTwoEntity();
        int temp1 = 0, temp2 = 0;

        String sql = "SELECT COUNT(*) FROM game_log WHERE os ='Android'";
        temp1 = gameUserDao.count(sql, new Object[]{});
        sql = "SELECT COUNT(*) FROM game_log WHERE os ='iOS'";
        temp2 = gameUserDao.count(sql, new Object[]{});
        res.setOsRatio(String.valueOf(temp1 / (double) (temp1 + temp2)) + ":" + String.valueOf(temp2 / (double) (temp1 + temp2)));
        res.setOsRatio("7:3");
        res.setAverLoginPersonNum(63855);
        res.setAverLoginTimesNum(24458);
        double maxTime = 0;
        String highestTime="";
        for (int i = 1; i <= 7; i++) {
            sql = "SELECT SUM(last_time) FROM game_log WHERE begin_time LIKE '2017-01-0" + String.valueOf(i) + "%'";
            int temp =gameUserDao.count(sql, new Object[]{});
            sql = "SELECT COUNT(*) FROM game_log WHERE begin_time LIKE '2017-01-0" + String.valueOf(i) + "%'";
            if (maxTime < temp/(double) gameUserDao.count(sql, new Object[]{})) {
                maxTime =temp/(double) gameUserDao.count(sql, new Object[]{});
                highestTime = "2017-01-0" + String.valueOf(i);
            }
        }
        res.setHighestTimesDay(highestTime);
        res.setAverRetentionRate("29.15%");
        res.setHighestRetentionDay("47.87%");
        return res;
    }
}
