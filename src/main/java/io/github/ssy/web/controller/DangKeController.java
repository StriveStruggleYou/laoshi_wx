package io.github.ssy.web.controller;


import io.github.ssy.drools.DroolsHelper;
import io.github.ssy.entity.WxUser;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DangKeController {

  @RequestMapping(value = "/learning/dangke/{id}")
  public String dangKeDeal(@PathVariable("id") String id, String openId) {
    //没有走入口数据信息
    if (StringUtils.isEmpty(openId)) {
      //请进入公众号，并进行操作。
      return "/error/error.html";
    } else {
      //第一课
      if (id.equals("1")) {
        return "redirect:http://h5.cyol.com/special/daxuexi/daxuexi2u7/m.php";
      }
    }
    return "/learning/table.html";
  }

  @RequestMapping(value = "/learning/qiandao.html")
  public String qianDao() {
    return "/learning/table.html";
  }

  @RequestMapping(value = "/learning/test")
  @ResponseBody
  public Object drools() {
    DroolsHelper droolsHelper = new DroolsHelper();
    KieSession kieSession = droolsHelper.getKieSession();
    WxUser wxUser = new WxUser();
    wxUser.setMsg("nihao");
    kieSession.insert(wxUser);
    kieSession.fireAllRules();
    System.out.println(wxUser);
    return wxUser;
  }


}
