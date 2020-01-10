package feign.interfaces;

import feign.config.FeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/27 15
 * @Description:
 */
@FeignClient(value = "cloud-authorize",configuration = FeignConfig.class)
public interface AuthorizeFeign  {


    @GetMapping(value = "/a")
    String a(@RequestParam(value = "name") String name);


    @GetMapping(value = "/b")
    String b(@RequestParam(value = "name") String name);

    @GetMapping(value = "/c")
    String c(@RequestParam(value = "name") String name);
}
