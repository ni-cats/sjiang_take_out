package com.sjiang.miaojj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjiang.miaojj.common.R;
import com.sjiang.miaojj.dto.SetmealDto;
import com.sjiang.miaojj.entity.Category;
import com.sjiang.miaojj.entity.Setmeal;
import com.sjiang.miaojj.service.CategoryService;
import com.sjiang.miaojj.service.SetmealDishService;
import com.sjiang.miaojj.service.SetmealService;
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
 * @CreateTime: 2023-04-17  09:21
 * @Description: TODO 套餐管理
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * @param setmealDto:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 新增套餐
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 9:58
     */

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("待添加的套餐信息:{}", setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功！");
    }

    /**
     * @param page:
     * @param pageSize:
     * @param name:
     * @return com.sjiang.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @Description TODO 套餐的分页查询
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 10:46
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> pageDto = new Page<>();
        //添加查询条件，根据name进行模糊查询
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        //排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, pageDto, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询对象
            Category category = categoryService.getById(categoryId);
            //分类名称及赋值
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        pageDto.setRecords(list);
        return R.success(pageDto);
    }

    /**
     * @param ids:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 删除套餐
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 11:25
     */

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("待删除的套餐id：{}", ids);

        setmealService.removeWithDish(ids);

        return R.success("删除成功！");


    }

    /**
     * @param setmeal:
     * @return com.sjiang.reggie.common.R<java.util.List < com.sjiang.reggie.entity.Setmeal>>
     * @throws
     * @Description TODO 根据条件查询套餐数据
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 20:14
     */

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);

    }
}
