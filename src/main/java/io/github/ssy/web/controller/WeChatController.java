package io.github.ssy.web.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
        logger.warn("postdata" + postdata);
        Document doc = DocumentHelper.parseText(postdata);
        Element root = doc.getRootElement();
        logger.warn("root:" + root.asXML());
        return buidSendMessage(root.elementText("FromUserName"));
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


  private String buidSendMessage(String openId) {
    StringBuffer str = new StringBuffer();
    String type = "text";
    String backMsg = "http://laoshi.yidaren.top//learning/qiandao.html";
    str.append("<xml>");
    str.append("<ToUserName>" + openId + "</ToUserName>");
    str.append("<FromUserName>gh_3678a48f4fca</FromUserName>");
    str.append("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
    str.append("<MsgType>" + type + "</MsgType>");
    str.append("<Content>" + backMsg + "</Content>");
    str.append("</xml>");
    logger.info("appidCallBack method sync weixin user "
        + "info to dalin account success:" + str.toString());
    return str.toString();

  }


}
