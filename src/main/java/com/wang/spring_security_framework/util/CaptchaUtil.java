package com.wang.spring_security_framework.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wang.spring_security_framework.service.CaptchaService;
import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Component
//Captcha 生成工具
public class CaptchaUtil {
    @Autowired
    private DefaultKaptcha producer;
    @Autowired
    private CaptchaService captchaService;

    //生成catchCreator的map
    public Map<String, Object> catchaImgCreator() throws IOException {
        //生成文字验证码
        String text = producer.createText();
        //生成文字对应的图片验证码
        BufferedImage image = producer.createImage(text);

        //将图片写出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        //对写出的字节数组进行Base64编码 ==> 用于传递8比特字节码
        BASE64Encoder encoder = new BASE64Encoder();

        //生成token
        Map<String, Object> token = captchaService.createToken(text);
        token.put("img", encoder.encode(outputStream.toByteArray()));
        return token;
    }

}
