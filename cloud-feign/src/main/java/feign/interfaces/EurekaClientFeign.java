package feign.interfaces;

import feign.config.FeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/25 18
 * @Description:
 */
@FeignClient(value = "cloud-case",configuration = FeignConfig.class)
public interface EurekaClientFeign {
    @GetMapping(value = "hi")
    String sayHiFromClicentEureka(@RequestParam(value = "name") String name);

    @GetMapping(value = "/a")
    String a(@RequestParam(value = "name") String name);


    @GetMapping(value = "/b")
    String b(@RequestParam(value = "name") String name);

    @GetMapping(value = "/c")
    String c(@RequestParam(value = "name") String name);
}
