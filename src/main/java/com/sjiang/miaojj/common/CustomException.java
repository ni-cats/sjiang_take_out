package com.sjiang.miaojj.common;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.common
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  21:04
 * @Description: TODO
 * @Version: 1.0
 */

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
