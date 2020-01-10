package feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * feign 的拦截器配置
 *
 * @author huangaixing
 * @date 2018/05/24
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 设置请求的头部信息
        requestTemplate.header("X-access-url", get("X-access-url"));
        requestTemplate.header("X-access-token", get("x-access-token"));
        requestTemplate.header("X-access-url", get("X-access-url"));
    }

    private String get(String key) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!StringUtils.isEmpty(servletRequestAttributes)) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return request.getHeader(key);
        }
        return null;
    }

    @Bean
    public Retryer feiginReteryer(){

        return new Retryer.Default(100,SECONDS.toMillis(1),5);
    }

}
