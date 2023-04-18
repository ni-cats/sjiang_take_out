package com.sjiang.miaojj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjiang.miaojj.dto.SetmealDto;
import com.sjiang.miaojj.entity.Setmeal;

import java.util.List;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  20:42
 * @Description: TODO
 * @Version: 1.0
 */

public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
}
