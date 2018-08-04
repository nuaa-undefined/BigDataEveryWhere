package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.Response;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 19:17
 */
@Controller
@CrossOrigin
@RequestMapping("/hero/heroes")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping("/getHeroInfo")
    public @ResponseBody
    ResponseEntity<HeroEntity> getHeroInfo(String name) {
        List<HeroEntity> heroEntities = heroService.getHeroInfo(name);
        return heroEntities != null ?
                new ResponseEntity<>(
                        Response.GET_DATA_SUCCESS_CODE, "获取数据成功", heroEntities
                ) : new ResponseEntity<>(400, "英雄不存在", 0, null);
    }

    @GetMapping("/getHeroUseRate")
    public @ResponseBody
    ResponseEntity<HeroEntity> getHeroUseRate(String name) {
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE, "获取数据成功", heroService.getHeroUseRate()
        );
    }


}
