package rule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/18 17
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"rule"})
@EnableDiscoveryClient
public class RuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuleApplication.class, args);
    }
}
