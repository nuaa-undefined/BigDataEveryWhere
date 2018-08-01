package org.nuaa.undefined.BigDataEveryWhere.configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/18 20:33
 */
@Configuration
@EnableSwagger
@EnableWebMvc
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(".*?");
    }

    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo(
//                "My Apps API Title",
//                "My Apps API Description",
//                "My Apps API terms of service",
//                "My Apps API Contact Email",
//                "My Apps API Licence Type",
//                "My Apps API License URL");
        ApiInfo apiInfo = new ApiInfo(
                "日志服务api",
                "日志服务主要为解决,我们现有的系统和第三方交互时产生输入输出做记录，为后续运维第一时间确定问题的原因\r联系人：奋斗的大侠",
                "日志服务目前接入端有 phoneClient,.netClient,javaClient",
                "duanjj@htmitech.com",
                "FREE",//Licence Type
                "http://localhost:8080/logcrab/license");
        return apiInfo;
    }
}