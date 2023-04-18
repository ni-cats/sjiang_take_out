package com.sjiang.miaojj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sjiang.miaojj.common.R;
import com.sjiang.miaojj.entity.User;
import com.sjiang.miaojj.service.UserService;
import com.sjiang.miaojj.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.controller
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-17  13:31
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @param user:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 发送手机验证码短信
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 13:48
     */

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码code：{}", code);
            //调用阿里云提供的短信服务API发送短信
//            SMSUtils.sendMessage("喵九匠","",phone,code);
            //将验证码保存到session中
            session.setAttribute(phone, code);
            return R.success("验证码发送成功！");
        }
        return R.error("短信发送失败！");
    }

    /**
     * @param map:存储登录用户的属性值
     * @param session:       用session域存储验证码
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 移动端用户登录
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/17 14:08
     */

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {

        log.info("接收登录数据：{}", map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从session中获取保存的验证码进行比对
        String codeInSession = (String) session.getAttribute(phone);
        //比对成功，允许登录
        if (codeInSession != null && codeInSession.equals(code)) {

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            //判断是否为新用户，若是新用户自动完成注册
            if (user == null) {
                //新用户注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败！");
    }
}
