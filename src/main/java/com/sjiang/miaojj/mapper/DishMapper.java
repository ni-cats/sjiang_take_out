package com.sjiang.miaojj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjiang.miaojj.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.mapper
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  20:40
 * @Description: TODO
 * @Version: 1.0
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
