package com.sjiang.miaojj.controller;

import com.sjiang.miaojj.common.R;
import com.sjiang.miaojj.entity.Orders;
import com.sjiang.miaojj.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.controller
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-17  22:21
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @param orders:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 用户下单
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 22:25
     */

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {

        log.info("提交的购物车数据：{}", orders.toString());
        orderService.submit(orders);
        return R.success("下单成功！");
    }
}
