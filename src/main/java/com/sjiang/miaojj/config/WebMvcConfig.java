package com.sjiang.miaojj.config;

import com.sjiang.miaojj.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.config
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-14  14:37
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 
     * @Description TODO 设置静态资源映射
     * @param registry:
     * @return void
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/14 14:39
     * @throws 
     */
    
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 
     * @Description TODO 扩展MVC框架的消息转换器
     * @param converters:
     * @return void
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 14:07
     * @throws 
     */
    
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器的调用...");

        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        //设置对象转转器，底层底层使用Jackson将java对象转换成json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        //将上面的消息转换器对象追加到MVC框架的转换器集合中
        converters.add(0,messageConverter);
    }
}
