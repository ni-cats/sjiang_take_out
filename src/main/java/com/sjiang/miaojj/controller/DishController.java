package com.sjiang.miaojj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjiang.miaojj.common.R;
import com.sjiang.miaojj.dto.DishDto;
import com.sjiang.miaojj.entity.Category;
import com.sjiang.miaojj.entity.Dish;
import com.sjiang.miaojj.entity.DishFlavor;
import com.sjiang.miaojj.service.CategoryService;
import com.sjiang.miaojj.service.DishFlavorService;
import com.sjiang.miaojj.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.controller
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-16  13:04
 * @Description: TODO 菜品管理
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * @param dishDto:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 新增菜品
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 13:43
     */

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        log.info("新增菜品：{}", dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功！");
    }

    /**
     * @param page:
     * @param pageSize:
     * @param name:
     * @return com.sjiang.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @Description TODO 分页查询
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 14:20
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //查询数据库
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * @param id:
     * @return com.sjiang.reggie.common.R<com.sjiang.reggie.dto.DishDto>
     * @throws
     * @Description TODO 根据id回显菜品信息
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 16:15
     */

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable("id") Long id) {

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /**
     * @param dishDto:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 修改菜品
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 16:16
     */

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        log.info("修改菜品：{}", dishDto.toString());

        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功！");
    }


    /**
     *
     * @Description TODO 展示菜品中的菜系
     * @param dish:
     * @return com.sjiang.reggie.common.R<java.util.List<com.sjiang.reggie.entity.Dish>>
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 9:55
     * @throws
     */

/*    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq( Dish::getStatus, 1); //仅展示起售菜品
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }*/

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq( Dish::getStatus, 1); //仅展示起售菜品
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            Long dishId = item.getId(); //当前菜品的id
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtoList);
    }
}
