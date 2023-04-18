package com.sjiang.miaojj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjiang.miaojj.entity.Orders;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-17  22:18
 * @Description: TODO
 * @Version: 1.0
 */

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
