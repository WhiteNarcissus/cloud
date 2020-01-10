package mycase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: maojicheng
 * @Date: 2019/12/24 17
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Case {
    public static void main(String[] args) {
        SpringApplication.run(Case.class, args);
    }

}
