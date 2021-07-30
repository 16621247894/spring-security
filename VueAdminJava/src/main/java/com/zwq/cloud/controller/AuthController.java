package com.zwq.cloud.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.zwq.cloud.common.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class AuthController extends BaseController{


    /**
     * 生成验证码
     */
    @Autowired
    Producer producer;

    @ApiOperation("生成验证码")
    @GetMapping("/captcha")
    public Response captcha() throws IOException {
        String key= UUID.randomUUID().toString();
        // 验证码
        String code=producer.createText();

        BufferedImage image=producer.createImage(code);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        Base64Encoder encoder=new Base64Encoder();
        String str = "data:image/jpeg;base64,";

    }
}
