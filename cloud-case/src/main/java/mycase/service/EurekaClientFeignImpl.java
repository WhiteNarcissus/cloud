package mycase.service;

import feign.interfaces.EurekaClientFeign;
import org.springframework.stereotype.Service;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/27 10
 * @Description:
 */
@Service
public class EurekaClientFeignImpl implements EurekaClientFeign {

    @Override
    public String sayHiFromClicentEureka(String name) {
        return null;
    }

    @Override
    public String a(String name) {
        return "a";
    }

    @Override
    public String b(String name) {
        return "b";
    }

    @Override
    public String c(String name) {
        return "c";
    }
}
