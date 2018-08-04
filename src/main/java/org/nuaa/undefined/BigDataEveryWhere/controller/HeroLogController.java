package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroInfoEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.HeroLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 23:37
 */
@Controller
@CrossOrigin
@RequestMapping("/hero/log")
public class HeroLogController {
    @Autowired
    private HeroLogService heroLogService;

    @GetMapping("/getHeroInfo")
    public @ResponseBody
    HeroInfoEntity getHeroInfo() {
        return heroLogService.getHeroInfo();
    }
}
