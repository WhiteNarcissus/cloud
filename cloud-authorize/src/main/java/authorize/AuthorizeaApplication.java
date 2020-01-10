package authorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/18 17
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizeaApplication.class, args);
    }
}
