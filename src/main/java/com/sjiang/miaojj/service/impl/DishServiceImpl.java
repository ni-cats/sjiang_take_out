package com.sjiang.miaojj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjiang.miaojj.dto.DishDto;
import com.sjiang.miaojj.entity.Dish;
import com.sjiang.miaojj.entity.DishFlavor;
import com.sjiang.miaojj.mapper.DishMapper;
import com.sjiang.miaojj.service.DishFlavorService;
import com.sjiang.miaojj.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
 * @CreateTime: 2023-04-15  20:43
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * @param dishDto:
     * @return void
     * @throws
     * @Description TODO 新增菜品，同时保存对应的口味数据
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 13:51
     */

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品dish
        this.save(dishDto);

        Long dishId = dishDto.getId();  //菜品id

        //封装口味表的时候，要先设置口味对应的菜品也就是菜品id
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存口味信息到口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * @param id:
     * @return com.sjiang.reggie.dto.DishDto
     * @throws
     * @Description TODO 根据id查询菜品信息和口味信息
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 16:02
     */

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //从dish表中查询菜品信息
        Dish dish = this.getById(id);

        //拷贝数据
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        //从dish_flavor表中查询当前菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /**
     * @param dishDto:
     * @return void
     * @throws
     * @Description TODO 更新菜品和口味信息
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 16:17
     */

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {

        //更新dish表 菜品表
        this.updateById(dishDto);

        //更新口味表 dish_flavor，先对当前菜品口味数据进行清理，再重新添加
        //清理口味数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //重新添加数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
