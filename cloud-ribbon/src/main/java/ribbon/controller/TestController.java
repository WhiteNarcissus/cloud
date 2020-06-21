package ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ribbon.service.RibbonService;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/24 17
 * @Description:
 */
@RestController
public class TestController {
    @Autowired
    RibbonService ribbonService ;


    @GetMapping("/hi")
    String  test(@RequestParam (required = false,defaultValue = "forezp") String name) {
        return ribbonService.hi(name);
    }

}
