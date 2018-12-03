package io.github.ssy.web.controller;


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
        return "/learning/1.html";
    }
}
