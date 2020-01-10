package authorize.controller;

import feign.interfaces.EurekaClientFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/24 17
 * @Description:
 */
@RestController
public class TestController {

    @RequestMapping("/a")
    public String  a(){
        return "a-authorize";
    }
    @RequestMapping("/b")
    public String  b(){
        return "b-authorize";
    }
    @RequestMapping("/v")
    public String  v(){
        return "v-authorize";
    }


}
