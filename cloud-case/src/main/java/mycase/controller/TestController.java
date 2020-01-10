package mycase.controller;

import feign.interfaces.AuthorizeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/24 17
 * @Description:
 */
@RestController
public class TestController {
    @Autowired
    AuthorizeFeign authorizeFeign;

  @RequestMapping("/a")
   public String  a(){

      return authorizeFeign.a("");
  }

    @RequestMapping("/c")
    public String  c(){
        return "c-case";
    }

    @RequestMapping("/b")
    public String  b(){

        return "b-case";
    }

}
