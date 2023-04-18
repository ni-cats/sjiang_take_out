package com.sjiang.miaojj.controller;

import com.sjiang.miaojj.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.controller
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-15  21:56
 * @Description: TODO 文件上传和下载
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/common")

public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * @param file:
     * @return com.sjiang.reggie.common.R<java.lang.String>
     * @throws
     * @Description TODO 文件上传
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 22:40
     */

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file为临时文件，需要转存到指定位置，否则本次请求完成后临时文件会直接删除

        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取原始文件名后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //利用UUID生成文件名，防止文件重复造成的文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在，不存在则创建一个
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            log.info("上传文件：{}", fileName);
            //转存文件
            file.transferTo(new File(basePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * @param name:
     * @param response:输出流需要通过response获得，代表响应，直接通过response操作数据
     * @return void
     * @throws
     * @Description TODO 文件下载
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/15 22:42
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //通过输入流读取文件内容
            fileInputStream = new FileInputStream(new File(basePath + name));
            //通过输出流写回浏览器
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            log.info("下载文件：{}", name);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
