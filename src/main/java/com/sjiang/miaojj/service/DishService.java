package com.sjiang.miaojj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjiang.miaojj.dto.DishDto;
import com.sjiang.miaojj.entity.Dish;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  20:42
 * @Description: TODO
 * @Version: 1.0
 */

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
