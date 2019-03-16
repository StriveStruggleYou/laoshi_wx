package io.github.ssy.web.controller;


import io.github.ssy.drools.DroolsHelper;
import io.github.ssy.entity.WxUser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeChatController {


  Map<String, Integer> userState = new HashMap<>();

  DroolsHelper droolsHelper = new DroolsHelper();

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
        Element msgType = root.element("MsgType");
        if (msgType != null) {

          WxUser wxUser = new WxUser();
          String openId = root.elementText("FromUserName");

          //进入时间类型
          if (msgType.getText().equals("event")) {
            Element event = root.element("Event");
            if (event.getText().equals("subscribe")) {
              return buidSubscribeSendMessage(openId);
            }
          }
          //对话交流
          if (msgType.getText().equals("text")) {

            String content = root.elementText("Content");
            wxUser.setOpenId(openId);
            wxUser.setMsg(content);
            //设置状态
            wxUser.setState(userState.get(openId));

            KieSession kieSession = droolsHelper.getKieSession();

            kieSession.insert(wxUser);

            kieSession.fireAllRules();
            //
            userState.put(openId, wxUser.getState());
            return buidSendMessage(root.elementText("FromUserName"), wxUser.getResponse());

//            if (content.equals("退出")) {
//              userState.put(openId, null);
//              return buidSendMessage(root.elementText("FromUserName"), "已经退出绑定流程");
//            }
//            if (content.equals("绑定")) {
//              userState.put(openId, 1);
//              return buidSendMessage(root.elementText("FromUserName"), "请输入手机号进行绑定");
//            }
//            if (userState.get(openId) != null && userState.get(openId) == 1) {
//              if (content.length() == 11 && org.apache.commons.lang3.math.NumberUtils
//                  .isNumber(content)) {
//                userState.put(openId, 2);
//                return buidSendMessage(root.elementText("FromUserName"), "已发送验证码进行手机号绑定");
//              } else {
//                return buidSendMessage(root.elementText("FromUserName"), "你是否没有输入正确的手机号呢，请重新输入");
//              }
//            }
//
//            if (userState.get(openId) != null && userState.get(openId) == 2) {
//              if (content.length() == 6 && org.apache.commons.lang3.math.NumberUtils
//                  .isNumber(content)) {
//                userState.put(openId, null);
//                return buidSendMessage(root.elementText("FromUserName"), "绑定成功");
//              } else {
//                return buidSendMessage(root.elementText("FromUserName"), "您没有输入正确的验证码呢");
//              }
//            }
          }
        }
        //
        return buidSendMessage(root.elementText("FromUserName"), "你输入的意思我不能理解呢");
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


  private String buidSendMessage(String openId, String content) {
    StringBuffer str = new StringBuffer();
    String type = "text";
    String backMsg = "http://laoshi.yidaren.top//learning/qiandao.html";
    str.append("<xml>");
    str.append("<ToUserName>" + openId + "</ToUserName>");
    str.append("<FromUserName>gh_3678a48f4fca</FromUserName>");
    str.append("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
    str.append("<MsgType>" + type + "</MsgType>");
    str.append("<Content>" + content + "</Content>");
    str.append("</xml>");
    logger.info("appidCallBack method sync weixin user "
        + "info to dalin account success:" + str.toString());
    return str.toString();

  }


  private String buidSubscribeSendMessage(String openId) {
    StringBuffer str = new StringBuffer();
    String type = "text";
    String backMsg = "欢迎你关注我呀,想要获取微信获取余额提醒就输入绑定，进入手机号绑定流程";
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

  public static void main(String args[]) throws DocumentException {
    String xml = "<xml><ToUserName><![CDATA[gh\n"
        + "_3678a48f4fca]]></ToUserName>\n"
        + "<FromUserName><![CDATA[omi-jwl1jEAuZzkykqTiRhAtb5x8]]></FromUserName>\n"
        + "<CreateTime>1552620907</CreateTime>\n"
        + "<Event><![CDATA[subscribe]]></Event>\n"
        + "<EventKey><![CDATA[]]></EventKey>\n"
        + "</xml>\n";


  }


}
