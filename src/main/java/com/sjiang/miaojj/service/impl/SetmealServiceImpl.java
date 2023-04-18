package com.sjiang.miaojj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjiang.miaojj.common.CustomException;
import com.sjiang.miaojj.dto.SetmealDto;
import com.sjiang.miaojj.entity.Setmeal;
import com.sjiang.miaojj.entity.SetmealDish;
import com.sjiang.miaojj.mapper.SetmealMapper;
import com.sjiang.miaojj.service.SetmealDishService;
import com.sjiang.miaojj.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service.impl
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  20:44
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    /**
     * @param setmealDto:
     * @return void
     * @throws
     * @Description TODO 同时保存新增套餐，和套餐和菜品的关联关系
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 10:19
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息,操作表setmeal
        this.save(setmealDto);


        //保存套餐和菜品的关联关系,操作表setmeal_dish
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * @param ids:
     * @return
     * @throws
     * @Description TODO 删除套餐，同时删除套餐和菜品的关联数据
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 11:28
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐的状态，确认是否可以删除
        //select count(*) from setmeal where id in (ids) and status = 1
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);

        //如果不能删除。抛出异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，无法删除！");
        }

        //如果可以删除，先删除套餐表中得数据
        this.removeByIds(ids);

        //删除关系表中的关系
        //delete from setmeal_dish where setmeal_id in (ids)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
