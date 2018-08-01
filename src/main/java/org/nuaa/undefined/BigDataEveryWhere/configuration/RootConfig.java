package org.nuaa.undefined.BigDataEveryWhere.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/5/12 10:00
 */
@Configuration
@ComponentScan(basePackages = {"org.nuaa.undefined.BigDataEveryWhere"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION,value = EnableWebMvc.class)})
public class RootConfig {

}