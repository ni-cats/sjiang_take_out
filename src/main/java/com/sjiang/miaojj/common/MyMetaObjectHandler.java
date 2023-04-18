package com.sjiang.miaojj.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.common
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  15:15
 * @Description: TODO 自定义元数据对象处理器
 * @Version: 1.0
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * @param metaObject:
     * @return void
     * @throws
     * @Description TODO 插入时自动填充
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 15:22
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[插入]：{}", metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * @param metaObject:
     * @return void
     * @throws
     * @Description TODO 更新时自动填充
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 15:23
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[更新]：{}", metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("当前线程id：{}",id);

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
