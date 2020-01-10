package ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/25 15
 * @Description:
 */
@Service
public class RibbonService {
    @Autowired
    RestTemplate restTemplate ;

    public String hi(String name){
        //这里 cloud-case 要对应 服务的name ，负载均衡（loading balance ）
        return restTemplate.getForObject("http://cloud-case/hi?name="+name,String.class);
    }




}
