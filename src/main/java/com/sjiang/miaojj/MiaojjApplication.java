package com.sjiang.miaojj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-14  14:32
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class MiaojjApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiaojjApplication.class, args);
        log.info("项目启动成功！");
    }
}
