package com.sjiang.miaojj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjiang.miaojj.entity.ShoppingCart;
import com.sjiang.miaojj.mapper.ShoppingCartMapper;
import com.sjiang.miaojj.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service.impl
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-17  20:52
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
