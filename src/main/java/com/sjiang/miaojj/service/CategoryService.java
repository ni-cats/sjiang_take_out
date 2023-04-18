package com.sjiang.miaojj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjiang.miaojj.entity.Category;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  16:59
 * @Description: TODO
 * @Version: 1.0
 */

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
