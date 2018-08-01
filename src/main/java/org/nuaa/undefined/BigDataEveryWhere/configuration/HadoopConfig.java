package org.nuaa.undefined.BigDataEveryWhere.configuration;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.net.URI;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/20 16:40
 */
@Configuration
@EnableWebMvc
public class HadoopConfig {
    @Bean
    public org.apache.hadoop.conf.Configuration configuration() {
        return new org.apache.hadoop.conf.Configuration();
    }

    @Bean
    public FileSystem fileSystem(org.apache.hadoop.conf.Configuration configuration) throws IOException, InterruptedException {
        return FileSystem.get(URI.create("hdfs://master:9000/"), configuration, "root");
    }
}
