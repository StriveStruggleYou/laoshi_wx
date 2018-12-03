package io.github.ssy.web.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeChatController {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    @ResponseBody
    public Object msgDeal(String echostr, String signature, String timestamp, String nonce,
            String openid, HttpServletRequest request) {
        if (StringUtils.isEmpty(echostr)) {
            String postdata = null;
            try {
                postdata = inputStreamToString(request.getInputStream());
            } catch (Exception e) {
                logger.error("postdata error openId" + openid, e);
            }
            return "success";
        } else {
            //校验token 逻辑
            return echostr;
        }
    }

    public String inputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }


}
