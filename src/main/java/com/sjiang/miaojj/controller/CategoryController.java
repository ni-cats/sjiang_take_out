package com.sjiang.miaojj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjiang.miaojj.common.R;
import com.sjiang.miaojj.entity.Category;
import com.sjiang.miaojj.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.controller
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  17:02
 * @Description: TODO 分类管理
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @param category:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 新增分类
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 17:11
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category.toString());
        categoryService.save(category);
        return R.success("新增分类成功！");
    }

    /**
     * @param page:
     * @param pageSize:
     * @return com.sjiang.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @Description TODO 分页查询数据
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 19:12
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {

        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        //条件过滤器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);

        //查询数据库
        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * @param ids:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 根据id删除分类
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 19:13
     */

    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除ids为{}的分类", ids);
//        categoryService.removeById(ids);
        categoryService.remove(ids);

        return R.success("分类信息删除成功！");
    }

    /**
     * @param category:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 修改分类
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 21:21
     */

    @PutMapping
    public R<String> update(@RequestBody Category category) {

        log.info("修改分类信息:{}", category.toString());
        categoryService.updateById(category);
        return R.success("修改成功！");
    }

    /**
     * @param category:
     * @return com.sjiang.reggie.common.R<java.util.List < com.sjiang.reggie.entity.Category>>
     * @throws
     * @Description TODO 根据条件查询分类
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/16 13:14
     */

    @GetMapping("/list")
    public R<List<Category>> list(Category category) {

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);

    }
}
