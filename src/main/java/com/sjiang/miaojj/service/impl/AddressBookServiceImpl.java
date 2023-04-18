package com.sjiang.miaojj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjiang.miaojj.entity.AddressBook;
import com.sjiang.miaojj.mapper.AddressBookMapper;
import com.sjiang.miaojj.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service.impl
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-17  14:54
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {
}
