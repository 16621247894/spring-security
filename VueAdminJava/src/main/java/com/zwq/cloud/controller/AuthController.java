package com.zwq.cloud.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.utils.Constants;
import com.zwq.cloud.utils.RedisCache;
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

/**
 * @author wuqing.zhu
 */
@RestController
public class AuthController extends BaseController {



    @Autowired
    RedisCache redisCache;

    /**
     * 生成验证码
     */
    @Autowired
    Producer producer;

    @ApiOperation("生成验证码")
    @GetMapping("/captcha")
    public Response captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        // 验证码
        String code = producer.createText();
        key="aaa";
        code="1111";
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        //Base64Encoder encoder=new Base64Encoder();
        String str = "data:image/jpeg;base64,";
        System.out.println("获取的key:"+key);
        System.out.println("获取的code:"+code);
        String base64Img = str + Base64Encoder.encode(outputStream.toByteArray());
        redisCache.hPut(Constants.Captcha.CAPTCHA_KEY, key, code, 10);
        return Response.success(
                MapUtil.builder()
                        .put("token", key)
                        .put("captchaImg", base64Img)
                        .build()

        );


    }
}
