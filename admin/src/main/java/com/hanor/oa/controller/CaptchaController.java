package com.hanor.oa.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@IocBean
@At("/captcha")
public class CaptchaController {

    @At
    @Ok("raw:png")
    public BufferedImage next(HttpSession session, @Param("w") int w, @Param("h") int h) {
        if (w * h < 1) { //长或宽为0?重置为默认长宽.
            w = 200;
            h = 60;
        }
        Captcha captcha = new Captcha.Builder(w, h)
                .addText().addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();
        String text = captcha.getAnswer();
        session.setAttribute("captcha", text);
        System.out.println("验证码：" + text);
        return captcha.getImage();
    }
}