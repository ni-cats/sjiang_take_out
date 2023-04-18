package com.sjiang.miaojj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjiang.miaojj.common.R;
import com.sjiang.miaojj.entity.Employee;
import com.sjiang.miaojj.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.controller
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-14  15:10
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * @param request:  将员工id存储到session中，便于从session中获取当前登录用户
     * @param employee:
     * @return com.sjiang.reggie.common.R<com.sjiang.reggie.entity.Employee>
     * @throws
     * @Description TODO 员工登录
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/14 15:23
     */


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败，用户名不存在");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误！请重新输入！");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用！");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * @param request: 用于清除session中的已经登录的员工的id
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 员工退出
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/14 16:25
     */

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");

    }

    /**
     * @param employee:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 新增员工信息
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 10:11
     */

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工信息：{}", employee.toString());

        //设置初始密码123456，进行加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        //设置其它默认值
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //获取创建人id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("新增员工成功！");
    }

    /**
     * @param page:     查询页面
     * @param pageSize: 查询页面的条数
     * @param name:     要查找的员工名字
     * @return com.sjiang.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @Description TODO 分页查询
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 11:21
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构建条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //查询数据库
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }


    /**
     * @param employee:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 根据id修改员工信息
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 13:47
     */

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("待更新的员工信息：{}", employee.toString());

//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());

        long id = Thread.currentThread().getId();
        log.info("当前线程id：{}",id);

        employeeService.updateById(employee);
        return R.success("员工信息更新成功！");
    }

    /**
     *
     * @Description TODO 根据id查询员工信息
     * @param id:
     * @return com.sjiang.reggie.common.R<com.sjiang.reggie.entity.Employee>
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 14:39
     * @throws
     */

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id) {
        log.info("根据id:{}查询员工信息！", id);
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("该员工不存在！");
    }

}
