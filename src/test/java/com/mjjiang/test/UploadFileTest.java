package com.mjjiang.test;

import org.junit.jupiter.api.Test;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.mjjiang.test
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  22:29
 * @Description: TODO
 * @Version: 1.0
 */

public class UploadFileTest {
    @Test
    public void test1(){
        String fileName ="qewfqergwrthwrth.png";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
