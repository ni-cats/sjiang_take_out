package com.sjiang.miaojj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjiang.miaojj.common.CustomException;
import com.sjiang.miaojj.entity.Category;
import com.sjiang.miaojj.entity.Dish;
import com.sjiang.miaojj.entity.Setmeal;
import com.sjiang.miaojj.mapper.CategoryMapper;
import com.sjiang.miaojj.service.CategoryService;
import com.sjiang.miaojj.service.DishService;
import com.sjiang.miaojj.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service.impl
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  17:00
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * @param id:
     * @return void
     * @throws
     * @Description TODO 根据id删除分类，删除之前要先进行判断
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 20:48
     */
    @Override
    public void remove(Long id) {

        //查询当前分类是否关联了菜品，如果关联，抛出异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            throw new CustomException("该分类关联了菜品，不能删除！");
        }

        //查询当前分类是否关联了套餐，如果关联，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);

        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new CustomException("该分类关联了套餐，不能删除！");
        }

        //正常删除分类
        super.removeById(id);
    }

}
