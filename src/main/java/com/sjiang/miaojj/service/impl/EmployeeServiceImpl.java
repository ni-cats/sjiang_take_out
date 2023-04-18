package com.sjiang.miaojj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjiang.miaojj.entity.Employee;
import com.sjiang.miaojj.mapper.EmployeeMapper;
import com.sjiang.miaojj.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.service.impl
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-14  15:08
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{
}
